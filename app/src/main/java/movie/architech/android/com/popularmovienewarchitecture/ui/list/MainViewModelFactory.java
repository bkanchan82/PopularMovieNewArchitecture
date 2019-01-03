package movie.architech.android.com.popularmovienewarchitecture.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import movie.architech.android.com.popularmovienewarchitecture.data.PopularMovieRepository;
import movie.architech.android.com.popularmovienewarchitecture.ui.list.MainActivityViewModel;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private PopularMovieRepository mRepository;
    private int mMovieType;

    public MainViewModelFactory(PopularMovieRepository repository, int movieType) {
        mRepository = repository;
        mMovieType = movieType;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(mRepository,mMovieType);
    }
}
