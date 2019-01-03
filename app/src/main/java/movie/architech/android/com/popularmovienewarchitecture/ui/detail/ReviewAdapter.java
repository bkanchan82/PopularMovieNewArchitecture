package movie.architech.android.com.popularmovienewarchitecture.ui.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import movie.architech.android.com.popularmovienewarchitecture.R;

/**
 * Created on 25-01-2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<MovieReview> movieReviews;

    public ReviewAdapter(){
        super();
    }


    @Override
    public ReviewAdapter.ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutId = R.layout.review_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutId,parent, false);

        return new ReviewAdapter.ReviewHolder(view);

    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewHolder holder, int position) {

        MovieReview reviewObject = movieReviews.get(position);

        holder.authorTextView.setText(reviewObject.getUser());
        holder.reviewTextView.setText(reviewObject.getReview());

    }

    @Override
    public int getItemCount() {
        if(movieReviews !=null) {
            return movieReviews.size();
        }else{
            return 0;
        }
    }

    public void setMovieReviewData(List<MovieReview> movieData){
        this.movieReviews = movieData;
        notifyDataSetChanged();
    }

    final class ReviewHolder extends RecyclerView.ViewHolder {

        final TextView authorTextView;
        final TextView reviewTextView;

        public ReviewHolder(View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.author_tv);
            reviewTextView = itemView.findViewById(R.id.review_tv);
        }
    }

}
