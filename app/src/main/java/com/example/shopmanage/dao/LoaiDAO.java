package com.example.shopmanage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.shopmanage.datahelper.DataHelper;
import com.example.shopmanage.model.Loai;

import java.util.ArrayList;
import java.util.List;

public class LoaiDAO {

    private SQLiteDatabase db;
    private DataHelper dataHelper;

    public static final String TB_NAME = "loaisp";
    private static final String ID = "id_loai";
    private static final String NAME = "name_Loai";
    private static final String MOTA = "mota_loai";

    private static final String TAG = "loaidao";

    public LoaiDAO(Context context){
        dataHelper = new DataHelper(context);
        db = dataHelper.getWritableDatabase();
    }

    public static final String create_tb = " create table " + TB_NAME + " ( " + ID + " text primary key, "
            + NAME + " text, " + MOTA + " text )";

    public int add(Loai loai){
        ContentValues values = new ContentValues();
        values.put(ID,loai.getId());
        values.put(NAME,loai.getName());
        values.put(MOTA,loai.getMoTa());
        if (db.insert(TB_NAME,null,values) > 0){
            return 1;

        }
        return -1;
    }
    public List<Loai> getAll(){
        List<Loai> list = new ArrayList<>();
        String select = " select * from "+TB_NAME;
        Cursor cursor = db.rawQuery(select,null);
        if (cursor.moveToFirst()){
            do {
                Loai loai = new Loai();
                loai.setId(cursor.getString(0));
                loai.setName(cursor.getString(1));
                loai.setMoTa(cursor.getString(2));
                list.add(loai);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }


}
