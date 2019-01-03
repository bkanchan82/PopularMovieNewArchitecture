package movie.architech.android.com.popularmovienewarchitecture.data.network;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    //Logging tag
    private static final String TAG = NetworkUtils.class.getSimpleName();
    //Base url for fetching movies list from network
    private static final String THE_MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/movie";
    //query parameter key for api key
    private static final String API_KEY_PARAM = "api_key";
    //append url with this parameter to get movie videos
    public static final String TRAILER_REQUEST = "videos";
    // appen url with this parameter to get user reviews
    public static final String REVIEW_REQUEST = "reviews";

    public static URL buildUrl(String queryBy,String apiKey) {

        Uri uri = Uri.parse(THE_MOVIE_DB_BASE_URL).buildUpon()
                .appendPath(queryBy)
                .appendQueryParameter(API_KEY_PARAM,apiKey)
                .build();

        URL url = null;
        try{
            url = new URL(uri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        Log.d(TAG,"Build url : "+url.toString());
        return url;
    }

    public static URL buildUrl(String queryParam, String apiKey,String requestType) {

        Uri buildUri = Uri.parse(THE_MOVIE_DB_BASE_URL).buildUpon()
                .appendPath(queryParam)
                .appendPath(requestType)
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }

        Log.v(TAG, "Build url : " + (url != null ? url.toString() : null));
        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            Log.d(TAG,"Recieved Response : "+response);
            return response;
        } finally {
            urlConnection.disconnect();
        }

    }

}
