package movie.architech.android.com.popularmovienewarchitecture;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import movie.architech.android.com.popularmovienewarchitecture.data.prefs.PreferenceUtils;

import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UnitTestSample {

    private static final String SELECTED_MOVIE_SHORTING = "popular";

    @Mock
    Context context;

    @Mock
    SharedPreferences sharedPreferences;

    @Before
    public void setupSharedPreference(){
        sharedPreferences = android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Test
    public void getShortingPreference(){
        when(context.getString(R.string.pref_movie_shorting_key)).thenReturn("movie_shorting");
        when(context.getString(R.string.pref_popular_movie_value)).thenReturn(SELECTED_MOVIE_SHORTING);
        String result = PreferenceUtils.getShortOrder(context);
        assertThat(result,is(SELECTED_MOVIE_SHORTING));
    }

}
