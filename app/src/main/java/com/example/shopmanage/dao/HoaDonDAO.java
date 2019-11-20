package com.example.shopmanage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shopmanage.datahelper.DataHelper;
import com.example.shopmanage.model.HoaDon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static final String TB_NAME = "hoadon";
    private static final String ID = "id_hoadon";
    private static final String NAME = "name_kh";
    private static final String DATE = "ngay_hoadon";

    private SQLiteDatabase db;
    private DataHelper dataHelper;
    private static final String TAG = "hoadonDAO";

    public static final String create_tb = " create table "+TB_NAME+ " ( " +ID+ " text primary key, " +NAME+ " text, "
                                          +DATE+ " text )";
    public HoaDonDAO(Context context){
        dataHelper = new DataHelper(context);
        db = dataHelper.getWritableDatabase();
    }
    public int add(HoaDon hoaDon){
        ContentValues values = new ContentValues();
        values.put(ID,hoaDon.getId());
        values.put(NAME,hoaDon.getNamekh());
        values.put(DATE,sdf.format(hoaDon.getNgay()));
        if (db.insert(TB_NAME,null,values) > 0){
            return 1;
        }
        return -1;
    }
    public List<HoaDon> getAll(){
        List<HoaDon> list = new ArrayList<>();
        String select = " select * from "+TB_NAME;
        Cursor cursor = db.rawQuery(select,null);
        if (cursor.moveToFirst()) {
            do {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(cursor.getString(0));
                hoaDon.setNamekh(cursor.getString(1));
                try {
                    hoaDon.setNgay(sdf.parse(cursor.getString(2)));
                }catch (ParseException e){
                    e.toString();
                }
                list.add(hoaDon);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
