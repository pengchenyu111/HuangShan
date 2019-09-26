package com.example.huangshan.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.example.huangshan.CustomVideoView;
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

//        显示第三方登录界面  第一个fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UserLoginFragment fragment = new UserLoginFragment();//添加第三方的fragment
        fragmentTransaction.add(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
//      初始化背景视频
        initVideoView();


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


//    返回重启加载
    @Override
    protected void onRestart() {
        super.onRestart();
        initVideoView();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        super.onStop();
        videoView.stopPlayback();
    }



}
