package movie.architech.android.com.popularmovienewarchitecture.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;



@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie WHERE movie_type = :movieType ORDER BY popularity")
    DataSource.Factory<Integer,MovieEntry> getAllMovie(int movieType);

    @Query("SELECT * FROM movie WHERE movie_type = :movieType ORDER BY movie_vote_count")
    DataSource.Factory<Integer,MovieEntry> getAllMovieByVoteCount(int movieType);

    @Query("SELECT COUNT(id) FROM movie WHERE movie_type = :movieType")
    int countAllMovieOfType(int movieType);

    @Query("DELETE FROM movie WHERE movie_type != :favoriteMovie")
    public void deleteOldMovies(int favoriteMovie);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public void bulkInsert(MovieEntry... movies);

}
