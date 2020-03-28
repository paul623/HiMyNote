package com.paul.himynote.Tools;

import android.graphics.Color;

import java.util.Date;
import java.util.Random;

public class ColorPool {
    public static final String[] COURSE_BACKGROUND_COLOR={"#FAEBD7","#FFE4C4","#D1EEEE","#FFEC8B","#B3EE3A","#7FFF00","#FFD700","#FF6A6A","#FFA500","#EE82EE","#FF4040","#D8BFD8","#BFEFFF"};
    public static final String[] LIGHT_COLOUR={"#FFFFFF","#CCFF99","#99CCFF","#99CCCC","#CCFF99","#CCFF99","#FFFFCC","#FF9966","#FF6666","#CCFF99","#CCCCFF","#CCFF00","#CC3399","#FF6600"};
    public static final int[] LIGHT_COLOUR_INT={Color.parseColor("#FFFFFF"),Color.parseColor("#CCFF99"),
            Color.parseColor("#99CCFF"),Color.parseColor("#99CCCC"),
            Color.parseColor("#CCFF99"),Color.parseColor("#CCFF99"),
            Color.parseColor("#FFFFCC"),Color.parseColor("#FF9966"),
            Color.parseColor("#FF6666"),Color.parseColor("#CCFF99"),
            Color.parseColor("#CCCCFF"),Color.parseColor("#CCFF00"),
            Color.parseColor("#CC3399"),Color.parseColor("#FF6600")};
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
