package com.example.huangshan.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.huangshan.R;
import com.example.huangshan.activity.AdminsMapViewActivity;
import com.example.huangshan.activity.WeatherActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainActivity中首页的fragment
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    private View view;

    @BindView(R.id.weather) Button weatherBtn;
    @BindView(R.id.admins_map_view_btn) Button adminsMapViewBtn;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        加载HomeFragment布局
        view = inflater.inflate(R.layout.fragment_main_home,container,false);
//        绑定控件
        ButterKnife.bind(this,view);
//        设置响应
        weatherBtn.setOnClickListener(this);
        adminsMapViewBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weather:
                Intent intent = new Intent(getActivity(), WeatherActivity.class);
                startActivity(intent);
                break;
            case R.id.admins_map_view_btn:
                Intent intent1 = new Intent(getActivity(), AdminsMapViewActivity.class);
                startActivity(intent1);
                break;
                default:
                    break;
        }
    }
}
