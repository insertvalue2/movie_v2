package com.example.mymove_v2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.mymove_v2.databinding.ActivityMovieDetailBinding;
import com.example.mymove_v2.models.Movie;

public class MovieDetailActivity extends AppCompatActivity {

    private final static String TAG = MovieDetailActivity.class.getName();
    public static final String PARAM_NAME_1 = "movie obj";
    private ActivityMovieDetailBinding binding;
    private Movie movie;
    private BottomSheetFragment bottomSheetFragment;

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
        binding.runTimeTextView.setText("상영시간 : " + movie.getRuntime() + "분");

        Glide.with(this)
                .load(movie.getMediumCoverImage())
                .into(binding.moviesPoster);

        Glide.with(this)
                .load(movie.getBackgroundImage())
                .into(binding.backgroundImageView);

        bottomSheetFragment = new BottomSheetFragment(movie);
    }

    private void addEventListener() {
        binding.showContentButton.setOnClickListener(view -> {
            addFragment();
        });
    }

    private void addFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 잘 알려진 버그 out 시 애니메이션 처리 안됨 ! --> 대체 studio 에서 제공하는 Modal Bottom Sheet Fragment 를 사용해 보자. !
        transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_bottom);
        transaction.setReorderingAllowed(true);
        transaction.addToBackStack(null);
        transaction.replace(binding.bottomSheetContainer.getId(), bottomSheetFragment);
        transaction.commit();
    }
}


