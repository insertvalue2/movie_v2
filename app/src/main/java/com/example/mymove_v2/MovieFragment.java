package com.example.mymove_v2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mymove_v2.adapter.MovieAdapter;
import com.example.mymove_v2.databinding.FragmentMovieBinding;
import com.example.mymove_v2.interfaces.OnMovieItemClicked;
import com.example.mymove_v2.interfaces.OnChangeToolbarType;
import com.example.mymove_v2.models.Movie;
import com.example.mymove_v2.models.YtsData;
import com.example.mymove_v2.repository.MovieService;
import com.example.mymove_v2.utils.Define;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieFragment extends Fragment implements OnMovieItemClicked {

    private static final String TAG = MovieFragment.class.getName();
    // binding 선언
    private FragmentMovieBinding binding;
    private static MovieFragment movieFragment;
    private MovieService service;
    private MovieAdapter adapter;
    private final OnChangeToolbarType onChangeToolbarType;
    private int currentPageNumber = 1;
    private boolean isFirstFragmentStart = true;
    // 스크롤시 중복 이벤트 해결 방안
    private boolean preventDuplicateScrollEvent = true;
    // 실수를 많이 하는 부분 조심 ! 선언과 초기화 까지 권장
    private List<Movie> movieList = new ArrayList<>();

    public static MovieFragment getInstance(OnChangeToolbarType onPageTitleChange) {
        if (movieFragment == null) {
            movieFragment = new MovieFragment(onPageTitleChange);
        }
        return movieFragment;
    }

    private MovieFragment(OnChangeToolbarType onChangeToolbarType) {
        this.onChangeToolbarType = onChangeToolbarType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // movie service 초기화
        service = MovieService.retrofit.create(MovieService.class);
        onChangeToolbarType.setupType(Define.PAGE_TITLE_MOVIE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // binding 초기화
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        setupRecyclerView(movieList);
        // 만약 데이터가 있다면
        if (isFirstFragmentStart) {
            requestMoviesData(currentPageNumber);
        } else {

            setVisibilityProgressBar(View.GONE);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * limit 현재 10개 설정
     *
     * @param requestPage 1 (10개) -> 2 (10) 앞에서 받은 10개의 다음 데이터 10개를 추가로 받아 옴
     */
    private void requestMoviesData(int requestPage) {
        String sortBy = "rating";
        int ITEM_LIMIT = 10;
        service.repoContributors(sortBy, ITEM_LIMIT, requestPage)
                .enqueue(new Callback<YtsData>() {
                    @Override
                    public void onResponse(Call<YtsData> call, Response<YtsData> response) {
                        if (response.isSuccessful()) {
                            // assert 란 개발자들이 디버깅을 빠르게 하기위한 도구이다.
                            // 즉, 에러 검출용 코드이지 코드를 다 완성하고 동작할때 돌아가는 함수가 아니다.
                            // assert 는 log 보다 더 효율적으로 사용될 수 있는데 이유는 버그가 발생한 위치, call stack 등 여러 정보를 알 수 있기 때문
                            assert response.body() != null;
                            adapter.addItem(response.body().getData().getMovies());
                            // recyclerview scroller 이벤트를 한번만 catch 해서 네트워크 동작을 받게 처리 했기때문데
                            // 다음번에도 다시 받기 위해 true 로 설정 해주어야 한다.
                            currentPageNumber++; // 다음 데이터를 받기 위해  currentPageNumber 증가 시켜 주어야 한다.
                            preventDuplicateScrollEvent = true;
                            isFirstFragmentStart = false;
                            setVisibilityProgressBar(View.GONE);

                        }
                    }

                    @Override
                    public void onFailure(Call<YtsData> call, Throwable t) {
                        Log.d(TAG, t.getMessage());
                        setVisibilityProgressBar(View.GONE);
                    }
                });

    }

    private void setVisibilityProgressBar(int isVisible) {
        binding.progressIndicator.setVisibility(isVisible);
    }

    private void setupRecyclerView(List<Movie> listData) {
        //1 어댑터 필요
        //2 매니저 필요
        //3 recyclerview 셋팅

        // 1
        adapter = new MovieAdapter();
        adapter.setOnMovieItemClicked(this);
        adapter.addItems(listData);
        // 2
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        // 3
        binding.movieRecyclerView.setAdapter(adapter);
        binding.movieRecyclerView.setLayoutManager(manager);
        binding.movieRecyclerView.hasFixedSize();
        binding.movieRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (preventDuplicateScrollEvent) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) binding.movieRecyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    int itemTotalCount = Objects.requireNonNull(binding.movieRecyclerView.getAdapter()).getItemCount() - 1;
                    if (lastVisibleItemPosition == itemTotalCount) {
                        Toast.makeText(getContext(), "마지막 위치 입니다.", Toast.LENGTH_SHORT).show();
                        if (currentPageNumber != 1) {
                            preventDuplicateScrollEvent = false;
                            requestMoviesData(currentPageNumber);
                        }
                    }
                }
            }
        });
    }

    /**
     * @param movie MovieAdapter 에서 콜백 메서드로 넘어오는 Movie(사용자가 선택한 뷰에 movie 데이터 정보)
     */
    @Override
    public void selectedItem(Movie movie) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.PARAM_NAME_1, movie);
        startActivity(intent);
    }

}




