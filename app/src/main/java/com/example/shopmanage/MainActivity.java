package com.example.shopmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopmanage.hang.LoaiActivity;
import com.example.shopmanage.hoadon.HoaDonActivity;
import com.example.shopmanage.hoadonchitiet.HoaDonChiTietActivity;
import com.example.shopmanage.khachhang.KhachHangActivity;
import com.example.shopmanage.model.HoaDon;
import com.example.shopmanage.nguoidung.UserActivity;
import com.example.shopmanage.nhanvien.NhanVienActivity;
import com.example.shopmanage.sanpham.SanPhamActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2,btn3,btn4,btn5,btn6,btn7;
    Intent intent1, intent2,intent3,intent4,intent5,intent6,intent7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                startActivity(intent1);
                break;
            case R.id.button2:
                startActivity(intent2);
                break;
            case R.id.button3:
                startActivity(intent3);
                break;
            case R.id.button4:
                startActivity(intent4);
                break;
            case R.id.button5:
                startActivity(intent5);
                break;
            case R.id.button6:
                startActivity(intent6);
            case R.id.button7:
                startActivity(intent7);

        }

    }

    private void anhXa() {
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);
        btn5 = (Button) findViewById(R.id.button5);
        btn6 = (Button) findViewById(R.id.button6);
        btn7 = (Button) findViewById(R.id.button7);
        intent1 = new Intent(MainActivity.this, UserActivity.class);
        intent2 = new Intent(MainActivity.this, NhanVienActivity.class);
        intent3 = new Intent(MainActivity.this, LoaiActivity.class);
        intent4 = new Intent(MainActivity.this, SanPhamActivity.class);
        intent5 = new Intent(MainActivity.this, HoaDonActivity.class);
        intent6 = new Intent(MainActivity.this, KhachHangActivity.class);
        intent7 = new Intent(MainActivity.this, HoaDonChiTietActivity.class);
    }
}
