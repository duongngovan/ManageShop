package com.example.shopmanage.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.shopmanage.MainActivity;
import com.example.shopmanage.R;
import com.example.shopmanage.dao.UserDAO;
import com.example.shopmanage.manhinhcho.ManHinhCho;
import com.example.shopmanage.model.SignUpUser;

public class DangKy extends AppCompatActivity {
    private EditText edtName, edtEmail, edtUser, edtPass;
    private Button  btnSave;
    private UserDAO db;
    private LinearLayout linearLayout;
    private String name,email,user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky);
        linearLayout = findViewById(R.id.linearlayout);
        linearLayout.animate().translationY(-1600).setDuration(500).setStartDelay(300);
        overridePendingTransition(R.anim.divao,R.anim.dira);

        anhXa();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }
    private void anhXa(){
        edtName = findViewById(R.id.name);
        edtEmail = findViewById(R.id.email);
        edtUser = findViewById(R.id.user);
        edtPass = findViewById(R.id.pass);

        btnSave = (Button)findViewById(R.id.button10);

    }
    private boolean check(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tạo tài khoản thất bại !");
        if (name.trim().length() == 0 || email.trim().length()==0 || user.trim().length() == 0 || pass.trim().length() == 0){
            builder.setMessage("Vui lòng nhập đầy đủ !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        if (name.trim().length() >= 20 || name.trim().length() <= 10 ){
            builder.setMessage("Tên phải từ 10 ký tự trở lên !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }

        if (!email.trim().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") ){
            builder.setMessage("Email bạn nhập không đúng !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        if (user.trim().length() >= 20 || user.trim().length() < 5 ){
            builder.setMessage("Tài khoản phải từ 5 ký tự trở lên !");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
            return false;
        }
        if (pass.trim().length() >= 20 || pass.trim().length() < 5 ){
            builder.setMessage("Mật khẩu phải từ 5 ký tự trở lên !");
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
    private void signup(){
        db = new UserDAO(getApplicationContext());
         name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        user = edtUser.getText().toString();
         pass = edtPass.getText().toString();
        if ( check()
                ){
                SignUpUser upUser = new SignUpUser(name.trim()
                        ,email.trim(),user.trim(),pass.trim());
                db.add(upUser);
                Toast.makeText(getApplicationContext(),"Thành Công",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, ManHinhCho.class));
        }
        else {
            Toast.makeText(getApplicationContext(),"Thất Bại",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
