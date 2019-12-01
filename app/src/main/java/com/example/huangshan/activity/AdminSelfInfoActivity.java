package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.huangshan.R;
import com.example.huangshan.bean.Admin;
import com.example.huangshan.fragment.AdminLoginFragment;
import com.example.huangshan.fragment.AdminSelfInfoFragment;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 管理员个人信息
 */
public class AdminSelfInfoActivity extends BaseActivity{

    private static final String TAG = "AdminSelfInfoActivity";
    private Bundle bundle = new Bundle();
    private Admin currentAdmin;//todo 这里不应该加admin的信息，应该为Oneadminmanagea！！！后面再改吧！没时间了！！！



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_self_info);

        ButterKnife.bind(this);

        currentAdmin = (Admin) this.getIntent().getSerializableExtra("currentAdmin");
        Log.d(TAG,currentAdmin+"");

        //加入第一个fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        AdminSelfInfoFragment fragment = new AdminSelfInfoFragment();
        bundle.putSerializable("currentAdmin",currentAdmin);
        fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.admin_self_info_container,fragment);
        fragmentTransaction.commit();
    }

}
