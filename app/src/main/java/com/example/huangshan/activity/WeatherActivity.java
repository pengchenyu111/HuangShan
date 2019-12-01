package com.example.huangshan.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.example.huangshan.utils.GetResourseIDUtil;
import com.example.huangshan.utils.HttpUtil;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import interfaces.heweather.com.interfacesmodule.bean.Code;
import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNow;
import interfaces.heweather.com.interfacesmodule.bean.air.now.AirNowCity;
import interfaces.heweather.com.interfacesmodule.bean.basic.Basic;
import interfaces.heweather.com.interfacesmodule.bean.basic.Update;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.Forecast;
import interfaces.heweather.com.interfacesmodule.bean.weather.forecast.ForecastBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.Lifestyle;
import interfaces.heweather.com.interfacesmodule.bean.weather.lifestyle.LifestyleBase;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.NowBase;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


public class WeatherActivity extends BaseActivity implements View.OnClickListener {

    //    titleEditText
    @BindView(R.id.weather_title_backbtn)
    ImageView backBtn;
    @BindView(R.id.weather_title)
    TextView title;
    @BindView(R.id.weather_title_update_time)
    TextView updateTime;
    //    now
    @BindView(R.id.weather_now_degree)
    TextView degree;
    @BindView(R.id.weather_now_info)
    TextView info;
    //    tips
    @BindView(R.id.weather_air)
    TextView airNum;
    @BindView(R.id.weather_rain)
    TextView rain;

