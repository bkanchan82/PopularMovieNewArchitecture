package movie.architech.android.com.popularmovienewarchitecture.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import movie.architech.android.com.popularmovienewarchitecture.R;
import movie.architech.android.com.popularmovienewarchitecture.data.network.NetworkUtils;
import movie.architech.android.com.popularmovienewarchitecture.data.network.TheMovieDbJsonUtils;
import movie.architech.android.com.popularmovienewarchitecture.utils.AppExecutor;

public class ReviewViewModel extends ViewModel {


    MutableLiveData<List<MovieReview>> mLiveList;

    public ReviewViewModel(Context context, int movieId) {
        mLiveList = new MutableLiveData<>();
        loadMovieReview(context,movieId);
    }

    public LiveData<List<MovieReview>> getLiveList() {
        return mLiveList;
    }

    private void loadMovieReview(Context context, int movieId){

        /*new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... voids) {
                URL reviewListUrl = NetworkUtils.buildUrl(String.valueOf(movieId), context.getString(R.string.THE_MOVIE_API_KEY), NetworkUtils.REVIEW_REQUEST);

                try{
                    String reviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewListUrl);
                    return reviewResponse;
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String reviewResponse) {
                if(reviewResponse!= null){
                    try{
                        List<MovieReview> data = TheMovieDbJsonUtils.getSimpleMovieReviewArrayListFromJson(reviewResponse);
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
                URL reviewListUrl = NetworkUtils.buildUrl(String.valueOf(movieId), context.getString(R.string.THE_MOVIE_API_KEY), NetworkUtils.REVIEW_REQUEST);

                try{
                    String reviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewListUrl);
                    List<MovieReview> data = TheMovieDbJsonUtils.getSimpleMovieReviewArrayListFromJson(reviewResponse);
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
