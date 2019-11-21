package com.example.huangshan.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.VideoView;

import java.util.jar.Attributes;

/**
 * 用于播放登录界面的自定义控件
 */
public class CustomVideoView extends VideoView {
  public CustomVideoView(Context context){
    super(context);
  }
  public CustomVideoView(Context context, AttributeSet attributeSet){
    super(context,attributeSet);
  }

  public CustomVideoView(Context context,AttributeSet attributeSet,int defstyle){
    super(context,attributeSet,defstyle);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int width = getDefaultSize(0,widthMeasureSpec);
    int height = getDefaultSize(0,heightMeasureSpec);
    setMeasuredDimension(width,height);
  }

  @Override
  public void setOnPreparedListener(MediaPlayer.OnPreparedListener l) {
    super.setOnPreparedListener(l);
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return super.onKeyDown(keyCode, event);
  }
}