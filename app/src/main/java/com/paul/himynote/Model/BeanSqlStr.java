package com.paul.himynote.Model;

//这里用来放置sql建表语句
public class BeanSqlStr {
    public static String NoteBeanSQL="create table note("
            + "id integer primary key autoincrement,"
            + "title String,"
            + "flag boolean,"
            + "theme String,"
            + "theme_number int,"
            +"colorID int,"
            +"content String,"
            +"addDate String,"
            +"finishDate String,"
            +"endDate String,"
            +"color String)";;
}
