package com.example.huangshan.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.huangshan.R;

/**
 * 导航栏的每一个fragment的父类
 */
public class BaseFragment extends Fragment {

    public static final String ARGS_TITLE = "args_title";
    private String mTitle;

    public BaseFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance(String title){
        Bundle args = new Bundle();
        args.putString(ARGS_TITLE, title);
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(ARGS_TITLE, "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView titleView = view.findViewById(R.id.fragment_tv);
        titleView.setText(mTitle);
    }
}