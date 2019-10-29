package com.example.huangshan.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.huangshan.R;
import com.example.huangshan.adapter.AdminListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 这个Activity用于：使用列表展示所有的管理员
 */
public class ListAdminsActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.admin_list_recyclerview) RecyclerView adminListRecyclerView;

    private AdminListAdapter adapter;
    private List<String> list = new ArrayList<>();
    private static final String TAG = "ListAdminsActivity";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_admins);

        ButterKnife.bind(this);

        //设置固定大小，这样可以优化性能
        adminListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置滚动方向：vertical
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        adminListRecyclerView.setLayoutManager(layoutManager);

        // 初始化管理员信息
        initAdminInfo();

        //设置Adapter
        adapter = new AdminListAdapter(this,list);
        Log.d(TAG,"-------执行设置adapter------------");
        adminListRecyclerView.setAdapter(adapter);
        Log.d(TAG,"-------------适配Adapter");
    }

    private void initAdminInfo() {
        // todo 这里之后要实现从数据库取数据
        //获取所有的管理员的信息
        // 这里模拟从数据库取得数据
        list.add("黄山");
        list.add("张三");
        list.add("赵四");
        list.add("111");
        list.add("112");
        list.add("113");
        list.add("114");
        list.add("115");
        list.add("116");
        list.add("117");
        list.add("118");
        list.add("119");
        list.add("120");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.admins_list_back_btn:
                finish();
                break;

                default:break;
        }
    }
}
