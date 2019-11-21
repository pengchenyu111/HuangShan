package com.example.huangshan.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.huangshan.R;
import com.example.huangshan.adapter.AdminListAdapter;
import com.example.huangshan.bean.OneAdminManage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 这个Activity用于：使用列表展示所有的管理员
 */
public class ListAdminsActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.admin_list_recyclerview) RecyclerView adminListRecyclerView;
    @BindView(R.id.admins_list_back_btn) ImageView backButton;

    private AdminListAdapter adapter;
    private List<OneAdminManage> list = new ArrayList<>();
    private static final String TAG = "ListAdminsActivity";

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_admins);

        //绑定控件
        ButterKnife.bind(this);

        //添加响应
        backButton.setOnClickListener(this::onClick);

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
        adminListRecyclerView.setAdapter(adapter);
    }

    private void initAdminInfo() {
        // 获得 AdminMapViewActivity 传过来的数据
        list = (ArrayList<OneAdminManage>)getIntent().getSerializableExtra("allAdmins");
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
