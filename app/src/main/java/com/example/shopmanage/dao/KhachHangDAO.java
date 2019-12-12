package com.example.shopmanage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shopmanage.datahelper.DataHelper;
import com.example.shopmanage.model.KhachHang;
import com.example.shopmanage.model.SignUpUser;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    public static final String TB_NAME = "khachhang";
    private static final String ID = "id_kh";
    private static final String NAME = "name_kh";
    private static final String SDT = "sdt_kh";
    private static final String GIOITINH = "gioitinh_kh";
    private static final String DIACHI = "diachi_kh";

    private static final String TAG = "khachhangdao";
    private SQLiteDatabase db;
    private DataHelper dataHelper;

    public KhachHangDAO(Context context){
        dataHelper = new DataHelper(context);
        db = dataHelper.getWritableDatabase();
    }

    public static final String create_tb = " create table " + TB_NAME + " ( " + ID + " integer primary key, "
            + NAME + " text not null, " + SDT + " text not null, "
            + GIOITINH + " text not null, " + DIACHI + " text not null )";
    public int add(KhachHang khachHang){
        ContentValues values = new ContentValues();
        values.put(NAME,khachHang.getName());
        values.put(SDT,khachHang.getSdt());
        values.put(GIOITINH,khachHang.getGioiTinh());
        values.put(DIACHI,khachHang.getDiaChi());
        if (db.insert(TB_NAME,null,values) > 0){
            return 1;
        }
        return -1;
    }
    public List<KhachHang> getAll(){
        List<KhachHang> list = new ArrayList<>();
        String select = " select * from " +TB_NAME;
        Cursor cursor = db.rawQuery(select,null);
        if (cursor.moveToFirst()) {
            do {
                KhachHang khachHang = new KhachHang();
                khachHang.setId(cursor.getInt(0));
                khachHang.setName(cursor.getString(1));
                khachHang.setSdt(cursor.getString(2));
                khachHang.setGioiTinh(cursor.getString(3));
                khachHang.setDiaChi(cursor.getString(4));
                list.add(khachHang);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public int delete(KhachHang khachHang) {
        return db.delete(TB_NAME, ID + " = ? ", new String[]{String.valueOf(khachHang.getId())});
    }
    public int updateHandler(KhachHang khachHang) {

        ContentValues values = new ContentValues();
        values.put(NAME, khachHang.getName());
        values.put(SDT, khachHang.getSdt());
        values.put(DIACHI, khachHang.getDiaChi());
        return db.update(TB_NAME, values, ID + " = ? ", new String[]{String.valueOf(khachHang.getId())});
    }

}
