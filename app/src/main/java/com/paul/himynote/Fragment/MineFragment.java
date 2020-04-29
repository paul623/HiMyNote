package com.paul.himynote.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.allen.library.SuperTextView;
import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.Layout;
import com.paul.himynote.MainActivity;
import com.paul.himynote.Manager.SettingManager;
import com.paul.himynote.Manager.SyncManager;
import com.paul.himynote.R;
import com.paul.himynote.Tools.ImageHelper;
import com.paul.himynote.View.EditDialog;

import es.dmoral.toasty.Toasty;

@Layout(R.layout.fragment_setting)
public class MineFragment extends BaseFragment<MainActivity> {

    @BindView(R.id.stv_mine_bg)
    SuperTextView stv_mine_bg;
    @BindView(R.id.stv_mine_account)
    SuperTextView stv_mine_account;
    @BindView(R.id.stv_mine_pull)
    SuperTextView stv_mine_pull;
    @BindView(R.id.stv_mine_push)
    SuperTextView stv_mine_push;
    @BindView(R.id.sb_mine_alpha)
    SeekBar seekBar;
    @BindView(R.id.stv_mine_headbar)
    SuperTextView headbar;
    @BindView(R.id.stv_mine_about)
    SuperTextView stv_mine_about;
    SettingManager settingManager;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    Toasty.success(me,"上传备份成功！",Toasty.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toasty.success(me,"从云端恢复成功！",Toasty.LENGTH_SHORT).show();
                    bgChangedListener.update();
                    break;
                case -1:
                    Toasty.error(me,"上传备份失败",Toasty.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toasty.error(me,"从云端恢复失败",Toasty.LENGTH_SHORT).show();
                    break;


            }
            return false;
        }
    });

    private BgChangedListener bgChangedListener;
    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {
        settingManager=new SettingManager(me);
        int seek_number= (int) (settingManager.getAlpha_number()*10);
        seekBar.setProgress(seek_number);
        headbar.setRightString(settingManager.getHead_username());
        if(settingManager.getHeadicon_path().equals("")){
            headbar.setLeftIcon(me.getDrawable(R.drawable.ic_head));
        }else {
            headbar.setLeftIcon(ImageHelper.getByPrivatePath(me,settingManager.getHeadicon_path()));
        }

    }

    @Override
    public void setEvents() {
        headbar.setRightTextGroupClickListener(new SuperTextView.OnRightTextGroupClickListener() {
            @Override
            public void onClickListener(View view) {
                EditDialog editDialog = new EditDialog(me);
                editDialog.show();
                editDialog.setOnPosNegClickListener(new EditDialog.OnPosNegClickListener() {
                    @Override
                    public void posClickListener(String value) {
                       headbar.setRightString(value);
                       Toasty.success(me,"设置成功",Toasty.LENGTH_SHORT).show();
                       settingManager.setHead_username(value);
                    }

                    @Override
                    public void negCliclListener() {

                    }
                });

            }
        });
        headbar.setLeftImageViewClickListener(new SuperTextView.OnLeftImageViewClickListener() {
            @Override
            public void onClickListener(ImageView imageView) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 3);
            }
        });
        stv_mine_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Double s=progress/10.0;
                settingManager.setAlpha_number(s.floatValue());
                bgChangedListener.updateAlpha(s.floatValue());
                bgChangedListener.updateAlpha(s.floatValue());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        stv_mine_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserAccountDialog();
            }
        });
        stv_mine_pull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingManager.canSync()){
                    SyncManager syncManager=new SyncManager(me);
                    syncManager.getCloudFiles(handler);
                }else {
                    showUserAccountDialog();
                }
            }
        });
        stv_mine_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingManager.canSync()){
                    SyncManager syncManager=new SyncManager(me);
                    syncManager.upDate(handler);
                }else {
                    showUserAccountDialog();
                }
            }
        });
        stv_mine_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAboutMeDialog();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==2){
            if(data!=null){
                Uri originalUri = data.getData(); // 获得图片的uri
                String path= ImageHelper.getPrivatePath(me,originalUri);
                if(path!=null&&!path.equals("")){
                    Toasty.success(me,"设置成功",Toasty.LENGTH_SHORT).show();
                    ImageHelper.updtaeBackground(settingManager.getBg_path());
                    settingManager.setBg_path(path);
                    bgChangedListener.setBG(path);
                }
            }
        }else if(requestCode==3){
            if(data!=null) {
                Uri originalUri = data.getData(); // 获得图片的uri
                String path = ImageHelper.getPrivatePath(me, originalUri);
                if (path != null && !path.equals("")) {
                    Toasty.success(me, "设置成功", Toasty.LENGTH_SHORT).show();
                    ImageHelper.updtaeBackground(settingManager.getHeadicon_path());
                    settingManager.setHeadicon_path(path);
                    headbar.setLeftIcon(ImageHelper.getByPrivatePath(me,path));
                }
            }
        }
    }
    public interface BgChangedListener{
        public void setBG(String path);
        public void updateAlpha(Float s);
        public void update();
    }
    public void setBgChangedListener(BgChangedListener listener){
        this.bgChangedListener=listener;
    }
    private void showUserAccountDialog(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(me,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(me,R.layout.activity_user_account_input,null);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //BindView
        final EditText et_server =dialog.findViewById(R.id.et_server);
        final EditText et_username=dialog.findViewById(R.id.et_name);
        final EditText et_password=dialog.findViewById(R.id.et_password);
        et_password.setText(settingManager.getPassword());
        et_server.setText(settingManager.getUserServer());
        et_username.setText(settingManager.getUsername());
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_server.getText().toString()==null||et_server.getText().toString().equals("")){
                    et_server.setText("https://dav.jianguoyun.com/dav/");
                }
                if(!et_server.getText().toString().contains("http")){
                    Toasty.info(me,"暂时不支持非http/https的网址",Toasty.LENGTH_SHORT).show();
                }else {
                    settingManager.setUserServer(et_server.getText().toString());
                    settingManager.setPassword(et_password.getText().toString());
                    settingManager.setUsername(et_username.getText().toString());
                    Toasty.success(me,"设置成功！",Toasty.LENGTH_SHORT).show();
                    dialog.dismiss();
                }


            }
        });
        dialog.show();

    }

    private void showAboutMeDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(me,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(me,R.layout.activity_about_me,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //BindView
        TextView textView =dialog.findViewById(R.id.tv_version);
        textView.setText("星月记 v"+ getAppVersionName(me));
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String qq="mqqwpa://im/chat?chat_type=wpa&uin=" + "2499761614";//跳转QQ
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(qq)));
                } catch (Exception e) {
                    Toasty.error(me, "请检查是否安装QQ！", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareURL="https://www.yuque.com/docs/share/45829fb8-9586-4971-909b-aa12cf640d7e?#";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
                intent.setData(Uri.parse(shareURL));//为Intent设置数据
                startActivity(intent);//将Intent传递给Activity
                dialog.dismiss();
            }
        });
        dialog.show();



    }
    public static String getAppVersionName(Context context) {
        String appVersionName = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            appVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("", e.getMessage());
        }
        return appVersionName;
    }
}
