package movie.architech.android.com.popularmovienewarchitecture.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.content.Context;
import android.util.Log;

import java.util.List;

import movie.architech.android.com.popularmovienewarchitecture.data.PopularMovieRepository;
import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieEntry;
import movie.architech.android.com.popularmovienewarchitecture.data.prefs.PreferenceUtils;

public class MainActivityViewModel extends ViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();

    private PopularMovieRepository mRepository;
    private LiveData<PagedList<MovieEntry>> mMovies;
    private final MutableLiveData<Integer> mMovieTypeLiveData = new MutableLiveData();

    public MainActivityViewModel(PopularMovieRepository repository, int mMovieType){
        mRepository = repository;
//        mMovies = mRepository.getMoviesList(mMovieType);
        mMovies = Transformations.switchMap(mMovieTypeLiveData, (movieType) -> {
            return mRepository.getMoviesList(movieType);
        });
        //set the initial value
        mMovieTypeLiveData.setValue(mMovieType);
    }

    public  LiveData<PagedList<MovieEntry>> getMovies() {
        return mMovies;
    }

    public void triggerPrefsChenged(Context context){
        int movieType = PreferenceUtils.getMovieType(context);
        mRepository.initializeData(movieType);
        mMovieTypeLiveData.setValue(movieType);
    }
}
