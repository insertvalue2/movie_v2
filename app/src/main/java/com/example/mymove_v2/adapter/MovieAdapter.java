package com.example.mymove_v2.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.mymove_v2.R;
import com.example.mymove_v2.interfaces.OnMovieItemClicked;
import com.example.mymove_v2.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> list = new ArrayList<>();
    private OnMovieItemClicked onMovieItemClicked;

    public void setOnMovieItemClicked(OnMovieItemClicked onMovieItemClicked) {
        this.onMovieItemClicked = onMovieItemClicked;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View movieItemView = inflater.inflate(R.layout.item_movie_card, parent, false);
        return new MyViewHolder(movieItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Movie movie = list.get(position);
        holder.setItem(movie);
        // add event listener = 각각에 아이템 뷰
        holder.movieItemView.setOnClickListener(view -> {
            onMovieItemClicked.selectedItem(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 통신으로 데이가 전달되면 해당 메서드로 데이터를 전달 받기

    /**
     *
     * 프래그먼트가 다시 돌아 올때 통신으로 받았던 movie list 를 그대로 사용
     * ex ) movieFragment --> infoFragment --> movieFragment
     * @param list
     */
    public void addItems(List<Movie> list) {
        this.list = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addEachItem(Movie movie) {
        this.list.add(movie);
        this.notifyItemChanged(list.size() - 1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(List<Movie> addList) {
        this.list.addAll(list.size(), addList);
        //notifyItemRangeChanged();
        notifyDataSetChanged();
    }


    // inner static class
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private View movieItemView;
        private final ImageView posterIv;
        private final TextView titleTv;
        private final TextView ratingTv;
        private final RatingBar ratingBar;
        private OnMovieItemClicked onMovieItemClicked;

        public MyViewHolder(View movieItemView) {
            super(movieItemView);
            this.movieItemView = movieItemView;

            posterIv = movieItemView.findViewById(R.id.posterIv);
            titleTv = movieItemView.findViewById(R.id.titleTv);
            ratingTv = movieItemView.findViewById(R.id.ratingTv);
            ratingBar = movieItemView.findViewById(R.id.ratingBar);
        }

        public void setItem(Movie movie) {
            titleTv.setText(movie.getTitle());
            ratingTv.setText(String.valueOf(movie.getRating()));
            Glide.with(posterIv.getContext())
                    .load(movie.getMediumCoverImage())
                    .placeholder(R.drawable.round_image)
                    .transform(new FitCenter(), new RoundedCorners(20))
                    .into(posterIv);
            //Log.d("TAG", "movie.getRating() : " + movie.getRating());
            //Log.d("TAG", "movie.getRating() / 2 : " + Math.round(movie.getRating() / 2));
            ratingBar.setRating((float) Math.floor(movie.getRating()));
        }
    }
}
