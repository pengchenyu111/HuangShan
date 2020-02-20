package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.util.LogTime;
import com.example.huangshan.R;
import com.example.huangshan.bean.OneAdminManage;
import com.example.huangshan.fragment.ShowAdminInfoFragment;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 这个activity用来展示一个管理员的详细信息
 */
public class  AdminInformationActivity extends BaseActivity {

    private static final String TAG = "AdminInformationActivit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_information);

        ButterKnife.bind(this);

        //拿到传过来的数据
        Intent intent = getIntent();
        OneAdminManage currentAdminManage = (OneAdminManage) intent.getSerializableExtra("currentAdminManage");

        //设置第一个fragment,并向其传送数据
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ShowAdminInfoFragment showAdminInfoFragment = new ShowAdminInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("adminInfo",currentAdminManage);
        showAdminInfoFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.admin_info_container,showAdminInfoFragment);
        fragmentTransaction.commit();

    }
}
