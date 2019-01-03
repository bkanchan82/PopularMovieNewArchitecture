package movie.architech.android.com.popularmovienewarchitecture.utils;

import android.content.Context;

import movie.architech.android.com.popularmovienewarchitecture.data.PopularMovieRepository;
import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieDao;
import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieDatabase;
import movie.architech.android.com.popularmovienewarchitecture.data.network.MovieNetworkDataSource;
import movie.architech.android.com.popularmovienewarchitecture.data.prefs.PreferenceUtils;
import movie.architech.android.com.popularmovienewarchitecture.ui.list.MainViewModelFactory;

public class InjectorUtils {


    public static PopularMovieRepository provideMovieRepository(Context context){
        MovieDao movieDao = MovieDatabase.getInstance(context.getApplicationContext()).movieDao();
        AppExecutor appExecutor = AppExecutor.getInstance();
        MovieNetworkDataSource movieNetworkDataSource = provideMovieNetworkDataSource(context);
        return PopularMovieRepository.getInstance(movieDao,movieNetworkDataSource,appExecutor);
    }


    public static MovieNetworkDataSource provideMovieNetworkDataSource(Context context){
        AppExecutor appExecutor = AppExecutor.getInstance();
        return MovieNetworkDataSource.getInstance(context,appExecutor);
    }

    public static MainViewModelFactory provideMainViewModelFactory(Context context){
        PopularMovieRepository popularMovieRepository = provideMovieRepository(context);
        int movieType = PreferenceUtils.getMovieType(context);
        return new MainViewModelFactory(popularMovieRepository,movieType);
    }

}
