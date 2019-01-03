package movie.architech.android.com.popularmovienewarchitecture.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MovieEntry.class} , version = MovieDatabase.DATABASE_VERSION , exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase{

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "the_movie_db";

    private static final Object LOCK = new Object();

    private static MovieDatabase sInstance;

    public static MovieDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                if (sInstance == null){
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class,
                            DATABASE_NAME)
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();




}
