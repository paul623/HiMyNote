package com.paul.himynote.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.paul.himynote.Model.NoteBean;
import com.paul.himynote.Tools.SQLHelper;

public class NoteBeanDao {
    Context context;
    SQLiteDatabase db;
    public NoteBeanDao(Context context) {
        this.context = context;
        db=(new SQLHelper(context,"note.db",null,1)).getWritableDatabase();
    }
    public void add(NoteBean bean){

    }
}
