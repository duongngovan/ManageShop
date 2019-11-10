package com.example.shopmanage.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.shopmanage.R;

import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Login_Fragment()).commit();
    }
    public void signup(View view){
        Fragment fragment = new SignUp_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).commit();
    }
    public void back(View view){
        Fragment fragment = new Login_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fragment).commit();
    }

}
