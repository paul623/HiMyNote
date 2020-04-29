package com.paul.himynote.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.paul.himynote.Manager.TagManager;
import com.paul.himynote.Manager.WordsManager;
import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.R;
import com.paul.himynote.Activity.ViewNoteActivity;
import com.paul.himynote.Tools.ImageHelper;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

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
    public TagManager tagManager;
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
    private DataChanagedListener dataChanagedListener;
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
        tagManager=new TagManager(me);
        settingManager=new SettingManager(me);
        List<NoteBean> noteBeans=NoteBeanManager.getAll();
        noteBeans.add(NoteBeanManager.getDefaultNotes(me));
        homeRVAdapter=new HomeRVAdapter(NoteBeanManager.getAll(), new HomeRVAdapter.onItemClickListener() {
            @Override
            public void onClick(NoteBean noteBean) {
                jump(ViewNoteActivity.class,new JumpParameter().put("data",noteBean),new OnJumpResponseListener() {
                    @Override
                    public void OnResponse(JumpParameter jumpParameter) {
                        if(jumpParameter.get("result")!=null&&(boolean)jumpParameter.get("result")){
                            homeRVAdapter.refreash(NoteBeanManager.getAll());
                            dataChanagedListener.dataChanaged();
                        }
                    }
                });
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
                            dataChanagedListener.dataChanaged();
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
                            dataChanagedListener.dataChanaged();
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
        File file=ImageHelper.compressImage(me,bitmap);
        Toasty.success(me,"生成地址:"+file.getAbsolutePath(),Toasty.LENGTH_SHORT).show();
        try {
            MediaStore.Images.Media.insertImage(me.getContentResolver(), file.getAbsolutePath(), "星月记"+file.getName(), null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            me.sendBroadcast(intent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


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
        BindRV();
    }
    private void BindRV(){
        //为RecycleView绑定触摸事件
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //首先回调的方法 返回int表示是否监听该方向
                int dragFlags =  ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT |ItemTouchHelper.UP|ItemTouchHelper.DOWN;//拖拽
                int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;//侧滑删除
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //滑动事件
                List<NoteBean> noteBeans=homeRVAdapter.getList();
                Collections.swap(noteBeans,viewHolder.getAdapterPosition(),target.getAdapterPosition());
                homeRVAdapter.setList(noteBeans);
                homeRVAdapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                NoteBeanManager.saveAll(noteBeans);
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑事件
                NoteBean noteBean=homeRVAdapter.getList().get(viewHolder.getAdapterPosition());
                tagManager.removeTagBean(noteBean);
                noteBean.delete();
                homeRVAdapter.getList().remove(viewHolder.getAdapterPosition());
                homeRVAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                dataChanagedListener.dataChanaged();
                Toasty.success(me,"删除成功！",Toasty.LENGTH_SHORT).show();
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //是否可拖拽
                return true;
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }
    public interface DataChanagedListener{
        public void dataChanaged();
    }
    public void setDataChanagedListener(DataChanagedListener listener){
        dataChanagedListener=listener;
    }
}
