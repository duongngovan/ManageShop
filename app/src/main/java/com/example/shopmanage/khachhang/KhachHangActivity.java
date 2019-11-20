package com.example.shopmanage.khachhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.shopmanage.R;
import com.example.shopmanage.adapter.RecyclerViewKhachHang;
import com.example.shopmanage.dao.KhachHangDAO;
import com.example.shopmanage.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangActivity extends AppCompatActivity {

    EditText edtName,edSdt,edDiaChi;
    Spinner spGioiTinh;
    Dialog dialog;
    KhachHangDAO khachHangDAO;
    KhachHang khachHang = new KhachHang();
    List<KhachHang> list;
    List<String> listSp;
    ArrayAdapter arrayAdapter;

    RecyclerView recyclerView;
    RecyclerViewKhachHang recyclerViewKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        Toolbar toolbar = findViewById(R.id.toolbar_kh);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        anhXa();
        spinerNguongoc();
        setRecyclerView();
    }
    private void setRecyclerView(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_kh);
        list = new ArrayList<>();
        khachHangDAO = new KhachHangDAO(getApplicationContext());
        list = khachHangDAO.getAll();
        setAdapter();
    }
    private void setAdapter(){
        if (recyclerViewKhachHang == null) {
            recyclerViewKhachHang = new RecyclerViewKhachHang(list,getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewKhachHang);
        } else {
            recyclerViewKhachHang.notifyDataSetChanged();
        }
    }
    public void savedKh(View view){
        khachHangDAO = new KhachHangDAO(getApplicationContext());


        khachHang.setName(edtName.getText().toString());
        khachHang.setDiaChi(edDiaChi.getText().toString());
        khachHang.setSdt(edSdt.getText().toString());

        khachHangDAO.add(khachHang);


    }
    private void spinerNguongoc() {
        listSp = new ArrayList<>();
        listSp.add("Nam");
        listSp.add("Nữ");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listSp);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spGioiTinh.setAdapter(arrayAdapter);
        spGioiTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                khachHang.setGioiTinh((String) spGioiTinh.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void anhXa(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_khachhang);
        edtName = dialog.findViewById(R.id.namekh);
        edDiaChi = dialog.findViewById(R.id.diachikh);
        edSdt = dialog.findViewById(R.id.sdtkh);
        spGioiTinh = dialog.findViewById(R.id.spinnerkh);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_khackhang,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.them_khackhang){
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }
}
