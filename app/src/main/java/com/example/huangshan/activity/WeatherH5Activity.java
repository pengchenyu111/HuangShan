package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huangshan.Constant;
import com.example.huangshan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherH5Activity extends BaseActivity {

    private static final String APP_CACAHE_DIRNAME = "/data/data/com.example.huangshan/cache/webviewCache";

    @BindView(R.id.weather_webview)
    WebView weatherWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_h5);

        ButterKnife.bind(this);

        WebSettings webSettings = weatherWebview.getSettings();
        webSettings.setDomStorageEnabled(true);//主要是这句
        webSettings.setJavaScriptEnabled(true);//启用js
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);

        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);//开启应用缓存
        webSettings.setDatabaseEnabled(true);//开启数据库存储
        webSettings.setAppCachePath(APP_CACAHE_DIRNAME);
        webSettings.setDatabasePath(APP_CACAHE_DIRNAME);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.supportMultipleWindows();
//        webSettings.setAllowContentAccess(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setSavePassword(true);
//        webSettings.setSaveFormData(true);

        weatherWebview.setWebChromeClient(new WebChromeClient());//这行最好不要丢掉
        //该方法解决的问题是打开浏览器不调用系统浏览器，直接用webview打开
        weatherWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        weatherWebview.loadUrl(Constant.HEFENGWEATHER_H5);
    }
}
