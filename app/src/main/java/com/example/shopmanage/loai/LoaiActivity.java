package com.example.shopmanage.loai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopmanage.R;
import com.example.shopmanage.adapter.RecyclerViewLoai;
import com.example.shopmanage.dao.LoaiDAO;
import com.example.shopmanage.model.Loai;

import java.util.ArrayList;
import java.util.List;

public class LoaiActivity extends AppCompatActivity {

    private EditText edId,edName,edMota;

    private Dialog dialog;
    private LoaiDAO loaiDAO;
    private Loai loai;
    private RecyclerView recyclerView;
    private RecyclerViewLoai recyclerViewLoai;
    private List<Loai> list;
    private String id,name,mota;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai);
        Toolbar toolbar = findViewById(R.id.loaitoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhXa();
        setAdapter();
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
    public void dimssLoai(View view){
        dialog.dismiss();
    }
    private boolean check(){
        loaiDAO = new LoaiDAO(getApplicationContext());
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm loại sản phẩm thất bại !");
        if (id.trim().length() == 0 || name.trim().length() == 0 || mota.trim().length() == 0){
            builder.setMessage("Vui lòng nhập đầy đủ !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        if (loaiDAO.checkID(id) ){
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
        if (name.trim().length() >= 25 || name.trim().length() <= 1){
            builder.setMessage("Tên phải từ 1 ký tự trở lên !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        if (mota.trim().length() >= 50 || mota.trim().length() <= 5){
            builder.setMessage("Mo tả phải từ 5 ký tự trở lên !");
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
    public void saved(View view){

        id = edId.getText().toString();
        name = edName.getText().toString();
        mota = edMota.getText().toString();

        loaiDAO = new LoaiDAO(getApplicationContext());
        loai = new Loai();

        loai.setId(id);
        loai.setName(name);
        loai.setMoTa(mota);
        if (check()){
            loaiDAO.add(loai);
            list.clear();
            list.addAll(loaiDAO.getAll());
            setRecyclerView();
            Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_SHORT).show();
        }




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
    private void setAdapter(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_loai);
        loaiDAO = new LoaiDAO(getApplicationContext());
        list = new ArrayList<>();
        list = loaiDAO.getAll();


    }
    private void setRecyclerView(){
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
