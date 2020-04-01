package com.paul.himynote.Adapter;

import android.content.Context;
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
import com.paul.himynote.Tools.DateUtils;

import java.util.List;

public class ViewThemeListAdapter extends BaseAdapter {
    Context context;
    List<NoteBean> noteBeans;


    public ViewThemeListAdapter(Context context, List<NoteBean> noteBeans) {
        this.context = context;
        this.noteBeans = noteBeans;
    }

    @Override
    public int getCount() {
        return noteBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return noteBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.lv_item_view_theme, null);
        CardView cardView = view.findViewById(R.id.cv_theme);
        TextView tv_content = view.findViewById(R.id.tv_theme_content);
        TextView tv_day = view.findViewById(R.id.tv_theme_day);
        TextView tv_date = view.findViewById(R.id.tv_theme_enddtae);
        NoteBean noteBean = noteBeans.get(position);
        tv_content.setTextSize(18f);
        cardView.setCardBackgroundColor(noteBean.getColorID());
        tv_content.setText(noteBean.getContent());
        tv_date.setText(noteBean.getEndDate());
        tv_day.setText(DateUtils.getCountDay(noteBean));
        return view;
    }

}
