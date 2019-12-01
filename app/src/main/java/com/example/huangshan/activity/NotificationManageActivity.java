package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.example.huangshan.adapter.NotificationListAdapter;
import com.example.huangshan.bean.Admin;
import com.example.huangshan.bean.Notification;
import com.example.huangshan.utils.HttpUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 通知管理 NotificationManageActivity
 */
public class NotificationManageActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NotificationManageActiv";
    private Bundle bundle = new Bundle();
    private Admin currentAdmin;
    private NotificationListAdapter adapter;
    private List<Notification> notifications = new ArrayList<Notification>();

    @BindView(R.id.notifications_list_back_btn)
    ImageView back;
    @BindView(R.id.notification_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.notification_refresh)
    SwipeRefreshLayout refreshLayout;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_manage);

        ButterKnife.bind(this);

        //获得传过来的数据
        currentAdmin = (Admin) this.getIntent().getSerializableExtra("currentAdmin");
        Log.d(TAG,currentAdmin+"");

        //设置响应
        back.setOnClickListener(this::onClick);
        //设置下拉刷新
        refreshLayout.setOnRefreshListener(this::onRefresh);

        //开始设置RecyclerView
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //初始化通知，数据库获取
        initData();

        //设置Adaptor
        adapter = new NotificationListAdapter(this,notifications);
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        //封装请求参数
        String url = Constant.URL+"NotificationServlet";
        Map<String,String> map = new HashMap<>();
        map.put("handleMethod","selectAllNotification");
        //发起请求
        try {
            String result = HttpUtil.postRequest(url,map);
            Gson gson = new Gson();
            notifications = gson.fromJson(result,new TypeToken<List<Notification>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notifications_list_back_btn:
                finish();
                break;
                default:break;
        }
    }

    @Override
    public void onRefresh() {
        //初始化通知，数据库获取
        initData();

        //设置Adaptor
        adapter = new NotificationListAdapter(this,notifications);
        recyclerView.setAdapter(adapter);

        Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();
        //关闭刷新
        refreshLayout.setRefreshing(false);
    }

//    public void searchViewSetting(SearchView mSearchView) {
//        int searchPlateId = mSearchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
//        View searchPlate = mSearchView.findViewById(searchPlateId);
////       mSearchView.setBackground();
//        if (searchPlate != null) {
//            int searchTextId = searchPlate.getContext().getResources()
//                    .getIdentifier("android:id/search_src_text", null, null);
//            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
//            if (searchText != null) {
//                searchText.setTextColor(Color.GRAY);
//                searchText.setHintTextColor(Color.GRAY);
//            }
//            try {
//                Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
//                mCursorDrawableRes.setAccessible(true);
//                mCursorDrawableRes.set(searchText, R.drawable.cursor_color);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View textView=inflater.inflate(R.layout.fragment_notification,null);
//
//        FloatingActionButton circle_add=(FloatingActionButton) textView.findViewById(R.id.circle_add);
//
//        SearchView searchView = (SearchView) textView.findViewById(R.id.search_view);
//        searchViewSetting(searchView);
//
//
//        circle_add.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        return textView;
//    }

}
