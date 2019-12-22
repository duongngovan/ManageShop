package com.example.shopmanage.hoadon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

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
    private Calendar calendar;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private EditText edID,edNgay,edSoLuong;

    private TextView txtTong;
    private String name;

    private Dialog dialog;
    private Spinner spKhachHang,spSanPham;
    private HoaDonDAO hoaDonDAO;
    private HoaDon hoaDon = new HoaDon();
    private KhachHangDAO khachHangDAO;
    private List<KhachHang> listkhachHang = new ArrayList<>();
    private List<String> listSp = new ArrayList<String>();

    ArrayAdapter arrayAdapter;
    RecyclerView recyclerView;
    RecyclerViewHoaDon recyclerViewHoaDon;
    List<HoaDon> list;


    //
    SanPhamDAO sanPhamDAO;
    List<SanPham> listSanPham = new ArrayList<>();
    List<String> listNameId = new ArrayList<>();
    private String id,ngay,soLuong;

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
    private boolean check(){
        hoaDonDAO = new HoaDonDAO(getApplicationContext());
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm hóa đơn thất bại !");
        if (id.trim().length() == 0 || ngay.trim().length() == 0 || soLuong.trim().length() == 0){
            builder.setMessage("Vui lòng nhập đầy đủ !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        if (hoaDonDAO.checkID(id) ){
            builder.setMessage("ID đã tồn tại !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        if ( id.trim().length() >= 15 || id.trim().length() < 3){
            builder.setMessage("ID phải từ 3 ký tự trở lên !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        try {
            int i = Integer.parseInt(soLuong);
            if (i <= 0){
                builder.setMessage("Số lượng phải lớn hơn 0 !");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                return false;
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return true;
    }
    public void dimssLoaiHoaDon(View view){
        dialog.dismiss();
    }
    public void savedhd(View view){
        id = edID.getText().toString();
        ngay = edNgay.getText().toString();
        soLuong = edSoLuong.getText().toString();
        hoaDonDAO = new HoaDonDAO(getApplicationContext());
        hoaDon.setId(id);
        try {
            hoaDon.setNgay(sdf.parse(ngay));
        }catch (ParseException e){
            e.printStackTrace();
        }
        hoaDon.setSoluong(Integer.parseInt(soLuong));
        if (check()){
            hoaDonDAO.add(hoaDon);
            list.clear();
            list.addAll(hoaDonDAO.getAll());
            setAdapter();
            Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_SHORT).show();
        }


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
        edSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                sanPhamDAO = new SanPhamDAO(getApplicationContext());
                try {
                    double a = sanPhamDAO.tinhTong(name.trim(),Integer.parseInt(edSoLuong.getText().toString()));
                    txtTong.setText(String.valueOf(a));
                    hoaDon.setTongTien(sanPhamDAO.tinhTong(name.trim(),Integer.parseInt(edSoLuong.getText().toString())));

                }catch (NumberFormatException e){
                    e.printStackTrace();
                }


            }
        });

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
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
