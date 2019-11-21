package com.example.huangshan.activity;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.bumptech.glide.Glide;
import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.example.huangshan.adapter.MyInfoWindowAdapter;
import com.example.huangshan.bean.OneAdminManage;
import com.example.huangshan.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminsMapViewActivity extends BaseActivity implements View.OnClickListener, AMap.OnMarkerClickListener {

    @BindView(R.id.admins_map_view_back_btn)
    ImageView adminMapBackBtn;
    @BindView(R.id.admins_map_view)
    MapView mapView;
    @BindView(R.id.show_admin_list)
    TextView showAdminList;

    private static final String TAG = "AdminsMapViewActivity";
    private AMap aMap;
    private UiSettings uiSettings;
    private MyInfoWindowAdapter adapter;
    private PopupWindow mPopupWindow;
    private List<OneAdminManage> adminManageList = new ArrayList<>();
    private OneAdminManage currentAdminManage;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_map_view);
//        绑定控件
        ButterKnife.bind(this);
//        设置响应
        adminMapBackBtn.setOnClickListener(this::onClick);
        showAdminList.setOnClickListener(this::onClick);

        mapView.onCreate(savedInstanceState);
        bundle = savedInstanceState;

        //初始化：从数据库获取负责人的信息
        initData();

        //初始化地图
        initMapView();
    }

    private void initData() {
        String url = Constant.URL+"AllAdminsServlet";
        try {
            //发网络请求，获取String 结果集
            String result = HttpUtil.getRequest(url);

            //转成实体对象,并写到List里
            Gson gson = new Gson();
            //由于返回的数据和一个很多条管理信息组成的数组，所以不能单纯用 ：gson.fromJson(result,OneAdminManage.class)
            adminManageList = gson.fromJson(result,new TypeToken<List<OneAdminManage>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化地图
     */
    private void initMapView() {

//        获得地图
        aMap = mapView.getMap();
//        转移镜头到黄山风景区
        LatLng latLng = new LatLng(Constant.HUANGSHAN_LATITUDE,Constant.HUANGSHAN_LONGITITUDE);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng,13,0,0)));
//        实例化 UiSettings 对象
        uiSettings = aMap.getUiSettings();
//        不显示缩放按钮
        uiSettings.setZoomControlsEnabled(false);
//        显示指南针
        uiSettings.setCompassEnabled(true);
//        显示比例尺
        uiSettings.setScaleControlsEnabled(true);
//        高德地图logo放于左下角
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);
//        显示默认的定位按钮
        uiSettings.setMyLocationButtonEnabled(true);
//        设置显示自身定位的小图标
        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        LOCATION_TYPE_LOCATION_ROTATE:系统默认，连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）
//        LOCATION_TYPE_SHOW) ;定位一次
//        LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER ：连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动
//        LOCATION_TYPE_MAP_ROTATE_NO_CENTER : 同上，但地图有倾斜度，偏向于步行导航时
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.strokeColor(Color.argb(0,0,0,0));//精度圈 圈框隐藏
        myLocationStyle.radiusFillColor(Color.argb(0,0,0,0));//精度圈 圈范围隐藏

        aMap.setMyLocationStyle(myLocationStyle);
