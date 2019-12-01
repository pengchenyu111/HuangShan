package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huangshan.R;
import com.example.huangshan.bean.Notification;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 一条通知的详情页
 */
public class NotificationInfoActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "NotificationInfoActivit";
    private Notification notification = new Notification();

    @BindView(R.id.notification_info_back_btn)
    ImageView back;
    @BindView(R.id.notification_info_title)
    TextView title;
    @BindView(R.id.notification_info_content)
    TextView content;
    @BindView(R.id.notification_info_sendAdminName)
    TextView sendAdminName;
    @BindView(R.id.notification_info_sendTime)
    TextView sendTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_info);

        ButterKnife.bind(this);

        back.setOnClickListener(this);

        //获得传来的值
        notification = (Notification) getIntent().getSerializableExtra("notification");
        title.setText(notification.getTitle());
        content.setText(notification.getContent());
        sendAdminName.setText(notification.getSendAdminName());
        sendTime.setText(notification.getSendTime());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notification_info_back_btn:
                finish();
                break;
                default:break;
        }
    }
}
