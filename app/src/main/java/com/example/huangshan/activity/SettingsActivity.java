package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.huangshan.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 这里是 设置 Activity  todo
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.setting_back_btn)
    ImageView back;
    @BindView(R.id.admin_exit)
    Button adminExit;//退出登录按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        back.setOnClickListener(this::onClick);
        adminExit.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back_btn:
                finish();
                break;
            case R.id.admin_exit:
                //清空缓存
                SharedPreferences preferences= getSharedPreferences("admin", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                //跳转回LoginActivity todo 有bug
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
                default:break;
        }
    }
}
