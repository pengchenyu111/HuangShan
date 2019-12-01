package com.example.huangshan.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.huangshan.Constant;
import com.example.huangshan.activity.MainActivity;
import com.example.huangshan.bean.Admin;
import com.example.huangshan.bean.LoginMessage;
import com.example.huangshan.view.CustomVideoView;
import com.example.huangshan.utils.HttpUtil;
import com.example.huangshan.R;
import com.example.huangshan.bean.ResultMessage;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * LoginActivity中admin用于登录的fragment
 */
public class AdminLoginFragment extends Fragment {

    private static final String TAG = "AdminLoginFragment";
    private View view;
    private  CustomVideoView videoView;


    public AdminLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_login, container, false);

        final EditText accountText = (EditText)view.findViewById(R.id.admin_account);
        final EditText passwordText = (EditText)view.findViewById(R.id.admin_pwd);


//      登录按钮响应
        Button adminLogin = (Button)view.findViewById(R.id.admin_login_btn);
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),accountText.getText()+","+passwordText.getText(),Toast.LENGTH_SHORT).show();
                try {
//                     获取输入的账号和密码
                    final String adminAccount = accountText.getText().toString();
                    final String adminPassword = passwordText.getText().toString();
//                    往服务器发送管理员登录请求
                    String result = adminLogin(adminAccount,adminPassword);
//                    反序列化为LoginMsg对象
                    Gson gson = new Gson();
                    LoginMessage msg = gson.fromJson(result, LoginMessage.class);
                    Log.d(TAG,msg+"");

                    String resultCode = msg.getResultCode();
                    if ("001".equals(resultCode)){
                        Toast.makeText(getActivity(),"登录成功",Toast.LENGTH_SHORT).show();

                        Admin currentAdmin = new Admin(msg.getAdminAccount(),msg.getAdminPassword(),msg.getAdminName(),
                                msg.getAdminSex(),msg.getAdminAge(),msg.getAdminWorkYear(),msg.getAdminPhone(),
                                msg.getAdminIntroduction(),msg.getAdminPower());
                        //写入缓存
                        SharedPreferences preferences= getActivity().getSharedPreferences("admin", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("adminAccount", currentAdmin.getAdminAccount());
                        editor.putString("adminPassword", currentAdmin.getAdminPassword());
                        editor.putString("adminName", currentAdmin.getAdminName());
                        editor.putString("adminSex", currentAdmin.getAdminSex());
                        editor.putString("adminAge", String.valueOf(currentAdmin.getAdminAge()));
                        editor.putString("adminWorkYear", String.valueOf(currentAdmin.getAdminWorkYear()));
                        editor.putString("adminPhone", currentAdmin.getAdminPhone());
                        editor.putString("adminIntroduction", currentAdmin.getAdminIntroduction());
                        editor.putString("adminPower", String.valueOf(currentAdmin.getAdminPower()));
                        editor.apply();

                        //传送数据
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("currentAdmin",currentAdmin);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else if ("002".equals(resultCode)){
                        Toast.makeText(getActivity(),"账号或密码错误",Toast.LENGTH_SHORT).show();
                    }else if ("003".equals(resultCode)){
                        Toast.makeText(getActivity(),"账号或密码不能为空",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

//        设置返回按钮响应
        ImageView adminBack = (ImageView)view.findViewById(R.id.admin_back);
        adminBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.popBackStack();
                getActivity().finish();
            }
        });

        return view;
    }

    private String adminLogin(String adminAccount, String adminPassword) throws Exception{
//        使用POST方式，则用map封装请求参数
        Map<String,String> map = new HashMap<>();
        map.put("adminAccount",adminAccount);
        map.put("adminPassword",adminPassword);
//        发请求
        String url = Constant.URL + "AdminLoginServlet";
        return HttpUtil.postRequest(url,map);
    }

}