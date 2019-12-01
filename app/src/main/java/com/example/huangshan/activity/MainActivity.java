package com.example.huangshan.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.huangshan.R;
import com.example.huangshan.bean.Admin;
import com.example.huangshan.fragment.PredictFragment;
import com.example.huangshan.fragment.NotificationFragment;
import com.example.huangshan.fragment.ShowDataFragment;
import com.example.huangshan.fragment.AccountManageFragment;
import com.example.huangshan.view.TabView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 首页
 */

public class MainActivity extends BaseActivity {
//    代替findViewById方法
    @BindView(R.id.viewpager) ViewPager mViewPager;

    @BindArray(R.array.tab_array) String[] mTabTitles;

    @BindView(R.id.tab_showdata) TabView mTabShowData;

    @BindView(R.id.tab_predict) TabView mTabPredict;

    @BindView(R.id.tab_notification) TabView mTabNotification;

    @BindView(R.id.tab_accountmanage) TabView mTabAccountManage;

    private List<TabView> mTabViews = new ArrayList<>();
    private List<Fragment> fragmentsCollector = new ArrayList<>();
    private Admin currentAdmin;
    private Bundle bundle = new Bundle();

    private static final int INDEX_SHOWDATA = 0;
    private static final int INDEX_PREDICT = 1;
    private static final int INDEX_NOTIFICATION = 2;
    private static final int INDEX_ACCOUNTMANAGE = 3;

    private static final int M_PERMISSION_CODE = 1001;
    private String[] mPermissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };
    private static final String TAG = "MainActivity";

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();

            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);

        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //权限请求
        askPermissions();
        //获取从LoginFragment传过来的Admin
        currentAdmin = (Admin) this.getIntent().getSerializableExtra("currentAdmin");
        Log.d(TAG,currentAdmin+"");
        bundle.putSerializable("currentAdmin",currentAdmin);
        initFragmentCollector();

        mTabViews.add(mTabShowData);
        mTabViews.add(mTabPredict);
        mTabViews.add(mTabNotification);
        mTabViews.add(mTabAccountManage);

        mViewPager.setOffscreenPageLimit(mTabTitles.length - 1);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            /**
             * @param position 滑动的时候，position总是代表左边的View， position+1总是代表右边的View
             * @param positionOffset 左边View位移的比例
             * @param positionOffsetPixels 左边View位移的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                左边View进行动画
                mTabViews.get(position).setXPercentage(1 - positionOffset);
//                如果positionOffset非0，那么就代表右边的View可见，也就说明需要对右边的View进行动画
                if (positionOffset > 0) {
                    mTabViews.get(position + 1).setXPercentage(positionOffset);
                }
            }
        });
    }

    private void initFragmentCollector() {
        Fragment fragment1 = new ShowDataFragment();
        Fragment fragment2 = new PredictFragment();
        Fragment fragment3 = new NotificationFragment();
        Fragment fragment4 = new AccountManageFragment();
        fragment1.setArguments(bundle);
        fragment2.setArguments(bundle);
        fragment3.setArguments(bundle);
        fragment4.setArguments(bundle);
        fragmentsCollector.add(fragment1);
        fragmentsCollector.add(fragment2);
        fragmentsCollector.add(fragment3);
        fragmentsCollector.add(fragment4);
    }

    private void updateCurrentTab(int index) {
        for (int i = 0; i < mTabViews.size(); i++) {
            if (index == i) {
                mTabViews.get(i).setXPercentage(1);
            } else {
                mTabViews.get(i).setXPercentage(0);
            }
        }
    }

//  以注解的形式设置导航栏下面的响应
    @OnClick({R.id.tab_showdata, R.id.tab_predict, R.id.tab_notification, R.id.tab_accountmanage})
    public void onClickTab(View v) {
        switch (v.getId()) {
            case R.id.tab_showdata:
                mViewPager.setCurrentItem(INDEX_SHOWDATA, false);
                updateCurrentTab(INDEX_SHOWDATA);
                break;
            case R.id.tab_predict:
                mViewPager.setCurrentItem(INDEX_PREDICT, false);
                updateCurrentTab(INDEX_PREDICT);
                break;

            case R.id.tab_notification:
                mViewPager.setCurrentItem(INDEX_NOTIFICATION, false);
                updateCurrentTab(INDEX_NOTIFICATION);
                break;

            case R.id.tab_accountmanage:
                mViewPager.setCurrentItem(INDEX_ACCOUNTMANAGE, false);
                updateCurrentTab(INDEX_ACCOUNTMANAGE);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter{
        public MyPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return getTabFragment(position,mTabTitles[position]);
        }

        @Override
        public int getCount() {
            return mTabTitles.length;
        }
    }

    private Fragment getTabFragment(int index,String title){
        Fragment fragment = null;
        switch (index){
            case INDEX_SHOWDATA:
//                fragment = new ShowDataFragment();
//                removeUselessFragment(fragment);
                fragment=fragmentsCollector.get(0);
                break;
            case INDEX_PREDICT:
//                fragment = new PredictFragment();
//                removeUselessFragment(fragment);
                fragment=fragmentsCollector.get(1);
                break;
            case INDEX_NOTIFICATION:
//                fragment = new NotificationFragment();
//                removeUselessFragment(fragment);
                fragment=fragmentsCollector.get(2);
                break;
            case INDEX_ACCOUNTMANAGE:
//                fragment = new AccountManageFragment();
//                removeUselessFragment(fragment);
                fragment=fragmentsCollector.get(3);
                break;
                default:
                    break;
        }
        return fragment;
    }

    private void removeUselessFragment(Fragment fragment) {
        fragmentsCollector.clear();
        fragmentsCollector.add(fragment);
    }

    /**
     * 以下函数为检查本 app所需要的运行时权限
     */
    private void askPermissions(){
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                && checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                && checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                && checkPermission(Manifest.permission.READ_PHONE_STATE)){
            return;
        }else{
            requestPermissions(mPermissions, M_PERMISSION_CODE);
        }
    }
    private boolean checkPermission(String permission) {

        if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case M_PERMISSION_CODE:
                if (grantResults.length>0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED){
                    return;
                }else{
                    Toast.makeText(this,"请通过全部权限，否则将无法正常使用！",Toast.LENGTH_SHORT).show();
                    ActivityCollector.finishAll();
                }
        }
    }
}
