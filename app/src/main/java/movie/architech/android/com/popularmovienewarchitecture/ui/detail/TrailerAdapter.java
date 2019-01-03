package movie.architech.android.com.popularmovienewarchitecture.ui.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import movie.architech.android.com.popularmovienewarchitecture.R;

/**
 * Created on 25-01-2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    private List<MovieTrailer> movieTrailers;
    private Context context = null;
    private final TrailerAdapterOnItemClickListener TrailerAdapterOnItemClickListener;

    public TrailerAdapter(Context ctx, TrailerAdapterOnItemClickListener trailerAdapterOnItemClickListener){
        this.TrailerAdapterOnItemClickListener = trailerAdapterOnItemClickListener;
        context = ctx;
    }


    public interface TrailerAdapterOnItemClickListener {
        void onMovieItemClickListener(MovieTrailer trailerObject);
    }

    @Override
    public TrailerAdapter.TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.trailer_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutId,parent, false);

        return new TrailerAdapter.TrailerHolder(view);

    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {

        MovieTrailer trailerObject = movieTrailers.get(position);

        Picasso.get()
                .load(trailerObject.getVideoThumbnailUrl())
                .into(holder.trailerPosterImageView);

    }

    @Override
    public int getItemCount() {
        if(movieTrailers !=null) {
            return movieTrailers.size();
        }else{
            return 0;
        }
    }

    public void setMovieTrailerData(List<MovieTrailer> movieData){
        this.movieTrailers = movieData;
        notifyDataSetChanged();
    }

    final class TrailerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView trailerPosterImageView;

        public TrailerHolder(View itemView) {
            super(itemView);
            trailerPosterImageView = itemView.findViewById(R.id.trailer_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedItemPosition = getAdapterPosition();
            TrailerAdapterOnItemClickListener.onMovieItemClickListener(movieTrailers.get(clickedItemPosition));
        }
    }

}
