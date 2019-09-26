package com.example.huangshan.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.huangshan.Constant;
import com.example.huangshan.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminsMapViewActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener,View.OnClickListener {

    @BindView(R.id.admins_map_view_back_btn)
    ImageView adminMapBackBtn;
    @BindView(R.id.admins_map_view)
    MapView mapView;

    private static final String TAG = "AdminsMapViewActivity";
    private AMap aMap;
    private UiSettings uiSettings;
    private PoiSearch.Query query;
    private PoiSearch search;
    private ArrayList<PoiItem> poiItems = new ArrayList<>();


    //定位需要的声明
    private AMapLocationClient mLocationClient = null;                //定位发起端
    private AMapLocationClientOption mLocationOption = null;          //定位参数
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_map_view);
//        绑定控件
        ButterKnife.bind(this);
//        设置响应
        adminMapBackBtn.setOnClickListener(this);

        mapView.onCreate(savedInstanceState);

        initMapView(savedInstanceState);


    }

    /**
     * 初始化地图
     */
    private void initMapView(Bundle savedInstanceState) {

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
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);
        myLocationStyle.strokeColor(Color.argb(0,0,0,0));//精度圈 圈框隐藏
        myLocationStyle.radiusFillColor(Color.argb(0,0,0,0));//精度圈 圈范围隐藏
        aMap.setMyLocationStyle(myLocationStyle);
//        设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        aMap.setMyLocationEnabled(true);

        searchPOI();
    }

    /**
     * 通过POI检索出黄山的景点
     */
    private void searchPOI(){
        // 参数一： 表示搜索字符串，
        //参数二 ：表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码
        //参数三 ：表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query = new PoiSearch.Query("",Constant.POI_SCENERY,"黄山");
//        设置每页最多返回多少条poiitem,和当前页码
        query.setPageSize(10);
        query.setPageNum(0);
        search = new PoiSearch(this,query);
        search.setOnPoiSearchListener(this);
//        设置周边范围
        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(Constant.HUANGSHAN_LATITUDE,Constant.HUANGSHAN_LATITUDE),5000));
        search.searchPOIAsyn();
        Log.d(TAG,"001");
    }

    /**
     * 以下为OnPoiSearchListener 接口要求重写的方法
     * onPoiSearched 为关键字检索、周边检索、多边形内检索 才写
     * onPoiItemSearched 为ID检索 才写
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000){
            poiItems = poiResult.getPois();
            if (poiItems != null){
                Log.d(TAG,"002,"+poiResult.getPageCount()+","+poiResult.getPois().size());
                drawMarkers();
            }else{
                Log.d(TAG,"POI检索五结果");
            }
        }else{
            Log.d(TAG,"POI检索错误，错误码为： "+ i);
            Toast.makeText(AdminsMapViewActivity.this,"景点信息获取错误，请稍后再试",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    /**
     *
     * 画自定义的 Marker
     */
    private void drawMarkers(){
        Log.d(TAG,"003,"+poiItems.size());
        for (int i =0;i < poiItems.size();i++){
            MarkerOptions markerOptions = new MarkerOptions();
            PoiItem poiItem = poiItems.get(i);
            LatLonPoint latLonPoint = poiItem.getLatLonPoint();//获得每一个检索结果的中心点
            LatLng latLng = new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude());
            markerOptions.position(latLng);// Marker的位置
            markerOptions.title(poiItem.getAdName());// Marker的标题
            markerOptions.snippet(poiItem.getAdName()+","+poiItem.getLatLonPoint().getLatitude()+","+poiItem.getLatLonPoint().getLongitude());//Marker的内容
            markerOptions.draggable(true);//设置可拖动
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.admin_mapview_icon)));
            markerOptions.setFlat(true);//设置marker平贴地图效果
            Log.d(TAG,poiItem.getAdName()+","+poiItem.getLatLonPoint().getLatitude()+","+poiItem.getLatLonPoint().getLongitude());
        }
    }

//    /**
//     * 逆地址编码 ：通过经纬度获得地区的详细描述
//     * GeocodeSearch.OnGeocodeSearchListener 接口要求重写的两个方法之一
//     * @param regeocodeResult
//     * @param i
//     */
//    @Override
//    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//
//    }
//
//    /**
//     *   地址编码 ：通过输入地址信息获得经纬度
//     * GeocodeSearch.OnGeocodeSearchListener 接口要求重写的两个方法之一
//     * @param geocodeResult 解析获得坐标信息
//     * @param i  返回码 1000为成功
//     */
//    @Override
//    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
//        if (i == 1000){
//            int size = geocodeResult.getGeocodeAddressList().size();
//            double latitude = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude();
//            double longititude = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude();
//            Log.d(TAG,latitude+","+longititude+","+size);
//        }else{
//            Toast.makeText(this,"获取地址信息错误",Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.admins_map_view_back_btn:
                finish();
                break;

                default:break;
        }
    }

    /**
     * 以下重写的方法是控制地图的生命周期，因为地图占的内存相当大
     * <p>
     * 官方文档要求重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }



}
