package movie.architech.android.com.popularmovienewarchitecture.data.network;

import android.support.annotation.NonNull;

import java.util.List;

import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieEntry;

public class MovieResponse {

    @NonNull
    private final List<MovieEntry> mMoviesEntry;

    public MovieResponse(List<MovieEntry> movieEntries){
        mMoviesEntry = movieEntries;
    }

    public List<MovieEntry> getMoviesEntry(){
        return mMoviesEntry;
    }

}
