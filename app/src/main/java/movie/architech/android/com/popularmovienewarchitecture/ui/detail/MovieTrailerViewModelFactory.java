package movie.architech.android.com.popularmovienewarchitecture.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;

public class MovieTrailerViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    int movieId;
    Context context;

    public MovieTrailerViewModelFactory(Context ctx,int movieId){
        this.context = ctx;
        this.movieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T)new MovieTrailerViewModel(context,movieId);
    }
}
