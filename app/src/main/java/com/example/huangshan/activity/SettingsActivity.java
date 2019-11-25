package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        back.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back_btn:
                finish();
                break;
                default:break;
        }
    }
}
