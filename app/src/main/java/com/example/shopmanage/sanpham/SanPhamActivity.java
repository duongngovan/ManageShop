package com.example.shopmanage.sanpham;

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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shopmanage.R;
import com.example.shopmanage.adapter.RecyclerSanPham;
import com.example.shopmanage.dao.LoaiDAO;
import com.example.shopmanage.dao.SanPhamDAO;
import com.example.shopmanage.dungchung.Camera;
import com.example.shopmanage.model.Loai;
import com.example.shopmanage.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanPhamActivity extends AppCompatActivity {

    private EditText edId,edName,edBaoHanh,edGiaBan,edSoLuong;
    private Spinner spLoai,spNguonGoc;
    private FloatingActionButton ftbAnh;
    private Dialog dialog;
    private List<String> listNguongoc;
    private ArrayAdapter adapter;
    private SanPham sanPham;
    private SanPhamDAO sanPhamDAO;
    private RecyclerView recyclerView;
    private List<SanPham> listl;
    private RecyclerSanPham recyclerSanPham;

    //
    private LoaiDAO loaiDAO;
    private List<Loai> loai = new ArrayList<>();
    private List<String> listLoai = new ArrayList<>();
    private ArrayAdapter arrayAdapterLoai;
    private int REQUEST_CODE_CAMERA = 123;
    private int REQUEST_CODE_FOLDER = 456;
    private CircleImageView circleImageView;
    private String id,name,baoHanh,giaBan,soLuong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        Toolbar toolbar = findViewById(R.id.sanphamtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sanPham = new SanPham();
        anhXa();
        spinerNguongoc();
        spinnerLoai();
        camera();
        recyclerview();
        setAdapter();
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
        ArrayList<SanPham> filteredList = new ArrayList<>();
        for (SanPham item : listl){
            if (item.getId().toLowerCase().contains(text.toLowerCase()) || item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        recyclerSanPham.filterList(filteredList);
    }
    private boolean check(){
        sanPhamDAO = new SanPhamDAO(getApplicationContext());
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Thêm sản phẩm thất bại !");
        if (id.trim().length() == 0 || name.trim().length() == 0 || baoHanh.trim().length() == 0 || soLuong.trim().length() == 0 || giaBan.trim().length() == 0){
            builder.setMessage("Vui lòng nhập đầy đủ !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        if (sanPhamDAO.checkID(id) ){
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
        if (name.trim().length() >= 25 || name.trim().length() <= 5){
            builder.setMessage("Tên phải từ 5 ký tự trở lên !");
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
                builder.setMessage("Số lượng phải lớn hơn không !");
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
        try {
            int i = Integer.parseInt(giaBan);
            if (i <= 0){
                builder.setMessage("Số lượng phải lớn hơn không !");
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
    public void dimssSanPham(View view){
        dialog.dismiss();
    }
    public void saved(View view){
        id = edId.getText().toString();
        name = edName.getText().toString();
        baoHanh = edBaoHanh.getText().toString();
        soLuong = edSoLuong.getText().toString();
        giaBan = edGiaBan.getText().toString();
        com.example.shopmanage.dungchung.Camera camera = new Camera();
        sanPham.setId(id);
        sanPham.setName(name);
        sanPham.setBh(baoHanh);
        sanPham.setGiaBan(Double.parseDouble(giaBan));
        sanPham.setSoLuong(Integer.parseInt(soLuong));
        sanPham.setHinhanh(camera.ImageView_To_Byte(circleImageView));
        sanPhamDAO = new SanPhamDAO(getApplicationContext());
        if (check()){
            sanPhamDAO.add(sanPham);
            listl.clear();
            listl.addAll(sanPhamDAO.getAll());
            setAdapter();
            Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_SHORT).show();
        }

    }
    private void recyclerview(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_sp);
        listl = new ArrayList<>();
        sanPhamDAO = new SanPhamDAO(getApplicationContext());
        listl = sanPhamDAO.getAll();


    }
    private void setAdapter(){
        if (recyclerSanPham == null) {
            recyclerSanPham = new RecyclerSanPham(getApplicationContext(),listl);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(recyclerSanPham);
        }else {
            recyclerSanPham.notifyDataSetChanged();
        }
    }
    private void spinnerLoai() {
        // list này là lấy toàn bộ giá trị của thể loại
        loaiDAO = new LoaiDAO(getApplicationContext());
        loai = loaiDAO.getAll();

        // dùng for để lấy toàn bộ giá trị mã thể loại
        for (Loai spi : loai) {
            listLoai.add(spi.sp());
            arrayAdapterLoai = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listLoai);
            arrayAdapterLoai.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        }
        spLoai.setAdapter(arrayAdapterLoai);

        spLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                sanPham.setSpinner(item);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void spinerNguongoc() {
        listNguongoc = new ArrayList<>();
        listNguongoc.add("Việt Nam");
        listNguongoc.add("Trung Quốc");

        listNguongoc.add("Khác");
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listNguongoc);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spNguonGoc.setAdapter(adapter);
        spNguonGoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sanPham.setNguongoc((String) spNguonGoc.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void anhXa(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sanpham);
        spNguonGoc = dialog.findViewById(R.id.nguongocsp);
        spLoai = dialog.findViewById(R.id.spinnerloai);
        edId = dialog.findViewById(R.id.idsanpham);
        edName = dialog.findViewById(R.id.namesanpham);
        edBaoHanh = dialog.findViewById(R.id.baohanhsp);
        edGiaBan = dialog.findViewById(R.id.giabansanpham);
        edSoLuong = dialog.findViewById(R.id.soluongsp);
        ftbAnh = dialog.findViewById(R.id.btnchonanh);
        circleImageView = dialog.findViewById(R.id.hinhanhsanpham);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sanpham,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.them_sanpham){
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
    private void camera(){
        ftbAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SanPhamActivity.this);
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
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
