package com.paul.himynote.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getCurDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
