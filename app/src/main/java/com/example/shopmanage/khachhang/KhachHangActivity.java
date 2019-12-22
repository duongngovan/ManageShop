package com.example.shopmanage.khachhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shopmanage.R;
import com.example.shopmanage.adapter.RecyclerViewKhachHang;
import com.example.shopmanage.dao.KhachHangDAO;
import com.example.shopmanage.model.KhachHang;

import java.util.ArrayList;
import java.util.List;

public class KhachHangActivity extends AppCompatActivity {

    private EditText edtName,edSdt,edDiaChi;
    private Spinner spGioiTinh;
    private Dialog dialog;
    private KhachHangDAO khachHangDAO;
    private KhachHang khachHang = new KhachHang();
    private List<KhachHang> list;
    private List<String> listSp;
    private ArrayAdapter arrayAdapter;

    private RecyclerView recyclerView;
    private RecyclerViewKhachHang recyclerViewKhachHang;
    private String name,sdt,diachi;

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
    private boolean check(){
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm loại sản phẩm thất bại !");
        if (name.trim().length() == 0 || sdt.trim().length()==0 || diachi.trim().length() == 0 ){
            builder.setMessage("Vui lòng nhập đầy đủ !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        if (name.trim().length() >= 30 || name.trim().length() < 7){
            builder.setMessage("Tên phải từ 7 ký tự trở lên !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }

        if (diachi.trim().length() >= 50 || diachi.trim().length() <= 5){
            builder.setMessage("Tên phải từ 5 ký tự trở lên !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        return true;
    }
    public void dimssKhachHang(View view){
        dialog.dismiss();
    }
    public void savedKh(View view){
        name = edtName.getText().toString();
        diachi = edDiaChi.getText().toString();
        sdt = edSdt.getText().toString();
        khachHangDAO = new KhachHangDAO(getApplicationContext());

        khachHang.setName(name);
        khachHang.setDiaChi(diachi);
        khachHang.setSdt(sdt);
        if (check()){
            khachHangDAO.add(khachHang);
            list.clear();
            list.addAll(khachHangDAO.getAll());
            setAdapter();
            Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_SHORT).show();
        }


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
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
