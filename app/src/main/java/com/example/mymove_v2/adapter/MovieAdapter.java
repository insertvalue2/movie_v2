package com.example.mymove_v2.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymove_v2.R;
import com.example.mymove_v2.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    List<Movie> list = new ArrayList<>();

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 통신으로 데이가 전달되면 해당 메서드로 데이터를 전달 받기
    public void addItems(List<Movie> list) {
        this.list = list;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView posterIv;
        private TextView titleTv;
        private TextView ratingTv;
        private RatingBar ratingBar;

        public MyViewHolder(View movieItemView) {
            super(movieItemView);
            posterIv = movieItemView.findViewById(R.id.posterIv);
            titleTv = movieItemView.findViewById(R.id.titleTv);
            ratingTv = movieItemView.findViewById(R.id.ratingTv);
            ratingBar = movieItemView.findViewById(R.id.ratingBar);

        }

        public void setItem(Movie movie) {
            titleTv.setText(movie.getTitle());
            ratingTv.setText(movie.getRating() + "");
            Glide.with(posterIv.getContext())
                    .load(movie.getMediumCoverImage())
                    .placeholder(R.drawable.round_image)
                    .into(posterIv);
            Log.d("TAG", "movie.getRating() : " + movie.getRating());
            Log.d("TAG", "movie.getRating() / 2 : " + Math.round(movie.getRating() / 2));

            ratingBar.setRating((float) Math.floor(movie.getRating()));
        }
    }
}