    //    forecast 在下面写否则要出错
    @BindView(R.id.forecast_layout)
    LinearLayout linearLayout;
    //    lifestyle
    @BindView(R.id.weather_life_comf)
    TextView comf;
    @BindView(R.id.weather_life_flu)
    TextView flu;
    @BindView(R.id.weather_life_uv)
    TextView uv;
    @BindView(R.id.weather_life_sport)
    TextView sport;
    @BindView(R.id.weather_life_trav)
    TextView trav;
    @BindView(R.id.weather_life_air)
    TextView air;
    //    背景图片
    @BindView(R.id.weather_bcg_pic)
    ImageView bcgPic;
    //下拉刷新
    @BindView(R.id.weather_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private static final String TAG = "WeatherActivity";
    private NowBase nowBase;
    private Basic basic;
    private Update update;
    private List<ForecastBase> forecastBases;
    private List<LifestyleBase> lifestyleBases;
    private AirNowCity airNowCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        设置状态栏透明化
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
//        加载布局
        setContentView(R.layout.activity_weather);
//        绑定控件
        ButterKnife.bind(this);
//        设置响应
        backBtn.setOnClickListener(this);

//        和风天气账户初始化
        HeConfig.init(Constant.HEFENGWEATHER_ID, Constant.HEFENGWEATHER_key);
        HeConfig.switchToCNBusinessServerNode();

//        读取缓存
        SharedPreferences preferences = getSharedPreferences("weather", Context.MODE_PRIVATE);
        String bingPicString = preferences.getString("BING_PIC", null);
//         加载背景图片
        if (bingPicString != null) {
            Glide.with(this).load(bingPicString).into(bcgPic);
        } else {
            loadPic();
        }
//      获取天气数据
        getWeatherData();

//        下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeatherData();
                loadPic();
                Toast.makeText(WeatherActivity.this, "更新天气成功！", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    /**
     * 加载必应每日一图来当背景
     */
    private void loadPic() {
        try {
//            加载
            String result = HttpUtil.getRequest(Constant.BING_PIC);
            Glide.with(this).load(result).into(bcgPic);
//            存入缓存
            SharedPreferences.Editor editor = getSharedPreferences("weather", MODE_PRIVATE).edit();
            editor.putString("BING_PIC", result);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取天气数据
     */
    private void getWeatherData() {
//       获取now
        HeWeather.getWeatherNow(WeatherActivity.this, Constant.HEFENGWEATHER_LOCATION_HUANGSHAN_GUANGMINGDING, Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i(TAG, "Weather Now onError: ", throwable);
            }

            @Override
            public void onSuccess(Now now) {
                Log.i(TAG, " Weather Now onSuccess: " + new Gson().toJson(now));
                Log.d(TAG, new Gson().toJson(now));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK.getCode().equalsIgnoreCase(now.getStatus())) {
                    //此时返回数据
                    nowBase = now.getNow();
                    basic = now.getBasic();
                    update = now.getUpdate();
                    //        titleEditText
                    title.setText(basic.getLocation());
                    updateTime.setText(update.getLoc().split(" ")[1]);
                    //        now
                    degree.setText(nowBase.getTmp() + "℃");
                    info.setText(nowBase.getCond_txt());
                    //        tips

//                    Log.d(TAG, basic.getLocation() + "," + update.getLoc() + "," + nowBase.getTmp() + "," + nowBase.getCond_txt() + "," + nowBase.getWind_sc());
                } else {
                    //在此查看返回数据失败的原因
                    String status = now.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });

//        获取forecast
        HeWeather.getWeatherForecast(WeatherActivity.this, "CN101221008", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherForecastBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i(TAG, "Weather Forecast onError", throwable);
            }

            @Override
            public void onSuccess(Forecast forecast) {
                Log.i(TAG, " Weather Forecast onSuccess: " + new Gson().toJson(forecast));
                if (Code.OK.getCode().equalsIgnoreCase(forecast.getStatus())) {
                    forecastBases = forecast.getDaily_forecast();
                    //         tips
                    rain.setText(forecastBases.get(0).getPop() + "%");
                    //        forecast
                    linearLayout.removeAllViews();
                    for (int i = 1; i <= 5; i++) {
                        View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.weather_forecast_item, linearLayout, false);
                        //    forecast
                        TextView forecastDate = (TextView) view.findViewById(R.id.weather_forecast_date);
                        TextView condTxtD = (TextView) view.findViewById(R.id.weather_forecast_cond_txt_d);
                        ImageView weatherIcon = (ImageView) view.findViewById(R.id.weather_forecast_icon);
                        TextView tmpMax = (TextView) view.findViewById(R.id.weather_forecast_tmp_max);
                        TextView tmpMin = (TextView) view.findViewById(R.id.weather_forecast_tmp_min);
//                          加载信息
                        forecastDate.setText(forecastBases.get(i).getDate());
                        condTxtD.setText(" "+forecastBases.get(i).getCond_txt_d());
                        tmpMax.setText(forecastBases.get(i).getTmp_max() + "℃");
                        tmpMin.setText(forecastBases.get(i).getTmp_min() + "℃");
//                        获取天气icon
                        String iconName = "weather_icon_" + forecastBases.get(i).getCond_code_d().trim() + ".png";
                        int iconID = GetResourseIDUtil.getWeatherIconID(iconName);
                        weatherIcon.setImageResource(iconID);

                        linearLayout.addView(view);
                    }

                } else {
                    String status = forecast.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });

//        获得lifestyle
        HeWeather.getWeatherLifeStyle(WeatherActivity.this, "CN101221008", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherLifeStyleBeanListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i(TAG, "Weather Lifestyle onError", throwable);
            }

            @Override
            public void onSuccess(Lifestyle lifestyle) {
                Log.i(TAG, " Weather Lifestyle onSuccess: " + new Gson().toJson(lifestyle));
                if (Code.OK.getCode().equalsIgnoreCase(lifestyle.getStatus())) {
                    lifestyleBases = lifestyle.getLifestyle();
                    //        lifestyle
                    for (LifestyleBase lifestyleBase : lifestyleBases) {
                        switch (lifestyleBase.getType()) {
                            case "comf":
                                comf.setText("生活" + lifestyleBase.getBrf());
                                break;
                            case "flu":
                                flu.setText("流感" + lifestyleBase.getBrf());
                                break;
                            case "uv":
                                uv.setText("紫外线" + lifestyleBase.getBrf());
                                break;
                            case "sport":
                                sport.setText(lifestyleBase.getBrf() + "运动");
                                break;
                            case "trav":
                                trav.setText(lifestyleBase.getBrf() + "旅游");
                                break;
                            case "air":
                                air.setText("空气" + lifestyleBase.getBrf());
                                break;
                            default:
                                break;
                        }
                    }

                } else {
                    String status = lifestyle.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });

//        获取空气质量
        HeWeather.getAirNow(WeatherActivity.this, "CN101221008", Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultAirNowBeansListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i(TAG, "Weather AirNow onError", throwable);
            }

            @Override
            public void onSuccess(AirNow airNow) {
                if(Code.OK.getCode().equalsIgnoreCase(airNow.getStatus())){
                    airNowCity = airNow.getAir_now_city();
                    airNum.setText(airNowCity.getAqi()+" "+airNowCity.getQlty());
                }else {
                    String status = airNow.getStatus();
                    Code code = Code.toEnum(status);
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });

    }

    //    界面按钮响应
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weather_title_backbtn:
                finish();
                break;
            default:
                break;
        }
    }
}