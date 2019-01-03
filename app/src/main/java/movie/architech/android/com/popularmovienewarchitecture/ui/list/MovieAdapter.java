package movie.architech.android.com.popularmovienewarchitecture.ui.list;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import movie.architech.android.com.popularmovienewarchitecture.R;
import movie.architech.android.com.popularmovienewarchitecture.data.database.MovieEntry;

import static android.support.constraint.Constraints.TAG;

public class MovieAdapter extends PagedListAdapter<MovieEntry, MovieAdapter.MovieViewHolder> {

    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_LOGO_SIZE = "w185/";
    public static final String POSTER_SIZE = "w300/";

    private OnMovieSelectListener mCallback;

    protected MovieAdapter(OnMovieSelectListener onMovieSelectListener) {
        super(DIFF_CALLBACK);
        mCallback = onMovieSelectListener;
    }

    public interface OnMovieSelectListener{
        void onMovieClickEvent(MovieEntry movieEntry);
    }

    private static DiffUtil.ItemCallback<MovieEntry> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieEntry>() {
                // Concert details may have changed if reloaded from the database,
                // but ID is fixed.
                @Override
                public boolean areItemsTheSame(MovieEntry oldConcert, MovieEntry newConcert) {
                    return oldConcert.getId() == newConcert.getId();
                }

                @Override
                public boolean areContentsTheSame(MovieEntry oldConcert,
                                                  MovieEntry newConcert) {
                    return oldConcert.equals(newConcert);
                }
            };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        int layoutId = R.layout.movie_item;
        boolean shouldAttachToTheParentImidiaty = false;
        View view = LayoutInflater.from(context).inflate(layoutId,viewGroup,shouldAttachToTheParentImidiaty);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder,
                                 int position) {
        MovieEntry movieEntry = getItem(position);
        if (movieEntry != null) {


//            Log.d(TAG,"Poster Path : "+movieEntry.getPosterPath());
            Picasso.get()
                    .load(POSTER_BASE_URL+POSTER_SIZE+movieEntry.getPosterPath())
                    .into(holder.posterIv);
            holder.tvMovieName.setText(movieEntry.getMovieTitle());
        } else {
            // Null defines a placeholder item - PagedListAdapter automatically
            // invalidates this row when the actual object is loaded from the
            // database.
//            holder.clear();
        }
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView posterIv;
        TextView tvMovieName;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterIv = (ImageView) itemView.findViewById(R.id.poster_image_view);
            tvMovieName = (TextView)itemView.findViewById(R.id.movie_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MovieEntry movieEntry = getItem(getAdapterPosition());
            mCallback.onMovieClickEvent(movieEntry);
        }
    }

}
