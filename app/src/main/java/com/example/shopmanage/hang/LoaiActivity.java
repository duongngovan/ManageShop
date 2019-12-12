package com.example.shopmanage.hang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shopmanage.R;
import com.example.shopmanage.adapter.RecyclerViewLoai;
import com.example.shopmanage.dao.LoaiDAO;
import com.example.shopmanage.model.Loai;
import com.example.shopmanage.model.NhanVien;
import com.example.shopmanage.nhanvien.NhanVienActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoaiActivity extends AppCompatActivity {

    EditText edId,edName,edMota;
    Button btnNew;
    Dialog dialog;
    LoaiDAO loaiDAO;
    Loai loai;
    RecyclerView recyclerView;
    RecyclerViewLoai recyclerViewLoai;
    List<Loai> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai);
        Toolbar toolbar = findViewById(R.id.loaitoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhXa();
        setRecyclerview();

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
        ArrayList<Loai> filteredList = new ArrayList<>();
        for (Loai item : list){
            if (item.getId().toLowerCase().contains(text.toLowerCase()) || item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        recyclerViewLoai.filterList(filteredList);
    }
    private void anhXa(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_loai);
        edId = dialog.findViewById(R.id.idloai);
        edName = dialog.findViewById(R.id.nameloai);
        edMota = dialog.findViewById(R.id.motaloai);



    }
    public void saved(View view){
        loaiDAO = new LoaiDAO(getApplicationContext());
        loai = new Loai();

        loai.setId(edId.getText().toString());
        loai.setName(edName.getText().toString());
        loai.setMoTa(edMota.getText().toString());

        loaiDAO.add(loai);
        setRecyclerview();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loai,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.them_loai){
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void setRecyclerview(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_loai);
        loaiDAO = new LoaiDAO(getApplicationContext());
        list = new ArrayList<>();
        list = loaiDAO.getAll();
        setAdapter();

    }
    private void setAdapter(){
        if (recyclerViewLoai == null){
            recyclerViewLoai = new RecyclerViewLoai(getApplicationContext(),list);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerViewLoai);
        } else {
            recyclerViewLoai.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
