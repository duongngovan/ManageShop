package com.example.shopmanage.manhinhcho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.shopmanage.R;
import com.example.shopmanage.login.DangKy;
import com.example.shopmanage.login.DangNhap;

public class ManHinhCho extends AppCompatActivity {
    private ImageView bgapp, clover;
    private LinearLayout textsplash, texthome, menus;
    private Animation frombottom;
    private Intent intent1,intent2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_cho);

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
        intent1 = new Intent(ManHinhCho.this, DangNhap.class);
        intent2 = new Intent(ManHinhCho.this, DangKy.class);

    }
    public void btn1(View view){
        startActivity(intent1);
        finish();
    }
    public void btn2(View view){
        startActivity(intent2);

    }

}
