package movie.architech.android.com.popularmovienewarchitecture.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "movie")
public class MovieEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "movie_id")
    private int movieId;

    @ColumnInfo(name = "movie_vote_count")
    private int movieVoteCount;

    @ColumnInfo(name = "vote_average")
    private double voteAverage;

    @ColumnInfo(name = "movie_title")
    private String movieTitle;

    private int popularity;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @ColumnInfo(name = "original_title")
    private String originalTitle;

    private String overview;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "movie_type")
    private int movieType;

    public MovieEntry(int id,
                      int movieId,
                      int movieVoteCount,
                      double voteAverage,
                      String movieTitle,
                      int popularity,
                      String posterPath,
                      String originalLanguage,
                      String originalTitle,
                      String overview,
                      String releaseDate,
                      int movieType) {
        this.id = id;
        this.movieId = movieId;
        this.movieVoteCount = movieVoteCount;
        this.voteAverage = voteAverage;
        this.movieTitle = movieTitle;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.movieType = movieType;
    }

    @Ignore
    public MovieEntry(int movieId,
                      int movieVoteCount,
                      double voteAverage,
                      String movieTitle,
                      int popularity,
                      String posterPath,
                      String originalLanguage,
                      String originalTitle,
                      String overview,
                      String releaseDate,
                      int movieType) {
        this.movieId = movieId;
        this.movieVoteCount = movieVoteCount;
        this.voteAverage = voteAverage;
        this.movieTitle = movieTitle;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.movieType = movieType;

    }

    public int getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getMovieVoteCount() {
        return movieVoteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getMovieType() {
        return movieType;
    }
}
