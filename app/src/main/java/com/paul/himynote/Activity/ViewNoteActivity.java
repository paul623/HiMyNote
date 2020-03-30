package com.paul.himynote.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.interfaces.SwipeBack;
import com.kongzue.baseframework.util.JumpParameter;
import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.R;

import es.dmoral.toasty.Toasty;
@SwipeBack(true)
@Layout(R.layout.activity_view_note)
public class ViewNoteActivity extends BaseActivity {

    @BindView(R.id.stv_view_toolbar)
    SuperTextView toolbar;
    @BindView(R.id.tv_view_clock)
    TextView tv_clock;
    @BindView(R.id.tv_view_content)
    TextView tv_content;

    NoteBean noteBean;

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
    public void initDatas(JumpParameter parameter) {
        noteBean=(NoteBean)parameter.get("data");
        if(noteBean==null){
            Toasty.error(me,"出错了！",Toasty.LENGTH_SHORT).show();
            finish();
        }
        tv_content.setText(noteBean.getContent());
        String clock=noteBean.getAddDate();
        if(noteBean.getEndDate()!=null&&!noteBean.getEndDate().equals("")){
            clock=clock+"~"+noteBean.getEndDate();
        }
        tv_clock.setText(clock);
        ColorDrawable drawable=new ColorDrawable(noteBean.getColorID());
        toolbar.setLeftIcon(drawable);
        toolbar.setLeftString(noteBean.getTitle());
        toolbar.setRightBottomString(noteBean.getTheme());
    }

    @Override
    public void setEvents() {

    }
}
