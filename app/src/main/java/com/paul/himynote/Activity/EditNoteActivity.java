package com.paul.himynote.Activity;



import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;

import com.github.mummyding.colorpickerdialog.ColorPickerDialog;
import com.github.mummyding.colorpickerdialog.OnColorChangedListener;
import com.kongzue.baseframework.BaseActivity;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.util.JumpParameter;
import com.paul.himynote.Manager.NoteBeanManager;
import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.R;
import com.paul.himynote.Tools.ColorPool;
import com.paul.himynote.Tools.DateUtils;

import java.util.Date;

import es.dmoral.toasty.Toasty;


@Layout(R.layout.activity_edit_note)
public class EditNoteActivity extends BaseActivity {

    NoteBean noteBean;
    private boolean editable;
    @BindView(R.id.stv_edit_bar)
    SuperTextView toolbar;
    @BindView(R.id.stv_edit_color)
    SuperTextView stv_edit_color;
    @BindView(R.id.stv_edit_clock)
    SuperTextView stv_edit_clock;
    @BindView(R.id.sb_edit_delete)
    Button btn_edit_delete;
    @BindView(R.id.et_edit_theme)
    AutoCompleteTextView et_edit_theme;
    @BindView(R.id.et_edit_title)
    EditText et_edit_title;
    @BindView(R.id.et_edit_content)
    EditText et_edit_content;


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
        editable=(boolean)parameter.get("flag");
        et_edit_theme.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line ,NoteBeanManager.getThemesName()));
        if(editable){
            noteBean=(NoteBean)parameter.get("data");
            noteBean= NoteBeanManager.getRealNoteBean(noteBean);
            toolbar.setCenterString("编辑笔记");
            ColorDrawable drawable=new ColorDrawable(noteBean.getColorID());
            stv_edit_color.setLeftIcon(drawable);
            stv_edit_clock.setCenterString(noteBean.getAddDate()+"~"+noteBean.getEndDate());
            et_edit_content.setText(noteBean.getContent());
            et_edit_theme.setText(noteBean.getTheme());
            et_edit_title.setText(noteBean.getTitle());
            btn_edit_delete.setVisibility(View.VISIBLE);
        }else {
            noteBean=new NoteBean();
            noteBean.setColorID(Color.parseColor(ColorPool.getColor()));
            ColorDrawable drawable=new ColorDrawable(noteBean.getColorID());
            stv_edit_color.setLeftIcon(drawable);
            stv_edit_clock.setCenterString(noteBean.getAddDate()+"~"+"请设置截止日期");
            btn_edit_delete.setVisibility(View.GONE);
        }

    }

    @Override
    public void setEvents() {
        toolbar.setLeftImageViewClickListener(new SuperTextView.OnLeftImageViewClickListener() {
            @Override
            public void onClickListener(ImageView imageView) {
                JumpParameter parameter=new JumpParameter();
                parameter.put("result",false);
                setResponse(parameter);
                finish();
            }
        });
        toolbar.setRightImageViewClickListener(new SuperTextView.OnRightImageViewClickListener() {
            @Override
            public void onClickListener(ImageView imageView) {
                noteBean.setTitle(et_edit_title.getText().toString());
                noteBean.setContent(et_edit_content.getText().toString());
                noteBean.setTheme(et_edit_theme.getText().toString());
                boolean result=noteBean.save();
                if(result){
                    Toasty.success(me,"保存成功！",Toasty.LENGTH_SHORT).show();
                }else {
                    Toasty.error(me,"保存失败！",Toasty.LENGTH_SHORT).show();
                }
                setResponse(new JumpParameter().put("result",result));
                finish();
            }
        });
        stv_edit_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String []dates= DateUtils.getCurDate().split("-");
                DatePickerDialog datePickerDialog=new DatePickerDialog(me, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d("测试",year+" "+month+" "+dayOfMonth);
                        String results=year+"-";
                        if(month<10){
                            results=results+"0";
                        }
                        results=results+(month+1)+"-";
                        if(dayOfMonth<10){
                            results=results+"0";
                        }
                        results=results+dayOfMonth;
                        noteBean.setEndDate(results);
                        stv_edit_clock.setCenterString(noteBean.getAddDate()+"~"+noteBean.getEndDate());
                        if(DateUtils.countDayToInt(DateUtils.getCurDate(),noteBean.getEndDate())<0){
                            Toasty.info(me,"不推荐设置以前的日期哦~",Toasty.LENGTH_SHORT).show();
                        }
                    }
                },Integer.parseInt(dates[0]),Integer.parseInt(dates[1])-1,Integer.parseInt(dates[2]));
                datePickerDialog.show();
            }
        });
        stv_edit_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialog colorPickerDialog=new ColorPickerDialog(me,ColorPool.LIGHT_COLOUR_INT)
                        .setDismissAfterClick(false)
                        .setTitle("选择颜色")
                        .setCheckedColor(noteBean.getColorID())
                        .setOnColorChangedListener(new OnColorChangedListener() {
                            @Override
                            public void onColorChanged(int color) {
                                noteBean.setColorID(color);
                                ColorDrawable drawable=new ColorDrawable(color);
                                stv_edit_color.setLeftIcon(drawable);
                            }
                        })
                        .build()
                        .show();
            }
        });
        btn_edit_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result=noteBean.delete();
                if(result==1){
                    Toasty.success(me,"删除成功！",Toasty.LENGTH_SHORT).show();
                }else {
                    Toasty.error(me,"删除异常或者是删除了多个信息",Toasty.LENGTH_SHORT).show();
                }
                setResponse(new JumpParameter().put("result",true));
                finish();
            }
        });

    }


}
