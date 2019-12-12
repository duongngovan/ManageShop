package com.example.shopmanage.nhanvien;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.shopmanage.R;
import com.example.shopmanage.adapter.RecyclerNhanVien;
import com.example.shopmanage.dao.NhanVienDAO;
import com.example.shopmanage.datahelper.DataHelperEmployee;
import com.example.shopmanage.dungchung.Camera;
import com.example.shopmanage.model.NhanVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanVienActivity extends AppCompatActivity  {

    Dialog dialog;
    CircleImageView circleImageView;
    EditText edID,edName,edNgaySinh,edSDT,edDiaChi,edNgayVaoLam,edLuong;
    Button btnSaved,btnBack;
    FloatingActionButton btnChonAnh;
    NhanVienDAO nhanVienDAO;
    NhanVien nhanVien;
    DataHelperEmployee dataHelperEmployee;
    RecyclerNhanVien recyclerNhanVien;
    RecyclerView recyclerView;
    List<NhanVien> list;


    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Calendar calendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        Toolbar toolbar = findViewById(R.id.nvtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataHelperEmployee = new DataHelperEmployee(getApplicationContext());
        nhanVienDAO = new NhanVienDAO(getApplicationContext());
        anhXa();
        camera();
        setRecyclerView();
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
        ArrayList<NhanVien> filteredList = new ArrayList<>();
        for (NhanVien item : list){
            if (item.getId().toLowerCase().contains(text.toLowerCase()) || item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        recyclerNhanVien.filterList(filteredList);
    }


    private void anhXa(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_nhanvien);
        edID = dialog.findViewById(R.id.idnv);
        edName = dialog.findViewById(R.id.namenv);
        edNgaySinh = dialog.findViewById(R.id.ngaysinhnv);
        edSDT = dialog.findViewById(R.id.sdtnv);
        edDiaChi = dialog.findViewById(R.id.diachinv);
        edNgayVaoLam = dialog.findViewById(R.id.ngayvaolamnv);
        edLuong = dialog.findViewById(R.id.luongnv);
        btnSaved = dialog.findViewById(R.id.savednv);
        btnChonAnh = dialog.findViewById(R.id.btnchonanh);
        btnBack = dialog.findViewById(R.id.backnv);
        circleImageView = dialog.findViewById(R.id.hinhanhsanpham);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_nv);

    }

    private void camera(){
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NhanVienActivity.this);
                builder.setMessage("Vui lòng chọn!");
                builder.setPositiveButton("CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    }
                });
                builder.setNegativeButton("TAI ANH LEN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_CODE_FOLDER);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");


            circleImageView.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                circleImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void saved(View view){
        nhanVienDAO = new NhanVienDAO(getApplicationContext());
        nhanVien = new NhanVien();
        Camera camera = new Camera();
        nhanVien.setId(edID.getText().toString());
        nhanVien.setName(edName.getText().toString());
        nhanVien.setSdt(edSDT.getText().toString());
        nhanVien.setDiaChi(edDiaChi.getText().toString());
        nhanVien.setLuong(Double.parseDouble(edLuong.getText().toString()));
        try {
            nhanVien.setNgaySinh(sdf.parse(edNgaySinh.getText().toString()));
            nhanVien.setNgayVaoLam(sdf.parse(edNgayVaoLam.getText().toString()));
        }catch (ParseException e){
            e.printStackTrace();
        }
        nhanVien.setImage(camera.ImageView_To_Byte(circleImageView));
        nhanVienDAO.add(nhanVien);
        Toast.makeText(getApplicationContext(),"thanh cong",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nhanvien,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.themnhanvien){
            Toast.makeText(getApplicationContext(),"Chon them",Toast.LENGTH_SHORT).show();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void chonNgaySinh(View view){
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                  calendar.set(i,i1,i2);
                  edNgaySinh.setText(sdf.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();

    }
    public void chonNgayLam(View view){
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                edNgayVaoLam.setText(sdf.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();

    }
    private void setRecyclerView(){
        list = new ArrayList<>();
        nhanVienDAO = new NhanVienDAO(getApplicationContext());
        list= nhanVienDAO.getAll();
        setAdapter();

    }
    private void setAdapter(){
        if (recyclerNhanVien == null){
            recyclerNhanVien = new RecyclerNhanVien(list,getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(recyclerNhanVien);
        } else {
            recyclerNhanVien.notifyDataSetChanged();

        }
    }
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


}
