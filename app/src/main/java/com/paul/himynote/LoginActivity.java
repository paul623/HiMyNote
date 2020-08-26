package com.paul.himynote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.CircleImageView;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.DarkNavigationBarTheme;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.interfaces.NavigationBarBackgroundColor;
import com.kongzue.baseframework.util.JumpParameter;
import com.paul.himynote.Manager.UserService;
import com.paul.himynote.Tools.SpHelper;

import es.dmoral.toasty.Toasty;


@Layout(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_head_icon)
    CircleImageView headIcon;
    @BindView(R.id.login_tv_register)
    TextView tv_register;
    @BindView(R.id.login_btn)
    Button btn;
    @BindView(R.id.login_et_username)
    EditText et_username;
    @BindView(R.id.login_et_password)
    EditText et_password;
    @BindView(R.id.login_cb)
    CheckBox login_cb;
    UserService userService;

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas(JumpParameter parameter) {
        userService=new UserService(me);
        if(userService.canLogin()){
            et_username.setText(userService.getUserName());
            et_password.setText(userService.getPassword());
            login_cb.setChecked(true);
        }
    }

    @Override
    public void setEvents() {
        headIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(me,"你好，我是佩奇~",Toasty.LENGTH_SHORT).show();
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userService.login(et_username.getText().toString(),et_password.getText().toString(),login_cb.isChecked())){
                    Toasty.success(me,"登录成功！",Toasty.LENGTH_SHORT).show();
                    jump(MainActivity.class);
                }else{
                    Toasty.error(me,"用户名或者密码错误",Toasty.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void showRegisterDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_register, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //BindView
        EditText et_name,et_pass,et_re_pass;
        et_name=dialog.findViewById(R.id.register_et_username);
        et_pass=dialog.findViewById(R.id.register_et_password);
        et_re_pass=dialog.findViewById(R.id.register_et_confirm_password);
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> {
            String shareURL="https://www.yuque.com/docs/share/45829fb8-9586-4971-909b-aa12cf640d7e?#";
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);//为Intent设置动作
            intent.setData(Uri.parse(shareURL));//为Intent设置数据
            startActivity(intent);//将Intent传递给Activity
            dialog.dismiss();
        });
        dialog.findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if(!et_pass.getText().toString().equals(et_re_pass.getText().toString())){
                Toasty.error(me,"两次密码输入不一致！",Toasty.LENGTH_SHORT).show();
            }else {
                userService.register(et_name.getText().toString().trim(),et_pass.getText().toString().trim());
                Toasty.success(me,"注册成功！",Toasty.LENGTH_SHORT).show();
                dialog.dismiss();
            }

        });
        dialog.show();


    }

}