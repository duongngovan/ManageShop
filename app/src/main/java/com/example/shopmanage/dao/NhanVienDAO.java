package com.example.shopmanage.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shopmanage.datahelper.DataHelperEmployee;
import com.example.shopmanage.model.NhanVien;
import com.example.shopmanage.model.SignUpUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    private SQLiteDatabase db;
    private DataHelperEmployee dataHelperEmployee;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static final String TB_NAME = "table_employee";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DATE = "ngaysinh";
    private static final String SDT = "sdt";
    private static final String DIACHI = "diachi";
    private static final String NGAY_VL = "ngay_vao_lam";
    private static final String LUONG = "luong";
    private static final String IMAGE = "image";

    public NhanVienDAO(Context context) {
        dataHelperEmployee = new DataHelperEmployee(context);
        db = dataHelperEmployee.getWritableDatabase();
    }

    public static final String create_tb = " create table " + TB_NAME + " ( " + ID + " text primary key, "
            + NAME + " text not null, " + DATE + " text not null, " + SDT + " text not null, " + DIACHI + " text not null, " +
            NGAY_VL + " text not null, " + LUONG + " real not null, " + IMAGE + " blob )";

    public int add(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put(ID, nhanVien.getId());
        values.put(NAME, nhanVien.getName());
        values.put(DATE, sdf.format(nhanVien.getNgaySinh()));
        values.put(SDT, nhanVien.getSdt());
        values.put(DIACHI, nhanVien.getDiaChi());
        values.put(NGAY_VL, sdf.format(nhanVien.getNgayVaoLam()));
        values.put(LUONG, nhanVien.getLuong());
        values.put(IMAGE, nhanVien.getImage());
        if (db.insert(TB_NAME, null, values) > 0) {
            return 1;
        }

        db.close();
        return -1;
    }

    public List<NhanVien> getAll() {
        List<NhanVien> list = new ArrayList<>();
        String select = " select * from " + TB_NAME;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(cursor.getString(0));
                nhanVien.setName(cursor.getString(1));
                try {
                    nhanVien.setNgaySinh(sdf.parse(cursor.getString(2)));
                    nhanVien.setNgayVaoLam(sdf.parse(cursor.getString(5)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                nhanVien.setSdt(cursor.getString(3));
                nhanVien.setDiaChi(cursor.getString(4));
                nhanVien.setLuong(cursor.getDouble(6));
                nhanVien.setImage(cursor.getBlob(7));
                list.add(nhanVien);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return list;
    }

    public int updateHandler(NhanVien nhanVien) {

        ContentValues values = new ContentValues();
        values.put(NAME, nhanVien.getName());
        values.put(SDT, nhanVien.getSdt());
        values.put(DIACHI, nhanVien.getDiaChi());
        values.put(LUONG, nhanVien.getLuong());
        return db.update(TB_NAME, values, ID + " = ? ", new String[]{String.valueOf(nhanVien.getId())});
    }

    public int delete(NhanVien nhanVien) {
        return db.delete(TB_NAME, ID + " = ? ", new String[]{String.valueOf(nhanVien.getId())});
    }

}
