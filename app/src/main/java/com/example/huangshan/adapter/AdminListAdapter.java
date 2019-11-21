package com.example.huangshan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.huangshan.Constant;
import com.example.huangshan.R;
import com.daimajia.swipe.SwipeLayout;
import com.example.huangshan.activity.AdminInformationActivity;
import com.example.huangshan.bean.OneAdminManage;
import com.example.huangshan.bean.ResultMessage;
import com.example.huangshan.utils.HttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

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
            swipeLayout = itemView.findViewById(R.id.admin_list_root);
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
    private List<OneAdminManage> adminManages;

    //Adapter构造函数
    public AdminListAdapter(Context context,List<OneAdminManage> objests){
        this.mContext = context;
        this.adminManages = objests;
    }


    @Override
    public AdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adminlist_item,parent,false);
        AdminViewHolder holder = new AdminViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdminViewHolder adminViewHolder, int i) {
        //显示信息
        adminViewHolder.adminName.setText(adminManages.get(i).getAdminName());
        adminViewHolder.adminManageSpot.setText(adminManages.get(i).getScenicName());
        //动画
        adminViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        adminViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, adminViewHolder.swipeLayout.findViewWithTag("swipe_menu"));

        //单行点击事件，默认为显示管理员的详细信息
        adminViewHolder.infoRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"点击了item:"+adminManages.get(i).getAdminName(),Toast.LENGTH_SHORT).show();
            }
        });

        //管理员信息按钮
        adminViewHolder.adminInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AdminInformationActivity.class);
                intent.putExtra("currentAdminManage",adminManages.get(i));
                mContext.startActivity(intent);
            }
        });

        //拨打电话按钮
        adminViewHolder.adminPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+adminManages.get(i).getAdminPhone()));
                mContext.startActivity(intent);
            }
        });

        //删除管理员按钮
        adminViewHolder.adminDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获得数据
                String url = Constant.URL+"DeleteAdminManage";
                Map<String,String> map = new HashMap<>();
                map.put("scenicId",adminManages.get(i).getScenicId());
                map.put("adminAccount",adminManages.get(i).getAdminAccount());

                //显示 确认删除 的提示框
                new SweetAlertDialog(mContext,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("删除  "+adminManages.get(i).getAdminName()+"  ？")
                        .setContentText("一旦删除将无法找回此人的信息!")
                        .setCancelText("取消")
                        .setConfirmText("确认")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                //当前提示框消失
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                //更新数据库
                                try {
                                    String result = HttpUtil.postRequest(url,map);
                                    Gson gson = new Gson();
                                    ResultMessage message = gson.fromJson(result,ResultMessage.class);
                                    if (message.getResultCode().equals("006")){
                                        // 显示删除成功的提示框
                                        deleteSuccess(sweetAlertDialog, adminViewHolder, i);
                                    }else if (message.getResultCode().equals("007")){
                                        //显示失败的弹框
                                        deleteFailed(sweetAlertDialog);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).show();

            }
        });
    }

    private void deleteFailed(SweetAlertDialog sweetAlertDialog) {
        sweetAlertDialog
                .setTitleText("删除失败")
                .setContentText("服务器繁忙，请稍后再试！")
                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
    }

    private void deleteSuccess(SweetAlertDialog sweetAlertDialog, AdminViewHolder adminViewHolder, int i) {
        sweetAlertDialog
                .setTitleText("删除")
                .setContentText("这位负责人的信息已被删除！")
                .setConfirmText("OK")
                .showCancelButton(false)
                .setCancelClickListener(null)
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

        // 在列表中去除
        mItemManger.removeShownLayouts(adminViewHolder.swipeLayout);
        adminManages.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i, adminManages.size());
        mItemManger.closeAllItems();
    }

    @Override
    public int getItemCount() {
        return adminManages.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.admin_list_root;
    }

}
