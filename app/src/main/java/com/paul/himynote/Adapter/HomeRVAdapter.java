package com.paul.himynote.Adapter;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.paul.himynote.Animation.ScaleInAnimation;
import com.paul.himynote.Manager.NoteBeanManager;
import com.paul.himynote.Manager.SettingManager;
import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.R;


import java.util.List;


public class HomeRVAdapter extends RecyclerView.Adapter<HomeRVAdapter.ViewHolder> {
    private List<NoteBean> noteBeans;
    private onItemClickListener clickListener;
    private ScaleInAnimation mSelectAnimation = new ScaleInAnimation();


    static class ViewHolder extends RecyclerView.ViewHolder {
        View noteView;
        TextView tv_theme,tv_clock,tv_content;
        CardView cv_main;


        public ViewHolder(View view) {
            super(view);
            noteView = view;
            tv_theme=view.findViewById(R.id.tv_theme);
            tv_clock=view.findViewById(R.id.tv_clock);
            tv_content=view.findViewById(R.id.tv_show);
            cv_main=view.findViewById(R.id.cv_main);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        addAnimation(holder);
    }
    private void addAnimation(ViewHolder holder) {
        for (Animator anim : mSelectAnimation.getAnimators(holder.itemView)) {
            anim.setDuration(300).start();
            anim.setInterpolator(new LinearInterpolator());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lv_item_home, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NoteBean noteBean = noteBeans.get(position);
        holder.tv_content.setText(noteBean.getContent());
        holder.tv_clock.setText(noteBean.getAddDate());
        holder.tv_theme.setText(noteBean.getTheme());
        holder.cv_main.setCardBackgroundColor(noteBean.getColor());
        holder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(noteBean);
            }
        });
        holder.cv_main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener.onLongClick(noteBean);
                return false;
            }
        });
        holder.cv_main.setAlpha(new SettingManager(holder.cv_main.getContext()).getAlpha_number());
    }

    @Override
    public int getItemCount() {
        return noteBeans.size();
    }

    public HomeRVAdapter(List<NoteBean> noteBeans,onItemClickListener clickListener) {
        this.noteBeans = noteBeans;
        this.clickListener=clickListener;
    }
    public void refreash(List<NoteBean> noteBeans) {
        this.noteBeans.clear();
        this.noteBeans = noteBeans;
        notifyDataSetChanged();
    }
    public interface onItemClickListener{
        void onClick(NoteBean noteBean);
        void onLongClick(NoteBean noteBean);
    }
    public void refreashAlpha(Float alpha){
        notifyDataSetChanged();
    }

}
