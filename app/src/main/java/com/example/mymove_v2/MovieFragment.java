package com.example.mymove_v2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mymove_v2.adapter.MovieAdapter;
import com.example.mymove_v2.databinding.FragmentMovieBinding;
import com.example.mymove_v2.models.Data;
import com.example.mymove_v2.models.Movie;
import com.example.mymove_v2.models.YtsData;
import com.example.mymove_v2.repository.MovieService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieFragment extends Fragment {

    private static final String TAG = "MovieFragment";
    // binding 선언
    FragmentMovieBinding binding;
    MovieService service;


    private static MovieFragment movieFragment;

    public static MovieFragment getInstance() {
        if (movieFragment == null) {
            movieFragment = new MovieFragment();
        }
        return movieFragment;
    }

    private MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // movie service 초기화
        service = MovieService.retrofit.create(MovieService.class);

        // todo test code 삭제 요망
//        service.repoContributors("rating", 1, 1)
//                .enqueue(new Callback<YtsData>() {
//                    @Override
//                    public void onResponse(Call<YtsData> call, Response<YtsData> response) {
//                        Log.d(TAG, "status code : " + response.code());
//                    }
//
//                    @Override
//                    public void onFailure(Call<YtsData> call, Throwable t) {
//                        Log.d(TAG, TAG +" : " + t.getMessage());
//                    }
//                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // binding 초가회
        binding = FragmentMovieBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestMoviesData();
    }

    private void requestMoviesData() {
        service.repoContributors("rating", 10, 1)
                .enqueue(new Callback<YtsData>() {
                    @Override
                    public void onResponse(Call<YtsData> call, Response<YtsData> response) {
                        Log.d(TAG, "status code : " + response.code());
                        if (response.isSuccessful()) {
                            YtsData ytsData = response.body();
                            assert ytsData != null;
                            if (ytsData.getData() != null) {
                                Data data = ytsData.getData();
                                setupRecyclerView(data.getMovies());
                            }
                        } else {
                            Log.d(TAG, TAG + " : " + response.errorBody());
                        }
                        binding.progressIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<YtsData> call, Throwable t) {
                        Log.d(TAG, TAG + " : " + t.getMessage());
                    }
                });
    }

    private void setupRecyclerView(List<Movie> movieList) {
        //1 어댑터 필요
        //2 매니저 필요
        //3 recyclerview 셋팅

        // 1
        MovieAdapter adapter = new MovieAdapter();
        adapter.addItems(movieList);
        // 2
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        // 3
        binding.movieRecyclerView.setAdapter(adapter);
        binding.movieRecyclerView.setLayoutManager(manager);
        binding.movieRecyclerView.hasFixedSize();
    }

}




