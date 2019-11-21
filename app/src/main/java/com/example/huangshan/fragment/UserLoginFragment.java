package com.example.huangshan.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * LoginActivity中游客用于第三方登录的fragment
 */
public class UserLoginFragment extends Fragment {

    private View view;



    public UserLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_login, container, false);

        //微信登陆的图标
        ImageView weChatLogin = (ImageView) view.findViewById(R.id.wechat_login_icon);
        weChatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
//管理员登陆的文字
        TextView adminLogin = (TextView) view.findViewById(R.id.admin_login_text);
        adminLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置fragment切换效果：淡入淡出
                AdminLoginFragment fragment = new AdminLoginFragment();
                fragmentTransaction.replace(R.id.fragment_container,fragment);//replace替换新fragment
                fragmentTransaction.addToBackStack("userLogin");//加入到返回栈中，便于返回上一个fragment
                fragmentTransaction.commit();
            }
        });

//        响应返回按钮
        ImageView userBack = (ImageView)view.findViewById(R.id.user_back);
        userBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });


        return view;
    }

}
