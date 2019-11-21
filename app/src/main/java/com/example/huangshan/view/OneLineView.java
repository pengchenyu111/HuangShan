package com.example.huangshan.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.huangshan.R;


public class OneLineView extends LinearLayout {

    /**
     * 最外层容器
     */
    private LinearLayout llRoot;

    /**
     * 最左边的Icon
     */
    private ImageView ivLeftIcon;
    /**
     * 中间的文字内容
     */
    private TextView tvTextContent;

    /**
     * 中间的输入框
     */
    private EditText editContent;

    /**
     * 右边的文字
     */
    private TextView tvRightText;

    /**
     * 右边的icon 通常是箭头
     */
    private ImageView ivRightIcon;

    /**
     * 上分割线
     */
    private View topDivider;

    /**
     * 下分割线
     */
    private View bottomDivider;

    /**
     * 重新定义点击事件
     */
    private OnRootClickListener onRootClickListener;
    public void setOnRootClickListener(OnRootClickListener listener){
        this.onRootClickListener = listener;
    }
    public interface OnRootClickListener{
        void onRootClick(View view);
    }

    private static final String TAG = "OneLineView";


    public OneLineView(Context context) {
        super(context);
    }

    public OneLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    /**
     * 初始化view
     * @param context
     * @param attrs
     */
    private void init(Context context,AttributeSet attrs){

        View view = inflate(context,R.layout.layout_my_one_line,this);
        llRoot = (LinearLayout)view.findViewById(R.id.ll_root);
        ivLeftIcon = (ImageView)view.findViewById(R.id.iv_left_icon);
        tvTextContent = (TextView)view.findViewById(R.id.tv_text_content);
        editContent = (EditText)view.findViewById(R.id.edit_content);
        tvRightText = (TextView)view.findViewById(R.id.tv_right_text);
        ivRightIcon = (ImageView)view.findViewById(R.id.iv_right_icon);
        topDivider = (View)view.findViewById(R.id.divider_top);
        bottomDivider = (View)view.findViewById(R.id.divider_bottom);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.OneLineView);
        int n = array.getIndexCount();
        Log.d(TAG,"array的长度："+n);
        for(int i = 0;i<n;i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.OneLineView_iv_left_icon:
                    //第二个参数是默认显示的图片
                    int imageId = array.getResourceId(R.styleable.OneLineView_iv_left_icon,R.mipmap.admins);
                    ivLeftIcon.setImageResource(imageId);
                    break;
                case R.styleable.OneLineView_tv_text_content:
                    String myText = array.getString(R.styleable.OneLineView_tv_text_content);
                    tvTextContent.setText(myText);
                    break;
                case R.styleable.OneLineView_edit_content:
                    boolean showEdit = array.getBoolean(R.styleable.OneLineView_edit_content,false);
                    if (showEdit){
                        editContent.setVisibility(VISIBLE);
                    }else{
                        editContent.setVisibility(GONE);
                    }
                    break;
                case R.styleable.OneLineView_tv_right_text:
                    String MyRightText = array.getString(R.styleable.OneLineView_tv_right_text);
                    tvRightText.setText(MyRightText);
                    break;
                case R.styleable.OneLineView_iv_right_icon:
                    boolean showRightIcon = array.getBoolean(R.styleable.OneLineView_iv_right_icon,true);
                    if (showRightIcon){
                        ivRightIcon.setVisibility(VISIBLE);
                    }else{
                        ivRightIcon.setVisibility(GONE);
                    }
                    break;
                case R.styleable.OneLineView_divider_top:
                    boolean showDividerTop = array.getBoolean(R.styleable.OneLineView_divider_top,false);
                    if (showDividerTop){
                        topDivider.setVisibility(VISIBLE);
                    }else{
                        topDivider.setVisibility(GONE);
                    }
                    break;
                case R.styleable.OneLineView_divider_bottom:
                    boolean showDividerBottom = array.getBoolean(R.styleable.OneLineView_divider_bottom,true);
                    if (showDividerBottom){
                        bottomDivider.setVisibility(VISIBLE);
                    }else{
                        bottomDivider.setVisibility(GONE);
                    }
                    break;
                default:break;
            }

        }
        array.recycle();


        llRoot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onRootClickListener.onRootClick(llRoot);
            }
        });
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_MOVE :break;
//            case MotionEvent.ACTION_UP:
//                if (onRootClickListener != null){
//                    onRootClickListener.onRootClick();
//                }
//                break;
//            case MotionEvent.ACTION_DOWN: break;
//                    default:break;
//        }
//        return true;
//    }
}