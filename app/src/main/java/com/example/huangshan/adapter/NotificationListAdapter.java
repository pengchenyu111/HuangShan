package com.example.huangshan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.huangshan.R;
import com.example.huangshan.activity.NotificationInfoActivity;
import com.example.huangshan.bean.Notification;

import java.io.Serializable;
import java.util.List;

/**
 * 该Adapter适用于通知列表
 */
public class NotificationListAdapter extends RecyclerSwipeAdapter<NotificationListAdapter.NotificationViewHolder>{

    private static final String TAG = "NotificationListAdapter";

    public static class NotificationViewHolder extends RecyclerView.ViewHolder{
        SwipeLayout swipeLayout;
        CardView infoRoot;
        ImageButton typeBtn;
        TextView sendTime;
        TextView title;
        TextView content;
        TextView sendAdminName;
        ImageView details;
        ImageView delete;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.notification_list_root);
            infoRoot = itemView.findViewById(R.id.notification_item_info_root);
            typeBtn = itemView.findViewById(R.id.notification_type_btn);
            sendTime = itemView.findViewById(R.id.notification_sendTime);
            title = itemView.findViewById(R.id.notification_title);
            content = itemView.findViewById(R.id.notification_content);
            sendAdminName = itemView.findViewById(R.id.notification_send_admin_name);
            details = itemView.findViewById(R.id.notification_item_info);
            delete = itemView.findViewById(R.id.notification_item_delete);
        }
    }

    private Context mContext;
    private List<Notification> notifications;

    public NotificationListAdapter(Context context,List<Notification> objects){
        this.mContext = context;
        this.notifications = objects;
    }
    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notificationlist_item,parent,false);
        NotificationViewHolder holder = new NotificationViewHolder(view);
        return holder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int i) {

        //动画
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewWithTag("swipe_menu"));

        //显示信息
        holder.sendTime.setText(notifications.get(i).getSendTime());
        holder.title.setText(notifications.get(i).getTitle());
        holder.content.setText(notifications.get(i).getContent());
        holder.sendAdminName.setText(notifications.get(i).getSendAdminName());
        //设置type
        int type = notifications.get(i).getType();
        Resources resources = mContext.getResources();
        if (type == 0){
            Drawable drawable = resources.getDrawable(R.drawable.btn_circle_reds);
            holder.typeBtn.setBackground(drawable);
        }else if (type == 1){
            Drawable drawable = resources.getDrawable(R.drawable.btn_circle_yellow);
            holder.typeBtn.setBackground(drawable);
        }else if (type == 2){
            Drawable drawable = resources.getDrawable(R.drawable.btn_circle_green);
            holder.typeBtn.setBackground(drawable);
        }

        //设置通知详情响应
        holder.infoRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("notification", notifications.get(i));
                Intent intent = new Intent(mContext, NotificationInfoActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        //设置通知详情响应
        holder.details.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("notification", (Serializable) notifications.get(i));
                Intent intent = new Intent(mContext, NotificationInfoActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
//        holder.delete.setOnClickListener(this::onClick);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.notification_list_root;
    }
}
