package com.example.huangshan.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.example.huangshan.bean.Admin;
import com.example.huangshan.fragment.AdminLoginFragment;
import com.example.huangshan.view.CustomVideoView;
import com.example.huangshan.R;
import com.example.huangshan.fragment.UserLoginFragment;

/**
 * 登录  Activity
 */
public class LoginActivity extends BaseActivity {

    private CustomVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences preferences= getSharedPreferences("admin", MODE_PRIVATE);
        String adminAccount = (String) preferences.getString("adminAccount",null);
        //若是缓存中有之前用户登录的信息，则不用加载Fragment去网络请求，直接拿出缓存中的数据就行了
        //这时候就不会经过Login界面，而是直接进入Main界面
        if (adminAccount != null){
            String adminPassword = (String) preferences.getString("adminPassword",null);
            String adminName = (String) preferences.getString("adminName",null);
            String adminSex = (String) preferences.getString("adminSex",null);
            int adminAge = Integer.parseInt((String) preferences.getString("adminAge",null));
            int adminWorkYear = Integer.parseInt((String) preferences.getString("adminWorkYear",null));
            String adminPhone = (String) preferences.getString("adminPhone",null);
            String adminIntroduction = (String) preferences.getString("adminIntroduction",null);
            int adminPower = Integer.parseInt((String) preferences.getString("adminPower",null));

            Admin admin = new Admin(adminAccount,adminPassword,adminName,adminSex,adminAge,adminWorkYear,adminPhone,adminIntroduction,adminPower);
            Bundle bundle = new Bundle();
            bundle.putSerializable("currentAdmin",admin);
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            //缓存中没有用户信息，那就要执行登录界面了
            //显示第三方登录界面  第一个fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //UserLoginFragment fragment = new UserLoginFragment();//添加第三方的fragment
            AdminLoginFragment fragment = new AdminLoginFragment();
            fragmentTransaction.add(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
            //初始化背景视频
            initVideoView();
        }




    }

    private void initVideoView(){
        videoView = (CustomVideoView)findViewById(R.id.login_video_bg);
        videoView.setVideoURI(Uri.parse("android.resource://com.example.huangshan/"+R.raw.login_background));
//        播放
        videoView.start();
//        循环播放
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        ActivityCollector.removeActivity(this);
    }

    //返回直接退出
    @Override
    protected void onRestart() {
        super.onRestart();
        initVideoView();
//        this.finish();
    }

    //防止锁屏或者切出的时候，音乐在播放
//    @Override
//    protected void onStop() {
//        super.onStop();
//        videoView.stopPlayback();
//    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        finishAndRemoveTask();
//    }
}