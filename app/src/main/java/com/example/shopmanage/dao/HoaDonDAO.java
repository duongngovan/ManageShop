package com.example.shopmanage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.shopmanage.datahelper.DataHelper;
import com.example.shopmanage.model.HoaDon;
import com.example.shopmanage.model.KhachHang;
import com.example.shopmanage.model.SanPham;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAO {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static final String TB_NAME = "hoadon";
    private static final String ID = "id_hoadon";
    private static final String NAME = "name_kh";
    private static final String NAME_SP = "name_sp";
    private static final String SOLUONG = "soluong";
    private static final String DATE = "ngay_hoadon";
    private static final String TONGTIEN = "tong_tien";


    private SQLiteDatabase db;
    private DataHelper dataHelper;
    private static final String TAG = "hoadonDAO";

    public static final String create_tb = " create table " + TB_NAME + " ( " + ID + " text primary key, "
            + NAME + " text not null, " + NAME_SP + " text not null, "
            + SOLUONG + " integer not null, "
            + DATE + " text not null, " + TONGTIEN + " text not null )";

    public HoaDonDAO(Context context) {
        dataHelper = new DataHelper(context);
        db = dataHelper.getWritableDatabase();
    }

    public int add(HoaDon hoaDon) {
        ContentValues values = new ContentValues();
        values.put(ID, hoaDon.getId());
        values.put(NAME, hoaDon.getNamekh());
        values.put(NAME_SP, hoaDon.getNamesp());
        values.put(SOLUONG, hoaDon.getSoluong());
        values.put(DATE, sdf.format(hoaDon.getNgay()));
        values.put(TONGTIEN, hoaDon.getTongTien());
        if (db.insert(TB_NAME, null, values) > 0) {
            return 1;
        }
        return -1;
    }

    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        String select = " select * from " + TB_NAME;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(cursor.getString(0));
                hoaDon.setNamekh(cursor.getString(1));
                hoaDon.setNamesp(cursor.getString(2));
                hoaDon.setSoluong(cursor.getInt(3));
                try {
                    hoaDon.setNgay(sdf.parse(cursor.getString(4)));
                } catch (ParseException e) {
                    e.toString();
                }
                hoaDon.setTongTien(cursor.getDouble(5));
                list.add(hoaDon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int delete(HoaDon hoaDon) {
        return db.delete(TB_NAME, ID + " = ? ", new String[]{String.valueOf(hoaDon.getId())});
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




    public double getDoanhThuTheoNgay() {
        double doanhThu = 0;
        String sql = " select " + " sum(tong_tien) " + " as " + "tongsotien" + " from " + TB_NAME + " where " +DATE + " = date('now') ";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            doanhThu = cursor.getDouble(0);
            cursor.moveToNext();
        }
        cursor.close();
        return doanhThu;
    }

    public double getDoanhThuTheoThang() {
        double doanhThu = 0;
        String sql = " select " + " sum(tong_tien) " + " as " + "tongsotien" + " from " + TB_NAME + " where " +  " strftime('%m',ngay_hoadon ) = strftime('%m','now') ";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            doanhThu = cursor.getDouble(0);
            cursor.moveToNext();
        }
        cursor.close();
        return doanhThu;
    }
    public double getDoanhThuTheoNam(){
        double doanhThu = 0;
        String sql = " select " + " sum(tong_tien) " + " as " + "tongsotien" + " from " + TB_NAME + " where " +  " strftime('%Y',ngay_hoadon ) = strftime('%Y','now') ";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            doanhThu = cursor.getDouble(0);
            cursor.moveToNext();
        }
        cursor.close();
        return doanhThu;
    }


}
