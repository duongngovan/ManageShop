package com.example.shopmanage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shopmanage.nguoidung.UserActivity;
import com.example.shopmanage.nhanvien.NhanVienActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2;
    Intent intent1, intent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

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
        }

    }

    private void anhXa() {
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        intent1 = new Intent(MainActivity.this, UserActivity.class);
        intent2 = new Intent(MainActivity.this, NhanVienActivity.class);
    }
}
