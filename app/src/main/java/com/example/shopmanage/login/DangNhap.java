package com.example.shopmanage.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmanage.MainActivity;
import com.example.shopmanage.R;
import com.example.shopmanage.dao.UserDAO;
import com.example.shopmanage.model.SignUpUser;
import com.google.android.material.textfield.TextInputEditText;

public class DangNhap extends AppCompatActivity {

    private UserDAO userDao;
    private Intent intent;
    private Dialog dialog;
    //
    private SignUpUser signUpUser;

    //
    private ImageView imageView;
    private Animation smalltobig, btta, btta2;
    private TextView textView, subtitle_header;
    private Button button, btnMk;
    private EditText editText, editText2, edUser, edPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccccc);
        anhXa();
        userDao = new UserDAO(getApplicationContext());
        intent = new Intent(getApplicationContext(), MainActivity.class);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        ///
        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        btta = AnimationUtils.loadAnimation(this, R.anim.btta);
        btta2 = AnimationUtils.loadAnimation(this, R.anim.btta2);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        subtitle_header = findViewById(R.id.subtitle_header);

        // passing animation and start it
        imageView.startAnimation(smalltobig);
        textView.startAnimation(btta);
        subtitle_header.startAnimation(btta);
        button.startAnimation(btta2);
        editText.startAnimation(btta2);
        editText2.startAnimation(btta2);
    }

    private void login() {
        String user = editText.getText().toString();
        String pass = editText2.getText().toString();
        if (userDao.checkUser(user.trim(), pass.trim())) {
            startActivity(intent);
            finish();
        } else {

            Toast.makeText(getApplicationContext(), "Thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void anhXa() {
        //
        button = findViewById(R.id.button);

        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        //

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.quen_mat_khau);
        edUser = dialog.findViewById(R.id.usercu);
        edPass = dialog.findViewById(R.id.passnew);
        btnMk = dialog.findViewById(R.id.kiem_tra);
    }

    public void doimatkhau(View view) {

        dialog.show();
    }

    public void doimatkhaus(View view) {
        userDao = new UserDAO(getApplicationContext());
        signUpUser = new SignUpUser();
        signUpUser.setUser(edUser.getText().toString());
        signUpUser.setPass(edPass.getText().toString());
        userDao.doiMatK(signUpUser);
    }
}
