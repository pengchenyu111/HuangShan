package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.huangshan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 这个是关于界面的Activity
 *
 *
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.about_back_btn)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
