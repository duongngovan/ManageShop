package com.example.shopmanage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shopmanage.hang.LoaiActivity;
import com.example.shopmanage.hoadon.HoaDonActivity;

import com.example.shopmanage.khachhang.KhachHangActivity;
import com.example.shopmanage.model.HoaDon;
import com.example.shopmanage.nguoidung.UserActivity;
import com.example.shopmanage.nhanvien.NhanVienActivity;
import com.example.shopmanage.sanpham.SanPhamActivity;
import com.example.shopmanage.thongke.ThongKeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView bgapp, clover;
    private LinearLayout textsplash, texthome, menus;
    private Animation frombottom;
    private CardView btnLoai, btnSanPham, btnKhachHang, btnHoaDon, btnNhanVien, btnThongKe;


    private Intent intent2, intent3, intent4, intent5, intent6, intent7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amtion();

        anhXa();
        btnLoai.setOnClickListener(this);
        btnThongKe.setOnClickListener(this);
        btnNhanVien.setOnClickListener(this);
        btnSanPham.setOnClickListener(this);
        btnHoaDon.setOnClickListener(this);
        btnKhachHang.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnloai:
                startActivity(intent2);
                overridePendingTransition(R.anim.divao,R.anim.dira);
                break;
            case R.id.btnsanpham:
                startActivity(intent3);
                overridePendingTransition(R.anim.divao,R.anim.dira);
                break;
            case R.id.btnkhachhang:
                startActivity(intent4);
                overridePendingTransition(R.anim.divao,R.anim.dira);
                break;
            case R.id.btnhoadon:
                startActivity(intent5);
                overridePendingTransition(R.anim.divao,R.anim.dira);
                break;
            case R.id.btnnhanvien:
                startActivity(intent6);
                overridePendingTransition(R.anim.divao,R.anim.dira);
                break;
            case R.id.btnthongke:
                startActivity(intent7);
                overridePendingTransition(R.anim.divao,R.anim.dira);
                break;

        }

    }

    private void anhXa() {
        btnLoai = findViewById(R.id.btnloai);
        btnSanPham = findViewById(R.id.btnsanpham);
        btnKhachHang = findViewById(R.id.btnkhachhang);
        btnHoaDon = findViewById(R.id.btnhoadon);
        btnNhanVien = findViewById(R.id.btnnhanvien);
        btnThongKe = findViewById(R.id.btnthongke);


        intent2 = new Intent(MainActivity.this, LoaiActivity.class);
        intent3 = new Intent(MainActivity.this, SanPhamActivity.class);
        intent4 = new Intent(MainActivity.this, KhachHangActivity.class);
        intent5 = new Intent(MainActivity.this, HoaDonActivity.class);
        intent6 = new Intent(MainActivity.this, NhanVienActivity.class);
        intent7 = new Intent(MainActivity.this, ThongKeActivity.class);

    }

    private void amtion() {
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);


        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);

        bgapp.animate().translationY(-1900).setDuration(1800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(1800).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(1800).setStartDelay(300);

        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Bạn có muốn thoát không");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                   finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();

    }
}
