package com.example.huangshan.utils;

import android.os.Build;

import com.example.huangshan.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HttpUtil是发送请求的工具类
 *
 */

public class HttpUtil {

    public static final String baseUrl = Constant.URL;
    private static Map<String, List<Cookie>> cookieStore = new HashMap<>();

    //    创建线程池
    private static ExecutorService threadPool = Executors.newFixedThreadPool(30);
    //    创建默认的OkHttpClient对象
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
        @Override
        public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
            cookieStore.put(httpUrl.host(),list);
        }

        @NotNull
        @Override
        public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
            List<Cookie> cookies = cookieStore.get(httpUrl.host());
            return cookies == null ? new ArrayList<Cookie>() : cookies;
        }
    }).build();


    public static String getRequest(final String url)throws Exception{
//        GET请求方式通过url传递参数
//        相比POST，GET地效率更高
//        GET多用于查询


//        FutureTask可用于异步获取执行结果或取消执行任务的场景。
//        通过传入Runnable或者Callable的任务给FutureTask，直接调用其run方法或者放入线程池执行，
//        之后可以在外部通过FutureTask的get方法异步获取执行结果，
//        因此，FutureTask非常适合用于耗时的计算，主线程可以在完成自己的任务后，再去获取结果。


        FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
//              创建请求对象
                Request request = new Request.Builder().url(url).build();
                Call call = okHttpClient.newCall(request);
//              发送GET请求
                Response response = call.execute();
//              如果服务器成功地返回响应
                if (response.isSuccessful() && response.body() != null){
                    return response.body().string().trim();
                }else{
                    return null;
                }
            }
        });
//        提交任务
        threadPool.submit(task);
        return task.get();
    }

    public static String postRequest(String url, final Map<String,String>rawParams)throws Exception{
//        POST把数据放在HTTP的包体内（requrest body）来传递
//        POST多用于修改数据

        FutureTask<String> task = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
//                创建包含请求参数地表单体
                FormBody.Builder builder = new FormBody.Builder();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    rawParams.forEach(builder::add);//lambda表达式遍历map
                }
                FormBody body = builder.build();
//                创建请求对象
                Request request = new Request.Builder().url(url).post(body).build();
                Call call = okHttpClient.newCall(request);
//                发送POST请求
                Response response = call.execute();
//                如果服务器成功的返回响应
                if (response.isSuccessful() && response.body() != null){
                    return response.body().string().trim();
                }else{
                    return  null;
                }
            }
        });
        threadPool.submit(task);
        return task.get();
    }

}