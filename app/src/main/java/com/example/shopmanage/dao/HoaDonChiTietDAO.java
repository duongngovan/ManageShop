package com.example.shopmanage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.shopmanage.datahelper.DataHelper;
import com.example.shopmanage.model.HoaDonChiTiet;

public class HoaDonChiTietDAO {

    public static final String TB_NAME = "hoadonchitiet";
    private static final String ID = "id_hoadon";
    private static final String ID_NAME = "id_name_sp";
    private static final String SO_LUONG = "soluong_hdct";

    private SQLiteDatabase db;
    private DataHelper dataHelper;
    private static final String TAG = "hoadonchitietdao";
    public static final String create_tb = " create table "+TB_NAME+ " ( "+ID+ " text primary key, "+ID_NAME+ " text, " +SO_LUONG+ " integer )";

    public HoaDonChiTietDAO(Context context){
        dataHelper = new DataHelper(context);
        db = dataHelper.getWritableDatabase();
    }

    public int add(HoaDonChiTiet hoaDonChiTiet){
        ContentValues values = new ContentValues();
        values.put(ID,hoaDonChiTiet.getIdHoaDon());
        values.put(ID_NAME,hoaDonChiTiet.getSpIDSp());
        values.put(SO_LUONG,hoaDonChiTiet.getSoLuong());
        if (db.insert(TB_NAME,null,values) > 0){
            return 1;
        }
        db.close();
        return -1;
    }
}
