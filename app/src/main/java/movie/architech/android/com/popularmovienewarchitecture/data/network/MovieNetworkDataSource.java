package movie.architech.android.com.popularmovienewarchitecture.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executor;

import movie.architech.android.com.popularmovienewarchitecture.R;
import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieEntry;
import movie.architech.android.com.popularmovienewarchitecture.data.prefs.PreferenceUtils;
import movie.architech.android.com.popularmovienewarchitecture.utils.AppExecutor;

public class MovieNetworkDataSource {

    private static final String TAG = MovieNetworkDataSource.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static MovieNetworkDataSource sInstance;

    private AppExecutor mExecutor;
    private MutableLiveData<MovieEntry[]> mMoviesEntries;
    private Context mContext;

    private MovieNetworkDataSource(Context context, AppExecutor appExecutor){
        mContext = context;
        mExecutor = appExecutor;
        mMoviesEntries = new MutableLiveData<>();
    }


    public static synchronized MovieNetworkDataSource getInstance(Context context, AppExecutor executor){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new MovieNetworkDataSource(context,executor);
            }
        }
        return sInstance;
    }


    public LiveData<MovieEntry[]> getLiveMoviesEntries() {
        return mMoviesEntries;
    }

    public void fetchMovieList(){

        mExecutor.getNetworkExecutor().execute(new Runnable() {
            @Override
            public void run() {
                String shortOrder = PreferenceUtils.getShortOrder(mContext);

                URL url  = NetworkUtils.buildUrl(shortOrder,mContext.getString(R.string.THE_MOVIE_API_KEY));
                try{

                    String jsonString = NetworkUtils.getResponseFromHttpUrl(url);
                    int movieType = PreferenceUtils.getMovieType(mContext);
                    Log.d(TAG,"Saved changes for type : "+movieType);
                    MovieEntry[] entries = TheMovieDbJsonUtils.getSimpleMovieArrayListFromJson(jsonString,movieType);
                    mMoviesEntries.postValue(entries);

                }catch (IOException ioe){
                    ioe.printStackTrace();
                }catch (JSONException je){
                    je.printStackTrace();
                }
            }
        });

    }


}
