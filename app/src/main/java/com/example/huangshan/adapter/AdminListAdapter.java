package com.example.huangshan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.huangshan.R;

import java.util.List;

/**
 * 该 adapter 用于适配管理员导览列表
 */
public class AdminListAdapter extends RecyclerSwipeAdapter<AdminListAdapter.AdminViewHolder> {

    private static final String TAG = "AdminListAdapter";

    public static class AdminViewHolder extends RecyclerView.ViewHolder {
        SwipeLayout swipeLayout; // 一个item的根
        CardView infoRoot;// item 信息的根
        ImageView adminHeadIcon; //头像
        TextView adminName; //姓名
        TextView adminManageSpot; // 管理地点
        ImageView adminInfo;// 管理员信息按钮
        ImageView adminPhone; //管理员电话按钮
        ImageView adminDelete; //管理员删除按钮

        public AdminViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.admin_list_root);
            infoRoot = itemView.findViewById(R.id.item_info_root);
            adminHeadIcon = itemView.findViewById(R.id.admin_list_headicon);
            adminName = itemView.findViewById(R.id.admin_list_name);
            adminManageSpot = itemView.findViewById(R.id.admin_list_spot);
            adminInfo = itemView.findViewById(R.id.admin_item_info);
            adminPhone = itemView.findViewById(R.id.admin_item_call);
            adminDelete = itemView.findViewById(R.id.admin_item_delete);
        }
    }

    private Context mContext;
    private List<String> testString;

    //Adapter构造函数
    public AdminListAdapter(Context context,List<String> objests){
        this.mContext = context;
        this.testString = objests;
    }


    @Override
    public AdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adminlist_item,parent,false);
        AdminViewHolder holder = new AdminViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdminViewHolder adminViewHolder, int i) {
        adminViewHolder.adminName.setText(testString.get(i));
        adminViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        adminViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, adminViewHolder.swipeLayout.findViewWithTag("swipe_menu"));

        //单行点击事件，默认为显示管理员的详细信息
        adminViewHolder.infoRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了item:"+testString.get(i),Toast.LENGTH_SHORT).show();
            }
        });

        //管理员信息按钮
        adminViewHolder.adminInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了adminInfo",Toast.LENGTH_SHORT).show();
            }
        });

        //拨打电话按钮
        adminViewHolder.adminPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:18224464804"));//todo
                mContext.startActivity(intent);
            }
        });

        //删除管理员按钮
        adminViewHolder.adminDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo
                Toast.makeText(mContext,"点击了delete",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return testString.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.admin_list_root;
    }



}
