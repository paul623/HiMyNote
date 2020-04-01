package com.paul.himynote.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kongzue.baseframework.BaseFragment;
import com.kongzue.baseframework.interfaces.BindView;
import com.kongzue.baseframework.interfaces.Layout;
import com.kongzue.baseframework.util.JumpParameter;
import com.kongzue.baseframework.util.OnJumpResponseListener;
import com.paul.himynote.Activity.EditNoteActivity;
import com.paul.himynote.Activity.ViewNoteActivity;
import com.paul.himynote.Adapter.TagListViewAdapter;
import com.paul.himynote.Adapter.ViewThemeListAdapter;
import com.paul.himynote.MainActivity;
import com.paul.himynote.Manager.NoteBeanManager;
import com.paul.himynote.Model.KindsBean;
import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.R;

import es.dmoral.toasty.Toasty;

@Layout(R.layout.fragment_tag)
public class TagFragment extends BaseFragment<MainActivity> {

    @BindView(R.id.lv_tag_content)
    private ListView listView;
    TagListViewAdapter adapter;
    DataSetHasChangedLisener dataSetHasChangedLisener;
    @Override
    public void initViews() {

    }

    @Override
    public void initDatas() {
        adapter=new TagListViewAdapter(me);
        listView.setAdapter(adapter);
    }

    @Override
    public void setEvents() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KindsBean item =(KindsBean) adapter.getItem(position);
                showViewDialog(item);
            }
        });
    }
    public void refreshData(){
        adapter.refresh();
    }
    private void showViewDialog(KindsBean kindsBean) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(me,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(me,R.layout.dialog_view_theme,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //BindView
        TextView textView =dialog.findViewById(R.id.tv_theme);
        textView.setText(kindsBean.getTheme());
        ListView listView=dialog.findViewById(R.id.lv_content);
        ViewThemeListAdapter viewThemeListAdapter=new ViewThemeListAdapter(me,kindsBean.getData());
        listView.setAdapter(viewThemeListAdapter);
        dialog.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jump(ViewNoteActivity.class,new JumpParameter().put("data",kindsBean.getData().get(position)),new OnJumpResponseListener() {
                    @Override
                    public void OnResponse(JumpParameter jumpParameter) {
                        if(jumpParameter.get("result")!=null&&(boolean)jumpParameter.get("result")){
                            dataSetHasChangedLisener.dataChanged();
                            adapter.refresh();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });


    }
    public interface DataSetHasChangedLisener{
        public void dataChanged();
    }
    public void setDataSetHasChangedLisener(DataSetHasChangedLisener lisener){
        this.dataSetHasChangedLisener=lisener;
    }
}
