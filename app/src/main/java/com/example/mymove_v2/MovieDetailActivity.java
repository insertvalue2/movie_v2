package com.example.mymove_v2;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mymove_v2.databinding.ActivityMovieDetailBinding;
import com.example.mymove_v2.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private final static String TAG = MovieDetailActivity.class.getName();
    public static final String PARAM_NAME_1 = "movie obj";
    private ActivityMovieDetailBinding binding;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            movie = (Movie) getIntent().getSerializableExtra(PARAM_NAME_1);
            initData();
            addEventListener();
        }
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        binding.titleTextView.setText(movie.getTitle());
        // String.format -> 함수를 만들어서 도전해보기 !
        binding.yearTextView.setText("제작년도 : " + movie.getYear() + "년");
        binding.runTimeTextView.setText("상영시간 : "  +movie.getRuntime() + "분");

        Glide.with(this)
                .load(movie.getMediumCoverImage())
                .into(binding.moviesPoster);

        Glide.with(this)
                .load(movie.getBackgroundImage())
                .into(binding.backgroundImageView);

    }

    private void addEventListener() {
        binding.showContentButton.setOnClickListener(view -> {
            Log.d(TAG, "show content click ");
        });
    }
}