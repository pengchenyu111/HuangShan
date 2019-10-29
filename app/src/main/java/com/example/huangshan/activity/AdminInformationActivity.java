package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.huangshan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 这个activity用来展示一个管理员的详细信息
 */
public class AdminInformationActivity extends AppCompatActivity {

    @BindView(R.id.admin_info) TextView adminInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_information);

        ButterKnife.bind(this);

    }
}
