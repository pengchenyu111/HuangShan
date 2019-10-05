package com.example.huangshan.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.huangshan.R;
import com.example.huangshan.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity中  我  的fragment
 */
public class AccountManageFragment extends Fragment implements View.OnClickListener{

    @BindView(R.id.test_me) Button loginTest;
    private View view;

    public AccountManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        加载布局
        view = inflater.inflate(R.layout.fragment_main_accountmanage,container,false);
//        绑定控件
        ButterKnife.bind(this,view);
//        设置事件响应
        loginTest.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_me:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
        }
    }
}
