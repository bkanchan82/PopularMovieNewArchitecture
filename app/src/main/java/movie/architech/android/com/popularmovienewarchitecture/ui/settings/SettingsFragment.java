package movie.architech.android.com.popularmovienewarchitecture.ui.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import movie.architech.android.com.popularmovienewarchitecture.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.movie_prefs);
    }
}
