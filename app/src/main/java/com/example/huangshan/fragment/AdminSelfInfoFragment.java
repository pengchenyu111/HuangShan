package com.example.huangshan.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huangshan.R;
import com.example.huangshan.activity.LoginActivity;
import com.example.huangshan.bean.Admin;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 *
 */
public class AdminSelfInfoFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "AdminSelfInfoFragment";
    private View view;

    @BindView(R.id.admin_self_info_headicon)
    ImageView headIcon;//头像
    @BindView(R.id.admin_self_info_acount)
    TextView account;//账号
    @BindView(R.id.admin_self_info_name)
    TextView name;
    @BindView(R.id.admin_self_info_sex)
    TextView sex;//性别
    @BindView(R.id.admin_self_info_age)
    TextView age;
    @BindView(R.id.admin_self_info_workyear)
    TextView workYear;
    @BindView(R.id.admin_self_info_phone)
    TextView phone;
    @BindView(R.id.admin_self_info_introduction)
    TextView introduction;//简介

    @BindView(R.id.admin_slef_info_back_btn)
    ImageView backBtn;//返回按钮
    @BindView(R.id.admin_self_info_update)
    TextView updateBtn;//更新信息按钮



    public AdminSelfInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_self_info, container, false);
        ButterKnife.bind(this,view);

        backBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);


        //设置数据
        Admin currentAdmin = (Admin) getArguments().getSerializable("currentAdmin");
        headIcon.setImageResource(R.mipmap.admins);//todo  由于bean里忘了加headIcon属性了，这里暂时写死！！烦死了！！！
        account.setText(currentAdmin.getAdminAccount());
        name.setText(currentAdmin.getAdminName());
        sex.setText(currentAdmin.getAdminSex());
        age.setText(String.valueOf(currentAdmin.getAdminAge()));
        workYear.setText(String.valueOf(currentAdmin.getAdminWorkYear())+"年");
        phone.setText(currentAdmin.getAdminPhone());
        introduction.setText(currentAdmin.getAdminIntroduction());


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.admin_slef_info_back_btn:
                getActivity().finish();
                break;
            case R.id.admin_self_info_update:
                //todo 这里点击要跳转到另一个修改的Fragment
                break;
            default:break;
        }
    }


}
