package com.example.shopmanage.hoadon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.shopmanage.R;
import com.example.shopmanage.adapter.RecyclerViewHoaDon;
import com.example.shopmanage.dao.HoaDonDAO;
import com.example.shopmanage.dao.KhachHangDAO;
import com.example.shopmanage.hoadonchitiet.HoaDonChiTietActivity;
import com.example.shopmanage.model.HoaDon;
import com.example.shopmanage.model.KhachHang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HoaDonActivity extends AppCompatActivity {
    Calendar calendar;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    EditText edID,edNgay;

    Dialog dialog;
    Spinner spKhachHang;
    HoaDonDAO hoaDonDAO;
    HoaDon hoaDon = new HoaDon();
    KhachHangDAO khachHangDAO;
    List<KhachHang> listkhachHang = new ArrayList<>();
    List<String> listSp = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    RecyclerView recyclerView;
    RecyclerViewHoaDon recyclerViewHoaDon;
    List<HoaDon> list;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        Toolbar toolbar = findViewById(R.id.toolbar_hd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhXa();
        spinnerKhachHang();
        setRecyclerView();
    }
    private void setRecyclerView(){
        recyclerView = findViewById(R.id.recyclerview_hd);
        list = new ArrayList<>();
        hoaDonDAO = new HoaDonDAO(getApplicationContext());
        list = hoaDonDAO.getAll();
        setAdapter();


    }
    private void setAdapter(){
        if (recyclerViewHoaDon == null){
            recyclerViewHoaDon = new RecyclerViewHoaDon(list,getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewHoaDon);
        }else {
            recyclerViewHoaDon.notifyDataSetChanged();
        }
    }
    public void savedhd(View view){
        hoaDonDAO = new HoaDonDAO(getApplicationContext());
        hoaDon.setId(edID.getText().toString());
        try {
            hoaDon.setNgay(sdf.parse(edNgay.getText().toString()));
        }catch (ParseException e){
            e.printStackTrace();
        }
        hoaDonDAO.add(hoaDon);

    }
    private void spinnerKhachHang() {
        // list này là lấy toàn bộ giá trị của thể loại
        khachHangDAO = new KhachHangDAO(getApplicationContext());
        listkhachHang = khachHangDAO.getAll();

        // dùng for để lấy toàn bộ giá trị mã thể loại
        for (KhachHang spi : listkhachHang) {
            listSp.add(spi.getName());
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listSp);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        }
        spKhachHang.setAdapter(arrayAdapter);

        spKhachHang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                hoaDon.setNamekh(item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void anhXa(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_hoadon);
        edID = dialog.findViewById(R.id.idhoadon);
        spKhachHang = dialog.findViewById(R.id.tenkhhoadon);
        edNgay = dialog.findViewById(R.id.ngayhoadon);
        intent = new Intent(HoaDonActivity.this, HoaDonChiTietActivity.class);
    }
    public void chonNgayHd(View view){
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                edNgay.setText(sdf.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hoadon,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.them_hoadon){
           dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
