package com.example.shopmanage.nhanvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shopmanage.R;

public class NhanVienActivity extends AppCompatActivity {

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        Toolbar toolbar = findViewById(R.id.nvtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        anhXa();
    }
    private void anhXa(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_nhanvien);
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
}
