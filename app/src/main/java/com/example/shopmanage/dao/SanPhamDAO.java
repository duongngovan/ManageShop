package com.example.shopmanage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.shopmanage.datahelper.DataHelper;
import com.example.shopmanage.model.Loai;
import com.example.shopmanage.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {

    public static final String TB_NAME = "sanpham";
    private static final String ID = "id_sanpham";
    private static final String NAME = "name_sanpham";
    private static final String SPINNER = "id_loai";
    private static final String GIABAN = "giaban_sp";
    private static final String NGUONGOC = "nguongoc_sp";
    private static final String BAOHANH = "baohanh_sp";
    private static final String SOLUONG = "soluong_sp";
    private static final String IMAGE = "image";

    private SQLiteDatabase db;
    private DataHelper dataHelper;

    private static final String TAG = "sanphamDAO";

    public SanPhamDAO(Context context) {
        dataHelper = new DataHelper(context);
        db = dataHelper.getWritableDatabase();
    }

    public static final String create_tb = " create table " + TB_NAME + " ( " + ID + " text primary key, " +
            NAME + " text not null, " + SPINNER + " text not null, " +
            GIABAN + " real not null, " + NGUONGOC + " text not null, " +
            BAOHANH + " text not null, " + SOLUONG + " integer not null, " + IMAGE + " blob )";

    public int add(SanPham sanPham) {
        ContentValues values = new ContentValues();
        values.put(ID, sanPham.getId());
        values.put(SPINNER, sanPham.getSpinner());
        values.put(NAME, sanPham.getName());
        values.put(GIABAN, sanPham.getGiaBan());
        values.put(NGUONGOC, sanPham.getNguongoc());
        values.put(BAOHANH, sanPham.getBh());
        values.put(SOLUONG, sanPham.getSoLuong());
        values.put(IMAGE, sanPham.getHinhanh());
        if (db.insert(TB_NAME, null, values) > 0) {
            return 1;
        }
        return -1;
    }

    public List<SanPham> getAll() {
        List<SanPham> list = new ArrayList<>();
        String select = " select * from " + TB_NAME;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                SanPham sanPham = new SanPham();
                sanPham.setId(cursor.getString(0));
                sanPham.setName(cursor.getString(1));
                sanPham.setSpinner(cursor.getString(2));
                sanPham.setGiaBan(cursor.getDouble(3));
                sanPham.setNguongoc(cursor.getString(4));
                sanPham.setBh(cursor.getString(5));
                sanPham.setSoLuong(cursor.getInt(6));
                sanPham.setHinhanh(cursor.getBlob(7));
                list.add(sanPham);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public double tinhTong(String name, int soluong) {
        double tinhtong = 0;

        Cursor cursor = null;
        try {
            cursor = db.query(TB_NAME, null, NAME + " = ? ", new String[]{String.valueOf(name)}, null, null, null);
            cursor.moveToFirst();
            SanPham sanPham = new SanPham();
            sanPham.setGiaBan(cursor.getDouble(3));
            Log.d("abcdef", String.valueOf(sanPham.getGiaBan()));
            tinhtong = sanPham.getGiaBan() * soluong;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tinhtong;
    }

    public boolean checkID(String id) {
        Cursor cursor = null;
        try {
            cursor = db.query(TB_NAME,null,ID + " = ? ",new String[]{String.valueOf(id)},null,null,null);
            cursor.moveToFirst();
            int i  = cursor.getCount();
            if (i <= 0) {
                return false;
            }
            return true;
        }catch (Exception e){

        }
        return false;
    }
    public int update(SanPham sanPham){
        ContentValues values = new ContentValues();
        values.put(NAME,sanPham.getName());
        values.put(GIABAN,sanPham.getGiaBan());
        values.put(BAOHANH,sanPham.getBh());
        values.put(SOLUONG,sanPham.getSoLuong());
        return db.update(TB_NAME,values, ID + " = ? ",new String[]{String.valueOf(sanPham.getId())});
    }
    public int delete(SanPham sanPham){
        return db.delete(TB_NAME, ID + " = ? ", new String[]{String.valueOf(sanPham.getId())});

    }

}
