package com.paul.himynote;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.DarkNavigationBarTheme;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.FragmentLayout;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.interfaces.NavigationBarBackgroundColor;
import com.kongzue.baseframework.interfaces.OnFragmentChangeListener;
import com.kongzue.baseframework.util.FragmentChangeUtil;
import com.kongzue.baseframework.util.JumpParameter;
import com.kongzue.tabbar.Tab;
import com.kongzue.tabbar.TabBarView;
import com.kongzue.tabbar.interfaces.OnTabChangeListener;
import com.paul.himynote.Fragment.HomeFragment;
import com.paul.himynote.Fragment.MineFragment;
import com.paul.himynote.Fragment.TagFragment;
import com.paul.himynote.Manager.NoteBeanManager;
import com.paul.himynote.Model.NoteBean;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_main)
@DarkStatusBarTheme(true)
@NavigationBarBackgroundColor(a = 100, r = 0, g = 0, b = 0)
@DarkNavigationBarTheme(true)
@FragmentLayout(R.id.frame)
public class MainActivity extends BaseActivity {
    @BindView(R.id.tabbar)
    private TabBarView tabbar;

    private HomeFragment homeFragment=new HomeFragment();
    private MineFragment mineFragment=new MineFragment();
    private TagFragment tagFragment=new TagFragment();
    @Override
    public void initViews() {
        LitePal.initialize(me);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = me.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            me.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void initDatas(JumpParameter parameter) {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(this, "主页", R.drawable.ic_home));
        tabs.add(new Tab(this,"统计",R.drawable.ic_tag));
        tabs.add(new Tab(this, "设置", R.drawable.ic_setting));

        tabbar.setTab(tabs);
    }

    @Override
    public void setEvents() {
        tabbar.setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                changeFragment(index);
            }
        });
        getFragmentChangeUtil().setOnFragmentChangeListener(new OnFragmentChangeListener() {
            @Override
            public void onChange(int index, BaseFragment fragment) {
                tabbar.setNormalFocusIndex(index);
            }
        });
        checkPermission();
        mineFragment.setBgChangedListener(new MineFragment.BgChangedListener() {
            @Override
            public void setBG(String path) {
                homeFragment.updateBG(path);
            }

            @Override
            public void updateAlpha(Float s) {
                homeFragment.homeRVAdapter.refreashAlpha(s);
            }

            @Override
            public void update() {
                homeFragment.homeRVAdapter.refreash(NoteBeanManager.getAll());
            }
        });
        homeFragment.setDataChanagedListener(new HomeFragment.DataChanagedListener() {
            @Override
            public void dataChanaged() {
                tagFragment.refreshData();
            }
        });
        tagFragment.setDataSetHasChangedLisener(new TagFragment.DataSetHasChangedLisener() {
            @Override
            public void dataChanged() {
                homeFragment.homeRVAdapter.refreash(NoteBeanManager.getAll());
            }
        });
    }
    private void checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int readExternalStoragePermissionResult = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            if(readExternalStoragePermissionResult != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }
    }
    @Override
    public void initFragment(FragmentChangeUtil fragmentChangeUtil) {
        fragmentChangeUtil.addFragment(homeFragment);
        fragmentChangeUtil.addFragment(tagFragment);
        fragmentChangeUtil.addFragment(mineFragment);
        changeFragment(0);
    }
}
