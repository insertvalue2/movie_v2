package com.example.mymove_v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.mymove_v2.databinding.ActivityMainBinding;
import com.example.mymove_v2.utils.FragmentType;

public class MainActivity extends AppCompatActivity {

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
            fragment = MovieFragment.getInstance();
        } else {
            fragment = InfoFragment.newInstance();
        }
        transaction.replace(binding.mainContainer.getId(), fragment);
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
}