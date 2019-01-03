package movie.architech.android.com.popularmovienewarchitecture.data;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.Context;
import android.util.Log;

import java.util.List;

import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieDao;
import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieEntry;
import movie.architech.android.com.popularmovienewarchitecture.data.network.MovieNetworkDataSource;
import movie.architech.android.com.popularmovienewarchitecture.utils.AppExecutor;

public class PopularMovieRepository {

    private static final String TAG = PopularMovieRepository.class.getSimpleName();

    private MovieDao mMovieDao;
    private MovieNetworkDataSource mMovieNetworkDataSource;
    private AppExecutor mAppExecutor;

    private static PopularMovieRepository sInstance;
    private static final Object LOCK = new Object();

    private boolean mInitialized = false;

    private PopularMovieRepository(MovieDao movieDao,
                                   MovieNetworkDataSource movieNetworkDataSource,
                                   AppExecutor appExecutor) {
        mMovieDao = movieDao;
        mMovieNetworkDataSource = movieNetworkDataSource;
        mAppExecutor = appExecutor;
    }

    public static synchronized PopularMovieRepository getInstance(MovieDao movieDao,
                                                                  MovieNetworkDataSource movieNetworkDataSource,
                                                                  AppExecutor appExecutor) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new PopularMovieRepository(movieDao,
                        movieNetworkDataSource,
                        appExecutor);
            }
        }
        return sInstance;
    }


    public LiveData<PagedList<MovieEntry>> getMoviesList(int movieType){
        initializeData(movieType);
        if(movieType == 2){
            return new LivePagedListBuilder<>(
                    mMovieDao.getAllMovieByVoteCount(movieType),20).build();
        }else {
            return new LivePagedListBuilder<>(mMovieDao.getAllMovie(movieType),20).build();
        }
    }

    public synchronized void initializeData(int movieType) {
        LiveData<MovieEntry[]> liveMovieEntry = mMovieNetworkDataSource.getLiveMoviesEntries();
        liveMovieEntry.observeForever(newMovieEntries -> {
            mAppExecutor.getIoExecutor().execute(() -> {
                deleteOldData();
                mMovieDao.bulkInsert(newMovieEntries);
            });
        });

//        if (mInitialized) return;
        mInitialized = true;

        mAppExecutor.getIoExecutor().execute(() -> {
            if(isFetchNeeded(movieType)){
                startFetchMovieService();
            }
        });


    }

    private boolean isFetchNeeded(int movieType) {
        int count = mMovieDao.countAllMovieOfType(movieType);
        return (count == 0);
    }

    private void deleteOldData() {
        mMovieDao.deleteOldMovies(0);
    }

    private void startFetchMovieService(){
        mMovieNetworkDataSource.fetchMovieList();
    }
}
