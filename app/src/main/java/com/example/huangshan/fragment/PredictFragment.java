package com.example.huangshan.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.example.huangshan.activity.WeatherH5Activity;
import com.heweather.plugin.view.HeContent;
import com.heweather.plugin.view.HeWeatherConfig;
import com.heweather.plugin.view.LeftLargeView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PredictFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "PredictFragment";
    private View view;

    @BindView(R.id.heweather_plugin_ll_view)
    LeftLargeView leftLargeView;

    public PredictFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载布局
        view = inflater.inflate(R.layout.fragment_predict,container,false);
        //绑定控件
        ButterKnife.bind(this,view);
        //显示heweather插件
        showHeWeatherPlugin();


        return view;
    }

    private void showHeWeatherPlugin() {
        //初始化
        HeWeatherConfig.init(Constant.HEFENGWEATHER_PLUGIN_kEY,Constant.HEFENGWEATHER_LOCATION_HUANGSHAN_GUANGMINGDING);

        //显示
        //获取左侧大布局
        LinearLayout leftLayout = leftLargeView.getLeftLayout();
        //获取右上布局
        LinearLayout rightTopLayout = leftLargeView.getRightTopLayout();
        //获取右下布局
        LinearLayout rightBottomLayout = leftLargeView.getRightBottomLayout();

        //设置布局的背景圆角角度（单位：dp），颜色，边框宽度（单位：px），边框颜色
        leftLargeView.setStroke(10, Color.parseColor("#313a44"), 1, Color.BLACK);

        //添加温度描述到左侧大布局
        //第一个参数为需要加入的布局
        //第二个参数为文字大小，单位：sp
        //第三个参数为文字颜色，默认白色
        leftLargeView.addTemp(leftLayout, 40, Color.WHITE);
        //添加温度图标到右上布局，第二个参数为图标宽高（宽高1：1，单位：dp）
        leftLargeView.addWeatherIcon(rightTopLayout, 14);
        //添加预警图标到右上布局
        leftLargeView.addAlarmIcon(rightTopLayout, 14);
        //添加预警描述到右上布局
        leftLargeView.addAlarmTxt(rightTopLayout, 14);
        //添加文字AQI到右上布局
        leftLargeView.addAqiText(rightTopLayout, 14);
        //添加空气质量到右上布局
        leftLargeView.addAqiQlty(rightTopLayout, 14);
        //添加空气质量数值到右上布局
        leftLargeView.addAqiNum(rightTopLayout, 14);
        //添加地址信息到右上布局
        leftLargeView.addLocation(rightTopLayout, 14, Color.WHITE);
        //添加天气描述到右下布局
        leftLargeView.addCond(rightBottomLayout, 14, Color.WHITE);
        //添加风向图标到右下布局
        leftLargeView.addWindIcon(rightBottomLayout, 14);
        //添加风力描述到右下布局
        leftLargeView.addWind(rightBottomLayout, 14, Color.WHITE);
        //添加降雨图标到右下布局
        leftLargeView.addRainIcon(rightBottomLayout, 14);
        //添加降雨描述到右下布局
        leftLargeView.addRainDetail(rightBottomLayout, 14, Color.WHITE);
        //设置控件的对齐方式，默认居中
        leftLargeView.setViewGravity(HeContent.GRAVITY_LEFT);
        //显示布局
        leftLargeView.show();

        leftLargeView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.heweather_plugin_ll_view:
                Intent intent = new Intent(getActivity(), WeatherH5Activity.class);
                startActivity(intent);
                break;
                default:break;
        }
    }
}