package movie.architech.android.com.popularmovienewarchitecture.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import movie.architech.android.com.popularmovienewarchitecture.utils.InjectorUtils;

public class MovieSyncIntentService extends IntentService {

    public MovieSyncIntentService(){
        super(MovieSyncIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        MovieNetworkDataSource movieNetworkDataSource = InjectorUtils.provideMovieNetworkDataSource(this);
        movieNetworkDataSource.fetchMovieList();
    }
}
