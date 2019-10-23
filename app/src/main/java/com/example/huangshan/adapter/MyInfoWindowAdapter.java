package com.example.huangshan.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.example.huangshan.R;
import com.example.huangshan.activity.BaseApplication;


/**
 * 地图上自定义  infowindow  的适配器
 */
public class MyInfoWindowAdapter implements AMap.InfoWindowAdapter {

    private Context context = BaseApplication.getInstance().getApplicationContext();
    private LatLng latLng;
    private String snippet;
    private String adInfo;
    private static final String TAG = "MyInfoWindowAdapter";

    @Override
    public View getInfoWindow(Marker marker) {
//        获得marker内的内容
        latLng = marker.getPosition();
        snippet = marker.getSnippet();
        adInfo = marker.getTitle();
        Log.d(TAG,adInfo+","+snippet+","+latLng.latitude+","+latLng.longitude);

//        加载infowindow的布局
        View view = LayoutInflater.from(context).inflate(R.layout.layout_infowindow,null);
        TextView title = (TextView)view.findViewById(R.id.infowindow_title);
        TextView adminName = (TextView)view.findViewById(R.id.infowindow_adminName);
        title.setText(adInfo);//todo 数据库设置
        adminName.setText("彭陈宇");//todo 数据库获取负责人的信息

        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
