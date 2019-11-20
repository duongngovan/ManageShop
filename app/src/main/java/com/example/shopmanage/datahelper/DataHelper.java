package com.example.shopmanage.datahelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shopmanage.dao.HoaDonChiTietDAO;
import com.example.shopmanage.dao.HoaDonDAO;
import com.example.shopmanage.dao.KhachHangDAO;
import com.example.shopmanage.dao.LoaiDAO;
import com.example.shopmanage.dao.SanPhamDAO;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATA = "data_quanly";

    public DataHelper(Context context) {
        super(context, DATA, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LoaiDAO.create_tb);
        sqLiteDatabase.execSQL(SanPhamDAO.create_tb);
        sqLiteDatabase.execSQL(HoaDonDAO.create_tb);
        sqLiteDatabase.execSQL(HoaDonChiTietDAO.create_tb);
        sqLiteDatabase.execSQL(KhachHangDAO.create_tb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" drop table if exists " + LoaiDAO.TB_NAME);
        sqLiteDatabase.execSQL(" drop table if exists " +SanPhamDAO.TB_NAME);
        sqLiteDatabase.execSQL(" drop table if exists " +HoaDonDAO.TB_NAME);
        sqLiteDatabase.execSQL(" drop table if exists "+HoaDonChiTietDAO.TB_NAME);
        sqLiteDatabase.execSQL(" drop table if exists "+KhachHangDAO.TB_NAME);
        onCreate(sqLiteDatabase);

    }
}
