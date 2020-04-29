package com.paul.himynote.Tools;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.paul.himynote.Model.NoteBean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
    public static String getCurDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
    /**
     * 计算两个日期之间的差值
     * B-A
     * 更新JDK8的计算方法，更准确兼容性更好
     * 显然这个方法都不适合其他时区···
     * */
    public static long countDayToInt(String dateA, String dateB){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            return ChronoUnit.DAYS.between(LocalDate.parse(dateA),LocalDate.parse(dateB));
        }else {
            String[] splitdate = dateA.split("-");
            String[] splitdateB = dateB.split("-");
            Calendar a = Calendar.getInstance(),
                    b = Calendar.getInstance();
            a.set(Integer.parseInt(splitdate[0]), Integer.parseInt(splitdate[1]) - 1, Integer.parseInt(splitdate[2]));
            b.set(Integer.parseInt(splitdateB[0]), Integer.parseInt(splitdateB[1]) - 1, Integer.parseInt(splitdateB[2]));
            return (b.getTimeInMillis() - a.getTimeInMillis())
                    / (1000 * 60 * 60 * 24);
        }

    }
    public static String getCountDay(NoteBean noteBean){
        int min=(int)countDayToInt(getCurDate(),noteBean.getEndDate());
        if(min==0){
            return "今日到期";
        }else if(min>0){
            return min+" 天";
        }else {
            return "已过 "+(Math.abs(min))+" 天";
        }
    }
    public static String getMinDays(List<NoteBean> noteBeans){
        int []days=new int[noteBeans.size()];
        for(int i=0;i<noteBeans.size();i++){
            days[i]=(int)countDayToInt(getCurDate(),noteBeans.get(i).getEndDate());
        }
        int min=days[0];
        for(int i=1;i<days.length;i++){
            if(min<0){
                min=days[i];
            }else {
                if(days[i]>=0&&days[i]<min){
                    min=days[i];
                }
            }
        }
        if(min==0){
            return "今日到期";
        }else if(min>0){
            return min+" 天";
        }else {
            return "已过 "+(Math.abs(min))+" 天";
        }
    }
}
