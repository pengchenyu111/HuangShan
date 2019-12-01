package com.example.huangshan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.example.huangshan.bean.Admin;
import com.example.huangshan.bean.ResultMessage;
import com.example.huangshan.utils.HttpUtil;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 管理员发布通知的activity
 */
public class SendNotificationActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "SendNotificationActivit";
    private Bundle bundle = new Bundle();
    private Admin currentAdmin;

    private String title;
    private String content;
    private String sendAdminName;
    private String sendTime;
    private int type;

    @BindView(R.id.notifications_send_back_btn)
    ImageView back;
    @BindView(R.id.notification_send_btn)
    TextView sendNotification;
    @BindView(R.id.notification_input_title)
    EditText titleEditText;
    @BindView(R.id.notification_input_type_group)
    RadioGroup radioGroup;
    @BindView(R.id.notification_input_content)
    EditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        ButterKnife.bind(this);

        //设置响应
        back.setOnClickListener(this);
        sendNotification.setOnClickListener(this);

        //获得传过来的数据
        currentAdmin = (Admin) this.getIntent().getSerializableExtra("currentAdmin");
        Log.d(TAG,currentAdmin+"");

        //获得页面上的通知数据
        //title = titleEditText.getText().toString();
        //content = contentEditText.getText().toString();要在onclick中获取才有值，很傻逼！！！
        sendAdminName = currentAdmin.getAdminName();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sendTime = format.format(date);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.notification_input_type_0:
                        type = 0;
                        break;
                    case R.id.notification_input_type_1:
                        type = 1;
                        break;
                    case R.id.notification_input_type_2:
                        type = 2;
                        break;
                        default:break;
                }
            }
        });

        //创建通知栏
        //todo 这里的通知只是模拟发送推送，并不能真正发送给所用用户
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "adminNotification";
            String channelName = "adminNotification";
            int importance = NotificationManager.IMPORTANCE_MAX;
            createNotificationChannel(channelId, channelName, importance);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notifications_send_back_btn:
                finish();
                break;
            case R.id.notification_send_btn:
                //发布通知
                send();
                break;
                default:break;
        }
    }

    private void send() {
        //获取EditText中的值
        title = titleEditText.getText().toString();
        content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
            Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
        }else{
            //装入map 发给服务器
            Map<String,String> map = new HashMap<>();
            map.put("handleMethod","addNotification");
            map.put("sendTime",sendTime);
            map.put("sendAdminName",sendAdminName);
            map.put("title",title);
            map.put("content",content);
            map.put("type", String.valueOf(type));

            String url = Constant.URL+"NotificationServlet";
            try {
                String resultData = HttpUtil.postRequest(url, map);
                Gson gson = new Gson();
                ResultMessage message = gson.fromJson(resultData,ResultMessage.class);
                if (message.getResultCode().equals("008")){
                    new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("成功")
                            .setContentText("此通知发布成功！快去查看吧！")
                            .setConfirmText("OK")
                            .showCancelButton(false)
                            .setCancelClickListener(null)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    finish();
                                }
                            })
                            .show();

                    //设置点击通知栏跳转，要先把管理员发布的通知信息装进去
                    com.example.huangshan.bean.Notification adminNotification = new com.example.huangshan.bean.Notification();
                    adminNotification.setTitle(title);
                    adminNotification.setContent(content);
                    adminNotification.setSendAdminName(sendAdminName);
                    adminNotification.setSendTime(sendTime);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("notification",adminNotification);
                    Intent intent = new Intent(this,NotificationInfoActivity.class);
                    intent.putExtras(bundle);
                    PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

                    //开启通知栏
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = new NotificationCompat.Builder(this, "adminNotification")
                            .setContentTitle(title)
                            .setContentText(content)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.notification_icon)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_icon))
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setAutoCancel(true)
                            .setNumber(1)
                            .setContentIntent(pendingIntent)
                            .build();
                    manager.notify(1, notification);

                }else if (message.getResultCode().equals("009")){
                    new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("失败")
                            .setContentText("服务器繁忙，请稍候再试！")
                            .setConfirmText("OK")
                            .showCancelButton(false)
                            .setCancelClickListener(null)
                            .setConfirmClickListener(null)
                            .show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        channel.setShowBadge(true);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{0,50,100,150});
        channel.setSound(Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"/MIUI/.ringtone/2.mp3")),null);
        notificationManager.createNotificationChannel(channel);
    }
}
