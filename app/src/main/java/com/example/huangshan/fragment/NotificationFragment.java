package com.example.huangshan.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.example.huangshan.activity.NotificationManageActivity;
import com.example.huangshan.activity.SendNotificationActivity;
import com.example.huangshan.activity.UserFeedbackManageActivity;
import com.example.huangshan.bean.Admin;
import com.example.huangshan.utils.GlideImageLoader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.lang.reflect.Field;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  第三个导航栏，服务  NotificationFragment
 */
public class NotificationFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "NotificationFragment";
    private View view;
    private Bundle bundle = new Bundle();
    private Admin currentAdmin;

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.notification_btn)
    ImageButton notificationManage;
    @BindView(R.id.notification_send)
    ImageButton sendNotification;
    @BindView(R.id.notification_userfeedback_btn)
    ImageButton userFeedbackManage;


    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //加载布局
        view = inflater.inflate(R.layout.fragment_notification,container,false);
        //绑定控件
        ButterKnife.bind(this,view);
        //设置响应
        notificationManage.setOnClickListener(this::onClick);
        sendNotification.setOnClickListener(this::onClick);
        userFeedbackManage.setOnClickListener(this::onClick);
        //设置状态栏透明化
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //拿到MainACtivity传过来的值
        bundle = this.getArguments();
        currentAdmin = (Admin) bundle.getSerializable("currentAdmin");

        //加载轮播图
        bannerSettings();

        return view;
    }

    private void bannerSettings() {
        //github地址：https://github.com/youth5201314/banner

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(Arrays.asList(Constant.URL_BANNER));
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用

        //添加监听器
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                switch (position){
                    case 0:
                        Toast.makeText(getActivity(),"点击了图片"+position,Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getActivity(),"点击了图片"+position,Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(),"点击了图片"+position,Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(),"点击了图片"+position,Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(),"点击了图片"+position,Toast.LENGTH_SHORT).show();
                        break;
                        default:break;
                }
            }
        });
        banner.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notification_btn:
                Intent intent = new Intent(getActivity(), NotificationManageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.notification_send:
                Intent intent2 = new Intent(getActivity(), SendNotificationActivity.class);
                intent2.putExtras(bundle);
                startActivity(intent2);
                break;
            case R.id.notification_userfeedback_btn:
                Intent intent1 = new Intent(getActivity(), UserFeedbackManageActivity.class);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
                default:break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
