package com.example.mymove_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mymove_v2.databinding.FragmentBottomSheetBinding;
import com.example.mymove_v2.models.Movie;


public class BottomSheetFragment extends Fragment {

    private final Movie movie;

    FragmentBottomSheetBinding binding;

    public BottomSheetFragment(Movie movie) {
        this.movie = movie;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false);

        Glide.with(binding.movieImageView)
                .load(movie.getMediumCoverImage())
                .placeholder(R.drawable.round_image)
                .into(binding.movieImageView);

        binding.summaryTextView.setText(movie.getSummary());
        binding.descriptionTextView.setText(movie.getDescriptionFull());
        binding.synopsisTextView.setText(movie.getSynopsis());

        return binding.getRoot();
    }
}