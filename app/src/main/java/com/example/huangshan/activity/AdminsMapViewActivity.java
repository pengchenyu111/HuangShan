package com.example.huangshan.activity;


import android.app.ActionBar;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;

import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.example.huangshan.adapter.MyInfoWindowAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminsMapViewActivity extends BaseActivity implements PoiSearch.OnPoiSearchListener,View.OnClickListener, AMap.OnMarkerClickListener {

    @BindView(R.id.admins_map_view_back_btn)
    ImageView adminMapBackBtn;
    @BindView(R.id.admins_map_view)
    MapView mapView;

    private static final String TAG = "AdminsMapViewActivity";
    private AMap aMap;
    private UiSettings uiSettings;
    private PoiSearch.Query query;
    private PoiSearch search;
    private PoiResult poiResult;
    private MyInfoWindowAdapter adapter;
    private PopupWindow mPopupWindow;

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

        //todo 初始化：从数据库获取负责人的信息，再展示Marker和PopUpWindow

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

        searchPOI();
    }

    /**
     * 通过POI检索出黄山的景点
     */
    private void searchPOI(){
        // 参数一： 表示搜索字符串，
        //参数二 ：表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码
        //参数三 ：表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query = new PoiSearch.Query("",Constant.POI_SCENERY,Constant.HUANGSHAN_ADCODE);
//        设置每页最多返回多少条poiitem,和当前页码
        query.setPageSize(10);
        query.setPageNum(1);
        query.setCityLimit(true);
        search = new PoiSearch(this,query);
        search.setOnPoiSearchListener(this);
//        设置周边范围
//        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(Constant.HUANGSHAN_LATITUDE,Constant.HUANGSHAN_LATITUDE),5000,true));
        search.searchPOIAsyn();
    }

    /**
     * 以下为OnPoiSearchListener 接口要求重写的方法
     * onPoiSearched 为关键字检索、周边检索、多边形内检索 才写
     * onPoiItemSearched 为ID检索 才写
     */
    @Override
    public void onPoiSearched(PoiResult result, int i) {
        if (i == 1000){
           if (result !=null && result.getQuery() != null){
               if (result.getQuery().equals(query)){
                   poiResult = result;

                   List<PoiItem> poiItems = poiResult.getPois();
                   List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();
                   Log.d(TAG,"002,"+poiResult.getPageCount()+","+poiResult.getPois().size());
                   Log.d(TAG,"003,"+suggestionCities.size());

                   if (poiItems != null && poiItems.size()>0){
                       drawMarkers(poiItems);
                   }else{
                       Log.d(TAG,"POI检索无结果");
                   }
               }
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
     * 这里是根据POI检索的结果画的
     */
    private void drawMarkers(List<PoiItem> poiItems){
        Log.d(TAG,"004,"+poiItems.size());
        for (int i =0;i < poiItems.size();i++){
            MarkerOptions markerOptions = new MarkerOptions();
            PoiItem poiItem = poiItems.get(i);
            LatLonPoint latLonPoint = poiItem.getLatLonPoint();//获得每一个检索结果的中心点
            LatLng latLng = new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude());
            markerOptions.position(latLng);// Marker的位置
            markerOptions.title(poiItem.getTitle());// Marker的标题
            markerOptions.snippet(poiItem.getTitle()+","+poiItem.getLatLonPoint().getLatitude()+","+poiItem.getLatLonPoint().getLongitude());//Marker的内容
            markerOptions.draggable(false);//设置可拖动
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.maker_admin)));//加上图标
            markerOptions.setFlat(true);//设置marker平贴地图效果
            Marker marker = aMap.addMarker(markerOptions);


            Log.d(TAG,poiItem.getTitle()+","+poiItem.getLatLonPoint().getLatitude()+","+poiItem.getLatLonPoint().getLongitude());
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d(TAG,"点击了Marker");
        showPopUpWindow(marker);

        return false;
    }

    private void showPopUpWindow(Marker marker) {
        Log.d(TAG,"开始绘制popupwindow");
        //初始化PopUpWindow
        View popupwindowView = LayoutInflater.from(AdminsMapViewActivity.this).inflate(R.layout.layout_adminmap_pop,null);
        mPopupWindow = new PopupWindow(popupwindowView);
        mPopupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(getWindowManager().getDefaultDisplay().getHeight()*1/4);
        mPopupWindow.setFocusable(false);

        //加到附布局中
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

        closePop.setOnClickListener(this::onClick);
        adminInfo.setOnClickListener(this::onClick);
        callAdmin.setOnClickListener(this::onClick);

        adminName.setText("萧何");//todo
        adminManagedSpot.setText(marker.getTitle());
        adminWorkTime.setText("工作三年");//todo
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.admins_map_view_back_btn:
                finish();
                break;
            case R.id.admin_popupwindow_close:
                mPopupWindow.dismiss();
                break;

            default:break;
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


    /**
     * 以下重写的方法是控制地图的生命周期，因为地图占的内存相当大
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