//        设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        aMap.setMyLocationEnabled(true);
//        设置自定义 infowindow
        adapter = new MyInfoWindowAdapter();
        aMap.setInfoWindowAdapter(adapter);
        aMap.setOnMarkerClickListener(this::onMarkerClick);

        drawMarkers(adminManageList);
    }


    /**
     * 画自定义的 Marker
     * 这里是根据数据库检索的结果画的
     */
    private void drawMarkers(List<OneAdminManage> adminManages){

        for (int i =0;i < adminManages.size();i++){
            MarkerOptions markerOptions = new MarkerOptions();
            OneAdminManage oneAdminManage = adminManages.get(i);

            LatLng latLng = new LatLng(Double.parseDouble(oneAdminManage.getScenicLatitude()),Double.parseDouble(oneAdminManage.getScenicLongitude()));
            markerOptions.position(latLng);// Marker的位置
            markerOptions.title(oneAdminManage.getScenicName());// Marker的标题

            //由于高德地图的marker 没有携带其他信息的方法，所以我只能将信息封装到snippet中，到时候通过split分割了
            markerOptions.snippet(oneAdminManage.getAdminName()+","+oneAdminManage.getAdminWorkYear()+","+oneAdminManage.getAdminAccount()+","+oneAdminManage.getScenicId());//Marker的内容
            markerOptions.draggable(false);//设置可拖动
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.maker_admin)));//加上图标
            markerOptions.setFlat(true);//设置marker平贴地图效果

            Marker marker = aMap.addMarker(markerOptions);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // 获取marker 的snippet中携带的内容
        String info = marker.getSnippet();
        String[] temp = info.split(",");

        //检索出当前的OneAdminMenage
        for (int i = 0, n = adminManageList.size();i< n;i++){
            if (adminManageList.get(i).getAdminAccount().equals(temp[2]) && adminManageList.get(i).getScenicId().equals(temp[3])){
                currentAdminManage = adminManageList.get(i);
                break;
            }
        }
        showPopUpWindow(currentAdminManage);
        return false;
    }

    private void showPopUpWindow(OneAdminManage adminManage) {
        // 绘制前先清除之前的popupwindow，否则内存泄漏
        if (mPopupWindow != null){
            mPopupWindow.dismiss();
        }
        //初始化PopUpWindow
        View popupwindowView = LayoutInflater.from(AdminsMapViewActivity.this).inflate(R.layout.layout_adminmap_pop,null);
        mPopupWindow = new PopupWindow(popupwindowView);
        mPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(getWindowManager().getDefaultDisplay().getHeight()*1/4);
        mPopupWindow.setFocusable(false);

        //加到父布局中
        View rootView = LayoutInflater.from(AdminsMapViewActivity.this).inflate(R.layout.activity_admins_map_view,null);
        mPopupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);

        //获取PopUpWindow中的控件
        ImageView adminHeadIcon = (ImageView) popupwindowView.findViewById(R.id.admin_head_icon);//头像
        TextView adminName = (TextView)popupwindowView.findViewById(R.id.admin_name);//负责人姓名
        TextView adminManagedSpot = (TextView)popupwindowView.findViewById(R.id.admin_managed_spot);//负责人管理的辖区
        TextView adminWorkTime = (TextView)popupwindowView.findViewById(R.id.admin_work_time);
        ImageView closePop = (ImageView)popupwindowView.findViewById(R.id.admin_popupwindow_close);//关闭按钮
        LinearLayout adminInfo = (LinearLayout)popupwindowView.findViewById(R.id.lookover_admin_info);//详情按钮
        LinearLayout callAdmin = (LinearLayout)popupwindowView.findViewById(R.id.call_admin);//打电话按钮

        //设置响应
        closePop.setOnClickListener(this::onClick);
        adminInfo.setOnClickListener(this::onClick);
        callAdmin.setOnClickListener(this::onClick);

        //显示在界面上
        Glide.with(this).load(Constant.URL_HEADICONS+"15982295274.png").into(adminHeadIcon);//todo 加载头像
        adminName.setText(adminManage.getAdminName());
        adminManagedSpot.setText(adminManage.getScenicName());
        adminWorkTime.setText("工作经验"+adminManage.getAdminWorkYear()+"年");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.admins_map_view_back_btn:
                //退出当前Activity记得关闭弹出的组件，否则内存泄漏
                if (mPopupWindow != null){
                    mPopupWindow.dismiss();
                }
                finish();
                break;
            case R.id.show_admin_list:
                // 加载进入ListAdminActivity,显示所有管理员的列表
                turnToListAdminActivity();
                break;
            case R.id.admin_popupwindow_close:
                //点击弹出窗的关闭按钮，关闭弹出窗
                mPopupWindow.dismiss();
                break;
            case R.id.call_admin:
                //弹出窗中给管理员打电话
                callAdmin();
                break;
            case R.id.lookover_admin_info:
                //弹出窗中点击获取管理员的详细信息
                turnToAdminInfo();
                break;//todo
            default:break;
        }
    }

    private void turnToAdminInfo() {
        Intent intent = new Intent(this, AdminInformationActivity.class);
        intent.putExtra("currentAdminManage",currentAdminManage);
        startActivity(intent);
    }

    private void turnToListAdminActivity() {
        Intent intent = new Intent(AdminsMapViewActivity.this, ListAdminsActivity.class);
        intent.putExtra("allAdmins", (Serializable) adminManageList);
        startActivity(intent);
    }

    private void callAdmin() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        String url = "tel:"+currentAdminManage.getAdminPhone();
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    /**
     * 以下重写的方法是控制地图的生命周期，因为地图占的内存相当大
     * 官方文档要求重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        //若 popupwindow 未关闭就退出，会导致android.view.WindowLeaked异常
        if (mPopupWindow != null){
            mPopupWindow.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onDestroy();
        mapView.onCreate(bundle);
        Log.d(TAG,"调用了一次Resume方法!");//todo 要解决刷新问题
        initData();
        Log.d(TAG,"调用了一次initData方法!");
        initMapView();
        Log.d(TAG,"调用了一次initMapView方法!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//        mapView.onSaveInstanceState(outState);
//    }

}
