package movie.architech.android.com.popularmovienewarchitecture.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import movie.architech.android.com.popularmovienewarchitecture.R;

public class PreferenceUtils {

    public static String getShortOrder(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(context.getString(R.string.pref_movie_shorting_key),
                context.getString(R.string.pref_default_movie_shorting_value));
    }

    public static int getMovieType(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String shortBy = sharedPreferences.getString(context.getString(R.string.pref_movie_shorting_key),
                context.getString(R.string.pref_default_movie_shorting_value));
        if(shortBy.equals(context.getString(R.string.pref_favorite_movie_value))){
            return 0;
        }else if(shortBy.equals(context.getString(R.string.pref_top_rated_movie_value))){
            return 2;
        }else{
            return 1;
        }

        /*else if(shortBy.equals(context.getString(R.string.pref_popular_movie_value))){
            return 1;
        }*/
    }

}
