package com.example.huangshan.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.huangshan.R;
import com.example.huangshan.activity.AboutActivity;
import com.example.huangshan.activity.AdminSelfInfoActivity;
import com.example.huangshan.activity.AdminsMapViewActivity;
import com.example.huangshan.activity.LoginActivity;
import com.example.huangshan.activity.SettingsActivity;
import com.example.huangshan.activity.UsersManageActivity;
import com.example.huangshan.bean.Admin;
import com.example.huangshan.view.OneLineView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * MainActivity中  账户 的fragment
 */
public class AccountManageFragment extends Fragment implements View.OnClickListener{

  @BindView(R.id.headicon) CircleImageView headIcon;
  @BindView(R.id.admin_self_ll_root1) LinearLayout selfLayout;
  @BindView(R.id.ll_root1) LinearLayout adminLayout;
  @BindView(R.id.ll_root2) LinearLayout userLayout;
  @BindView(R.id.ll_root3) LinearLayout settingLayout;
  @BindView(R.id.ll_root4) LinearLayout aboutLayout;

  private View view;
  private Bundle bundle = new Bundle();
  private Admin currentAdmin;
  private static final String TAG = "AccountManageFragment";

  public AccountManageFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        加载布局
    view = inflater.inflate(R.layout.fragment_main_accountmanage,container,false);
//        绑定控件
    ButterKnife.bind(this,view);
//        设置事件响应
    headIcon.setOnClickListener(this);
    selfLayout.setOnClickListener(this);
    adminLayout.setOnClickListener(this);
    userLayout.setOnClickListener(this);
    settingLayout.setOnClickListener(this);
    aboutLayout.setOnClickListener(this);

//        设置头像
    Glide.with(getActivity()).load(R.mipmap.admins).into(headIcon);

    //拿到MainACtivity传过来的值
    bundle = this.getArguments();
    currentAdmin = (Admin) bundle.getSerializable("currentAdmin");

    return view;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()){
      case R.id.headicon:
        Toast.makeText(getActivity(),"点击了头像",Toast.LENGTH_SHORT).show();
        Intent intent4 = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent4);
        break;
      case R.id.admin_self_ll_root1:
        Intent intent5 = new Intent(getActivity(), AdminSelfInfoActivity.class);
        intent5.putExtras(bundle);
        startActivity(intent5);
        break;
      case R.id.ll_root1:
        Intent intent = new Intent(getActivity(),AdminsMapViewActivity.class);
        startActivity(intent);
        break;
      case R.id.ll_root2:
        Intent intent1 = new Intent(getActivity(),UsersManageActivity.class);
        startActivity(intent1);
        break;
      case R.id.ll_root3:
        Intent intent2 = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent2);
        break;
      case R.id.ll_root4:
        Intent intent3 = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent3);
        break;
      default:break;

    }
  }

}