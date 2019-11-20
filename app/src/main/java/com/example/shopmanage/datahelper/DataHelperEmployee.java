package com.example.shopmanage.datahelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shopmanage.dao.NhanVienDAO;

public class DataHelperEmployee extends SQLiteOpenHelper {
    public DataHelperEmployee(Context context) {
        super(context, "nhanvien", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(NhanVienDAO.create_tb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" drop table if exists " +NhanVienDAO.TB_NAME);
        onCreate(sqLiteDatabase);
    }
}
