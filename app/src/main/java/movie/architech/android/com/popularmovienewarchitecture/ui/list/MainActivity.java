package movie.architech.android.com.popularmovienewarchitecture.ui.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import movie.architech.android.com.popularmovienewarchitecture.R;
import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieEntry;
import movie.architech.android.com.popularmovienewarchitecture.ui.detail.DetailActivity;
import movie.architech.android.com.popularmovienewarchitecture.ui.settings.SettingsActivity;
import movie.architech.android.com.popularmovienewarchitecture.utils.InjectorUtils;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener,
        MovieAdapter.OnMovieSelectListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView rvDisplayMovie;
    MovieAdapter mAdapter;
    MainActivityViewModel mViewModel;

    @Nullable
    private SimpleIdelingResource mIdelingResource;

    @Nullable
    CountingIdlingResource countingIdlingResource = new CountingIdlingResource("Network_call");

    @VisibleForTesting
    @Nullable
    public IdlingResource getIdelingResource() {
        if (mIdelingResource == null) {
            mIdelingResource = new SimpleIdelingResource();
        }
        return mIdelingResource;
    }

    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        return countingIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvDisplayMovie = (RecyclerView) findViewById(R.id.rv_display_movies);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(getResources().getInteger(R.integer.stagered_grid_column),
                        StaggeredGridLayoutManager.VERTICAL);
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/
        rvDisplayMovie.setLayoutManager(staggeredGridLayoutManager);
        mAdapter = new MovieAdapter(this);

        rvDisplayMovie.setAdapter(mAdapter);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        getIdelingResource();
        loadViewModel();

    }

    private void loadViewModel() {
        MainViewModelFactory factory = InjectorUtils.provideMainViewModelFactory(this);

        mViewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
        /*if (mIdelingResource != null) {
            mIdelingResource.setIdelState(false);
        }*/
        if (countingIdlingResource != null) {
            countingIdlingResource.increment();
        }
        mViewModel.getMovies().observe(this, new Observer<PagedList<MovieEntry>>() {
            @Override
            public void onChanged(@Nullable PagedList<MovieEntry> movieEntries) {
                Log.d(TAG, "Movie list changed" + movieEntries.size());
                mAdapter.submitList(movieEntries);
                if (movieEntries != null && movieEntries.size() > 0) {
                    /*if (mIdelingResource != null) {
                        mIdelingResource.setIdelState(true);
                    }*/
                    if (countingIdlingResource != null) {
                        countingIdlingResource.decrement();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        mViewModel.triggerPrefsChenged(this);
    }


    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_LOGO_SIZE = "w185/";
    public static final String POSTER_SIZE = "w300/";

    @Override
    public void onMovieClickEvent(MovieEntry movieEntry) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieEntry.getMovieId());
        intent.putExtra(DetailActivity.EXTRA_MOVIE_TITLE, movieEntry.getMovieTitle());
        intent.putExtra(DetailActivity.EXTRA_MOVIE_POSTER_URL, POSTER_BASE_URL + POSTER_SIZE + movieEntry.getPosterPath());
        startActivity(intent);
    }
}
