package com.example.mymove_v2;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymove_v2.databinding.FragmentInfoBinding;
import com.example.mymove_v2.interfaces.OnPageTitleChange;
import com.example.mymove_v2.utils.Define;

public class InfoFragment extends Fragment {

    private FragmentInfoBinding binding;
    private final OnPageTitleChange onPageTitleChange;
    private static InfoFragment infoFragment;
    private OnBackPressedCallback onBackPressedCallback;

    private InfoFragment(OnPageTitleChange onPageTitleChange) {
        this.onPageTitleChange = onPageTitleChange;
    }

    public static InfoFragment newInstance(OnPageTitleChange onPageTitleChange) {
        if (infoFragment == null) {
            infoFragment = new InfoFragment(onPageTitleChange);
        }
        return infoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPageTitleChange.reNameTitle(Define.PAGE_TITLE_YTS_INFO);
        fragmentBackPressCustom();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        WebView webView = binding.webView;
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // 웹뷰가 바로 시작했을 때 처리 하기
                binding.progressIndicator.setVisibility(View.GONE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                // HTML 해석 , CSS 해석, 자바 스크립트 처리
            }
        });
        webView.loadUrl("https://yts.mx/");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private void fragmentBackPressCustom() {
        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.d("TAG", "stub!");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onBackPressedCallback.remove();
    }
}