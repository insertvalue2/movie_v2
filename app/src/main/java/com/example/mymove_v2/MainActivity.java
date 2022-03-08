package com.example.mymove_v2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymove_v2.databinding.ActivityMainBinding;
import com.example.mymove_v2.interfaces.OnChangeToolbarType;
import com.example.mymove_v2.utils.Define;
import com.example.mymove_v2.utils.FragmentType;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnChangeToolbarType {

    // 1. data-Binding 사용하기
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 2. 추가
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initData();
    }

    private void replaceFragment(FragmentType type) {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (type == FragmentType.MOVIE) {
            fragment = MovieFragment.getInstance(this);
        } else {
            fragment = InfoFragment.newInstance(this);
        }
                                                                     // MOVIE, INFO
        transaction.replace(binding.mainContainer.getId(), fragment, type.toString());
        transaction.commit();

    }

    @SuppressLint("NonConstantResourceId")
    private void addBottomNavigationListener() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_1:
                    replaceFragment(FragmentType.MOVIE);
                    break;
                case R.id.page_2:
                    replaceFragment(FragmentType.INFO);
                    break;
            }
            return true;
        });
    }

    private void initData() {
        replaceFragment(FragmentType.MOVIE);
        addBottomNavigationListener();
    }

    @Override
    public void setupType(String title) {
        if (title.equals(Define.PAGE_TITLE_YTS_INFO)) {
            binding.topAppBar.setVisibility(View.GONE);
        } else {
            binding.topAppBar.setTitle(title);
            binding.topAppBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        //onBackPressed 이벤트시 프래그먼트 TAG 속성으로 현재 활성화 된 프래그먼트 찾기
        String fragmentTAG = Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag(FragmentType.INFO.toString())).getTag();
        if (fragmentTAG.equals(FragmentType.INFO.toString())) {
            replaceFragment(FragmentType.MOVIE);
        } else {
            super.onBackPressed();
        }
    }
}