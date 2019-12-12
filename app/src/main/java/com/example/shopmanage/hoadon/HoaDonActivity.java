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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.shopmanage.R;
import com.example.shopmanage.adapter.RecyclerViewHoaDon;
import com.example.shopmanage.dao.HoaDonDAO;
import com.example.shopmanage.dao.KhachHangDAO;

import com.example.shopmanage.dao.SanPhamDAO;
import com.example.shopmanage.model.HoaDon;
import com.example.shopmanage.model.KhachHang;
import com.example.shopmanage.model.NhanVien;
import com.example.shopmanage.model.SanPham;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HoaDonActivity extends AppCompatActivity {
    Calendar calendar;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    EditText edID,edNgay,edSoLuong;

    TextView txtTong;
    String name;

    Dialog dialog;
    Spinner spKhachHang,spSanPham;
    HoaDonDAO hoaDonDAO;
    HoaDon hoaDon = new HoaDon();
    KhachHangDAO khachHangDAO;
    List<KhachHang> listkhachHang = new ArrayList<>();
    List<String> listSp = new ArrayList<String>();

    ArrayAdapter arrayAdapter;
    RecyclerView recyclerView;
    RecyclerViewHoaDon recyclerViewHoaDon;
    List<HoaDon> list;


    //
    SanPhamDAO sanPhamDAO;
    List<SanPham> listSanPham = new ArrayList<>();
    List<String> listNameId = new ArrayList<>();


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
        spSanPham();
        EditText edtSearch = findViewById(R.id.editext);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });



    }
    private void filter(String text){
        ArrayList<HoaDon> filteredList = new ArrayList<>();
        for (HoaDon item : list){
            if (item.getId().toLowerCase().contains(text.toLowerCase()) || item.getNamekh().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        recyclerViewHoaDon.filterList(filteredList);
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
        hoaDon.setSoluong(Integer.parseInt(edSoLuong.getText().toString()));
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
    private void spSanPham(){
        sanPhamDAO = new SanPhamDAO(getApplicationContext());
        listSanPham = sanPhamDAO.getAll();
        for (SanPham sp : listSanPham){
            listNameId.add(sp.getName());
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listNameId);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        }
        spSanPham.setAdapter(arrayAdapter);

        spSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                hoaDon.setNamesp(item);
                name = item;

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
        spSanPham = dialog.findViewById(R.id.tensphoandon);
        edSoLuong = dialog.findViewById(R.id.soluonghoadon);
        txtTong = dialog.findViewById(R.id.tonghd);

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
        sanPhamDAO = new SanPhamDAO(getApplicationContext());
        txtTong.setText("Tong :  "+sanPhamDAO.tinhTong(name.trim(),Integer.parseInt(edSoLuong.getText().toString())));
        hoaDon.setTongTien(sanPhamDAO.tinhTong(name.trim(),Integer.parseInt(edSoLuong.getText().toString())));
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
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
