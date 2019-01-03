package movie.architech.android.com.popularmovienewarchitecture.data.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieEntry;
import movie.architech.android.com.popularmovienewarchitecture.ui.detail.MovieReview;
import movie.architech.android.com.popularmovienewarchitecture.ui.detail.MovieTrailer;

/**
 * Created on 20-12-2017.
 */

public class TheMovieDbJsonUtils {

    //Movies information is contained in the array named 'results'
    private static final String TMD_RESULTS = "results";

    //this variable holds the value of vote count of the movie
    private static final String TMD_VOTE_COUNT = "vote_count";

    //this variable holds the value of title of the movie
    private static final String TMD_TITLE = "title";

    //this variable holds the value of popularity of the movie
    private static final String TMD_POPULARITY = "popularity";


    //this variable holds the value of poster image path of the movie
    private static final String TMD_POSTER_PATH = "poster_path";

    //this variable holds the value of overview of the movie
    private static final String TMD_OVERVIEW = "overview";

    //this variable holds the value of release date of the movie
    private static final String TMD_RELEASE_DATE = "release_date";

    private static final String TMD_ORIGINAL_TITLE = "original_title";
    private static final String TMD_MOVIE_ID = "id";
    private static final String TMD_ORIGINAL_LANGUAGE = "original_language";
    private static final String TMD_VOTE_AVERAGE = "vote_average";


    //this variable holds the error code if there is any problem in fulfilling the request
    private static final String TMD_MESSAGE_CODE = "status_message";

    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_LOGO_SIZE = "w185/";
    public static final String POSTER_SIZE = "w300/";

    public static MovieEntry[] getSimpleMovieArrayListFromJson(String moviesJsonString, int movieType)
            throws JSONException {

        /* String array list to hold each movie String */
        MovieEntry[] parsedMovieData;

        JSONObject moviesJson = new JSONObject(moviesJsonString);
        /* Is there an error? */
        if (moviesJson.has(TMD_MESSAGE_CODE)) {
            int errorCode = moviesJson.getInt(TMD_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray movieArray = moviesJson.getJSONArray(TMD_RESULTS);

        parsedMovieData = new MovieEntry[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {

            /* These are the values that will be collected */
            String title;
            String overview;
            int voteCount;
            String releaseDate;
            int popularity;
            String posterPath;
            String originalTitle;
            int movieId;
            double voteAverage;
            String originalLanguage;

            /* Get the JSON object representing the movie */
            JSONObject movieDetail = movieArray.getJSONObject(i);

            title = movieDetail.getString(TMD_TITLE);
            overview = movieDetail.getString(TMD_OVERVIEW);
            voteCount = movieDetail.getInt(TMD_VOTE_COUNT);
            releaseDate = movieDetail.getString(TMD_RELEASE_DATE);
            popularity = movieDetail.getInt(TMD_POPULARITY);
            posterPath = movieDetail.getString(TMD_POSTER_PATH);
            originalTitle = movieDetail.getString(TMD_ORIGINAL_TITLE);
            movieId = movieDetail.getInt(TMD_MOVIE_ID);
            originalLanguage = movieDetail.getString(TMD_ORIGINAL_LANGUAGE);
            voteAverage = movieDetail.getDouble(TMD_VOTE_AVERAGE);

            parsedMovieData[i] = new MovieEntry(movieId,
                    voteCount,
                    voteAverage,
                    title,
                    popularity,
                    posterPath,
                    originalLanguage,
                    originalTitle,
                    overview,
                    releaseDate,
                    movieType);
        }

        return parsedMovieData;
    }

    private static final String TMD_TRAILER_ID = "id";
    private static final String TMD_TRAILER_KAY = "key";
    private static final String TMD_TRAILER_NAME = "name";
    // --Commented out by Inspection (30-01-2018 13:20):private static final String TMD_TRAILER_SITE = "site";
    private static final String TMD_TRAILER_TYPE = "type";

    public static List<MovieTrailer> getSimpleMovieTrailerArrayListFromJson(String trailerJsonString)
            throws JSONException {

//         String array list to hold each movie String
        ArrayList<MovieTrailer> movieTrailers;

//         Is there an error?
        JSONObject trailerJson = new JSONObject(trailerJsonString);
        if (trailerJson.has(TMD_MESSAGE_CODE)) {
            int errorCode = trailerJson.getInt(TMD_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
//                     Location invalid
                    return null;
                default:
//                     Server probably down
                    return null;
            }
        }

        JSONArray trailerArray = trailerJson.getJSONArray(TMD_RESULTS);

        movieTrailers = new ArrayList<>();

        for (int i = 0; i < trailerArray.length(); i++) {

//             These are the values that will be collected
            String id;
            String key;
            String name;
            String type;

//             Get the JSON object representing the movie
            JSONObject movieDetail = trailerArray.getJSONObject(i);

            id = movieDetail.getString(TMD_TRAILER_ID);
            key = movieDetail.getString(TMD_TRAILER_KAY);
            name = movieDetail.getString(TMD_TRAILER_NAME);
            type = movieDetail.getString(TMD_TRAILER_TYPE);

            movieTrailers.add(new MovieTrailer(id, key, name, type));
        }

        return movieTrailers;
    }

    private static final String TMD_REVIEW_AUTHOR = "author";
    private static final String TMD_REVIEW_CONTENT = "content";

    public static List<MovieReview> getSimpleMovieReviewArrayListFromJson(String reviewJsonString)
            throws JSONException {

        JSONObject reviewJson = new JSONObject(reviewJsonString);

//         String array list to hold each movie String
        ArrayList<MovieReview> movieReviews;

//         Is there an error?
        if (reviewJson.has(TMD_MESSAGE_CODE)) {
            int errorCode = reviewJson.getInt(TMD_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
//                     Location invalid
                    return null;
                default:
//                     Server probably down
                    return null;
            }
        }

        JSONArray reviewArray = reviewJson.getJSONArray(TMD_RESULTS);

        movieReviews = new ArrayList<>();

        for (int i = 0; i < reviewArray.length(); i++) {

//             These are the values that will be collected
            String author;
            String content;

//             Get the JSON object representing the movie
            JSONObject movieDetail = reviewArray.getJSONObject(i);

            author = movieDetail.getString(TMD_REVIEW_AUTHOR);
            content = movieDetail.getString(TMD_REVIEW_CONTENT);

            movieReviews.add(new MovieReview(author, content));
        }

        return movieReviews;
    }

}
