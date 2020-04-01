package com.paul.himynote.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.allen.library.SuperTextView;
import com.paul.himynote.Manager.NoteBeanManager;
import com.paul.himynote.Model.KindsBean;
import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.R;

import java.util.List;

public class TagListViewAdapter extends BaseAdapter {
    Context context;
    List<KindsBean> kindsBeans;


    public TagListViewAdapter(Context context) {
        this.context = context;
        kindsBeans= NoteBeanManager.getKinds();
    }

    @Override
    public int getCount() {
        return kindsBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return kindsBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.lv_item_view_theme,null);
        CardView cardView = view.findViewById(R.id.cv_theme);
        TextView tv_content = view.findViewById(R.id.tv_theme_content);
        TextView tv_day = view.findViewById(R.id.tv_theme_day);
        TextView tv_date = view.findViewById(R.id.tv_theme_enddtae);
        KindsBean kindsBean = kindsBeans.get(position);
        cardView.setCardBackgroundColor(kindsBean.getColor());
        tv_content.setText(kindsBean.getTheme());
        tv_day.setText(kindsBean.getNearCountDay());
        tv_date.setText("还有 "+kindsBean.getNumber()+" 任务");
        return view;
    }
    public void refresh(){
        kindsBeans= NoteBeanManager.getKinds();
        notifyDataSetChanged();
    }

}
