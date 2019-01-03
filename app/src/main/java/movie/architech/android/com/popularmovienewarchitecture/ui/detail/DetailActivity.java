package movie.architech.android.com.popularmovienewarchitecture.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import movie.architech.android.com.popularmovienewarchitecture.R;

public class DetailActivity extends AppCompatActivity
implements TrailerAdapter.TrailerAdapterOnItemClickListener {

    TextView movieTitleTv;
    ImageView posterImageView;
    RecyclerView trailerRv;
    RecyclerView reviewRv;

    public static final String EXTRA_MOVIE_ID = "movie_id_extra";
    public static final String EXTRA_MOVIE_POSTER_URL = "movie_poster_url";
    public static final String EXTRA_MOVIE_TITLE = "movie_title";

    private int mMovieId;
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        /*Toolbar toolbar = (Toolbar)findViewById(R.id.detail_activity_toolbar);
        setSupportActionBar(toolbar);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieTitleTv = (TextView)findViewById(R.id.movie_title);
        posterImageView = (ImageView)findViewById(R.id.poster_image_view);
        trailerRv = (RecyclerView)findViewById(R.id.trailer_rv);
        reviewRv = (RecyclerView)findViewById(R.id.review_rv);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_MOVIE_ID)){
            mMovieId = intent.getIntExtra(EXTRA_MOVIE_ID,0);
            String posterUrl = intent.getStringExtra(EXTRA_MOVIE_POSTER_URL);
            if(posterUrl != null && posterUrl.length()>0){
                Picasso.get().load(posterUrl).into(posterImageView);
            }
            String movieTitle = intent.getStringExtra(EXTRA_MOVIE_TITLE);
            if(movieTitle != null && movieTitle.length()>0){
                movieTitleTv.setText(movieTitle);
            }
        }


        LinearLayoutManager trailerLinearLM =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        trailerRv.setLayoutManager(trailerLinearLM);
        trailerRv.setHasFixedSize(true);

        mTrailerAdapter = new TrailerAdapter(this, this);

        trailerRv.setAdapter(mTrailerAdapter);
        loadTrailerViewModel();


        LinearLayoutManager reviewLinearLM =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        reviewRv.setLayoutManager(reviewLinearLM);

        reviewRv.setHasFixedSize(true);

        mReviewAdapter = new ReviewAdapter();

        reviewRv.setAdapter(mReviewAdapter);
        loadReviewViewModel();

    }

    private void loadTrailerViewModel(){
        if(mMovieId != 0) {
            MovieTrailerViewModelFactory factory = new MovieTrailerViewModelFactory(this,mMovieId );
            MovieTrailerViewModel viewModel = ViewModelProviders.of(this,factory).get(MovieTrailerViewModel.class);
            LiveData<List<MovieTrailer>> liveData = viewModel.getLiveList();
            liveData.observe(this, new Observer<List<MovieTrailer>>() {
                @Override
                public void onChanged(@Nullable List<MovieTrailer> movieTrailers) {
                    mTrailerAdapter.setMovieTrailerData(movieTrailers);
                }
            });
        }
    }

    private void loadReviewViewModel(){
        if(mMovieId != 0) {
            ReviewViewModelFactory factory = new ReviewViewModelFactory(this,mMovieId );
            ReviewViewModel viewModel = ViewModelProviders.of(this,factory).get(ReviewViewModel.class);
            LiveData<List<MovieReview>> liveData = viewModel.getLiveList();
            liveData.observe(this, new Observer<List<MovieReview>>() {
                @Override
                public void onChanged(@Nullable List<MovieReview> movieReviews) {
                    mReviewAdapter.setMovieReviewData(movieReviews);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.action_favorite:
                break;
            case R.id.action_share:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClickListener(MovieTrailer trailerObject) {

    }
}
