package com.paul.himynote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.allen.library.CircleImageView;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.DarkNavigationBarTheme;
import com.kongzue.baseframework.interfaces.DarkStatusBarTheme;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.interfaces.NavigationBarBackgroundColor;
import com.kongzue.baseframework.util.JumpParameter;

import es.dmoral.toasty.Toasty;


@Layout(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_head_icon)
    CircleImageView headIcon;

    @Override
    public void initViews() {

    }

    @Override
    public void initDatas(JumpParameter parameter) {

    }

    @Override
    public void setEvents() {
        headIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(me,"你好，我是佩奇~",Toasty.LENGTH_SHORT).show();
            }
        });
    }
}