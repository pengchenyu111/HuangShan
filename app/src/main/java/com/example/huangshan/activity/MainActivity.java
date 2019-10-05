package com.example.huangshan.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.huangshan.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        askPermissions();

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
                fragment = new ShowDataFragment();
                break;
            case INDEX_PREDICT:
                fragment = new PredictFragment();
                break;
            case INDEX_NOTIFICATION:
                fragment = new NotificationFragment();
                break;
            case INDEX_ACCOUNTMANAGE:
                fragment = new AccountManageFragment();
                break;
                default:
                    break;
        }
        return fragment;
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
