package movie.architech.android.com.popularmovienewarchitecture.ui.detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import movie.architech.android.com.popularmovienewarchitecture.R;
import movie.architech.android.com.popularmovienewarchitecture.data.network.NetworkUtils;
import movie.architech.android.com.popularmovienewarchitecture.data.network.TheMovieDbJsonUtils;
import movie.architech.android.com.popularmovienewarchitecture.utils.AppExecutor;

public class MovieTrailerViewModel extends ViewModel {

    MutableLiveData<List<MovieTrailer>> mLiveList;

    public MovieTrailerViewModel(Context context,int movieId) {
        mLiveList = new MutableLiveData<>();
        loadMovieTrailer(context,movieId);
    }

    public LiveData<List<MovieTrailer>> getLiveList() {
        return mLiveList;
    }

    private void loadMovieTrailer(Context context, int movieId){

        /*new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                URL trailerListUrl = NetworkUtils.buildUrl(String.valueOf(movieId), context.getString(R.string.THE_MOVIE_API_KEY), NetworkUtils.TRAILER_REQUEST);

                try{
                    String trailerResponse = NetworkUtils.getResponseFromHttpUrl(trailerListUrl);
                    return trailerResponse;
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String trailerResponse) {
                if(trailerResponse!= null){
                    try{
                        List<MovieTrailer> data = TheMovieDbJsonUtils.getSimpleMovieTrailerArrayListFromJson(trailerResponse);
                        mLiveList.postValue(data);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }.execute();*/

        AppExecutor.getInstance().getNetworkExecutor().execute(new Runnable() {
            @Override
            public void run() {
                URL trailerListUrl = NetworkUtils.buildUrl(String.valueOf(movieId), context.getString(R.string.THE_MOVIE_API_KEY), NetworkUtils.TRAILER_REQUEST);

                try{
                    String trailerResponse = NetworkUtils.getResponseFromHttpUrl(trailerListUrl);
                    List<MovieTrailer> data = TheMovieDbJsonUtils.getSimpleMovieTrailerArrayListFromJson(trailerResponse);
                    mLiveList.postValue(data);
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }catch (JSONException jse){
                    jse.printStackTrace();
                }

            }
        });
    }
}
