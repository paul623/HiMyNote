package com.paul.himynote.Tools;

import android.graphics.Color;

import java.util.Date;
import java.util.Random;

public class ColorPool {
    public static final String[] COURSE_BACKGROUND_COLOR={"#FAEBD7","#FFE4C4","#D1EEEE","#FFEC8B","#B3EE3A","#7FFF00","#FFD700","#FF6A6A","#FFA500","#EE82EE","#FF4040","#D8BFD8","#BFEFFF"};
    public static final String[] LIGHT_COLOUR={"#FFFFCC","#CCFFFF","#FF9966",
            "#FF6666","#FFCCCC","#FFCC99","#CCFF99","#CCFFCC","#CCCC99","#0099FF",
            "#F5F5F5","#FF9933","#FF99CC","#FF6600"};
    public static final int[] LIGHT_COLOUR_INT={Color.parseColor("#FFFFCC"),Color.parseColor("#CCFFFF"),
            Color.parseColor("#FF9966"),Color.parseColor("#FF6666"),
            Color.parseColor("#FFCCCC"),Color.parseColor("#FFCC99"),
            Color.parseColor("#CCFF99"),Color.parseColor("#CCFFCC"),
            Color.parseColor("#CCCC99"),Color.parseColor("#0099FF"),
            Color.parseColor("#F5F5F5"),Color.parseColor("#FF9933"),
            Color.parseColor("#FF99CC"),Color.parseColor("#FF6600")};
    public static String getColor(){
        Date date=new Date();
        Random random=new Random(date.getTime());
        String colorString=LIGHT_COLOUR[(random.nextInt(LIGHT_COLOUR.length))];
        return colorString;
    }
    public static String convertToARGB(int color){
        String red=Integer.toHexString(Color.red(color));
        String green=Integer.toHexString(Color.green(color));
        String blue=Integer.toHexString(Color.blue(color));
        return "#"+red+green+blue;
    }
}
