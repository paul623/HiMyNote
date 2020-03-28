package com.paul.himynote.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ImageHelper {
    /**
     * 根据Uri直接获取图片
     * @param context 上下文
     * @param uri 图片的uri
     * */
    public static String getPrivatePath(Context context, Uri uri){
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            File file=compressImage(context,bitmap);
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 把bitmap写入app私有目录下
     * @param context 上下文
     * @param bitmap 这个bitmap不能为null
     * @return File
     * */
    public static File compressImage(Context context, Bitmap bitmap) {
        String filename;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date(System.currentTimeMillis());
            //图片名
            filename = format.format(date);
        }else {
            Date date=new Date();
            filename=date.getYear()+date.getMonth()+date.getDate()+date.getHours()+date.getMinutes()+date.getSeconds()+"";
        }

        final File[] dirs = context.getExternalFilesDirs("Documents");
        File primaryDir = null;
        if (dirs != null && dirs.length > 0) {
            primaryDir = dirs[0];
        }
        if(primaryDir==null){
            return null;
        }
        File file = new File(primaryDir.getAbsolutePath(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }

        // recycleBitmap(bitmap);
        return file;
    }
    /**
     * 根据私有目录地址删除图片
     * */
    public static void updtaeBackground(String privatePath){
        File file=new File(privatePath);
        if(file.exists()){
            file.delete();
        }
    }
    /**
     * 根据私有路径加载
     * @param context 上下文
     * @param path 这个路径一定是私有路径，即应用自己的目录下（data/包名）
     * @return Drawable 用来设置背景什么的
     * */
    public static Drawable getByPrivatePath(Context context, String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        return  new BitmapDrawable(context.getResources(), bitmap);
    }
}
