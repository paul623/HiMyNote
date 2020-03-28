package com.paul.himynote.Fragment;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.allen.library.SuperTextView;
import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.util.JumpParameter;
import com.kongzue.baseframework.util.OnJumpResponseListener;
import com.paul.himynote.Adapter.HomeRVAdapter;
import com.paul.himynote.Activity.EditNoteActivity;
import com.paul.himynote.MainActivity;
import com.paul.himynote.Manager.NoteBeanManager;
import com.paul.himynote.Manager.SettingManager;
import com.paul.himynote.Manager.SyncManager;
import com.paul.himynote.Manager.WordsManager;
import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.R;
import com.paul.himynote.Activity.ViewNoteActivity;
import com.paul.himynote.Tools.ImageHelper;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import es.dmoral.toasty.Toasty;

@Layout(R.layout.fragment_home)
public class HomeFragment extends BaseFragment<MainActivity> {
    @BindView(R.id.ly_home)
    private LinearLayout linearLayout;
    @BindView(R.id.rv_home)
    private RecyclerView recyclerView;
    @BindView(R.id.stv_toolbar)
    private SuperTextView toolbar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    String str_words;

    public HomeRVAdapter homeRVAdapter;
    SettingManager settingManager;
    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    Toasty.success(me,"同步成功！",Toasty.LENGTH_SHORT).show();
                    refreshLayout.finishRefresh(true);
                    break;
                case -1:
                    Toasty.error(me,"哎呀疼~请检查账户或网络后再试吧",Toasty.LENGTH_SHORT).show();
                    refreshLayout.finishRefresh(false);
                    break;
                case 2:
                    toolbar.setRightBottomString(str_words);
                    break;
            }
            return false;
        }
    });
    @Override
    public void initViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window window = me.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public void initDatas() {
        settingManager=new SettingManager(me);
        homeRVAdapter=new HomeRVAdapter(NoteBeanManager.getAll(), new HomeRVAdapter.onItemClickListener() {
            @Override
            public void onClick(NoteBean noteBean) {
                jump(ViewNoteActivity.class,new JumpParameter().put("data",noteBean));
            }

            @Override
            public void onLongClick(NoteBean noteBean) {
                JumpParameter jumpParameter=new JumpParameter();
                jumpParameter.put("data",noteBean);
                jumpParameter.put("flag",true);
                jump(EditNoteActivity.class, jumpParameter, new OnJumpResponseListener() {
                    @Override
                    public void OnResponse(JumpParameter jumpParameter) {
                        if(jumpParameter.get("result")!=null&&(boolean)jumpParameter.get("result")){
                            homeRVAdapter.refreash(NoteBeanManager.getAll());
                        }
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(homeRVAdapter);
        linearLayout.setBackground(ImageHelper.getByPrivatePath(me,settingManager.getBg_path()));
        setRefreshLayout();
    }

    @Override
    public void setEvents() {
        toolbar.setRightImageViewClickListener(new SuperTextView.OnRightImageViewClickListener() {
            @Override
            public void onClickListener(ImageView imageView) {
                JumpParameter jumpParameter=new JumpParameter();
                jumpParameter.put("flag",false);
                jump(EditNoteActivity.class,jumpParameter, new OnJumpResponseListener() {
                    @Override
                    public void OnResponse(JumpParameter jumpParameter) {
                        if(jumpParameter.get("result")!=null&&(boolean)jumpParameter.get("result")){
                            homeRVAdapter.refreash(NoteBeanManager.getAll());
                        }
                    }
                });
            }
        });
        toolbar.setLeftTextGroupClickListener(new SuperTextView.OnLeftTextGroupClickListener() {
            @Override
            public void onClickListener(View view) {
                createPoster();
            }
        });
    }
    private void createPoster() {
        Toasty.info(me,"生成分享图中...",Toasty.LENGTH_SHORT).show();
        int width = rootView.getMeasuredWidth();
        int height = rootView.getMeasuredHeight();
        /*
         * Config.RGB_565:每个像素2字节（byte）
         * ARGB_4444：2字节（已过时）
         * ARGB_8888:4字节
         * RGBA_F16：8字节
         * */
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        rootView.draw(canvas);
        Toasty.success(me,"生成地址:"+ImageHelper.compressImage(me,bitmap).getAbsolutePath(),Toasty.LENGTH_SHORT).show();
    }
    public void updateBG(String path){
        linearLayout.setBackground(ImageHelper.getByPrivatePath(me,path));
    }
    private void setRefreshLayout(){
        refreshLayout.setRefreshHeader(new ClassicsHeader(me));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(settingManager.canSync()){
                    SyncManager syncManager=new SyncManager(me);
                    syncManager.upDate(handler);
                }else {
                    Toasty.info(me,"请先设置同步账户",Toasty.LENGTH_SHORT).show();
                    refreshlayout.finishRefresh(false);//传入false表示刷新失败
                }

            }
        });

    }
}
