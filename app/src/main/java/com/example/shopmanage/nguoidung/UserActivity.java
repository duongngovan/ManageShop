package com.example.shopmanage.nguoidung;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopmanage.R;
import com.example.shopmanage.dao.UserDao;
import com.example.shopmanage.login.LoginActivity;
import com.example.shopmanage.login.SignUp_Fragment;
import com.example.shopmanage.model.SignUpUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    Intent intent;
    RecyclerUser recyclerviewAdapter;
    RecyclerView recycler;
    List<SignUpUser> list;
    UserDao db;
    Dialog dialog;
    EditText edtName,edtPhone,edtEmail,edtUser,edtPass,edtPassr;
    Button btnBack,btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerview();
        anhXa();
    }
    private void anhXa(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_user);
        edtName = dialog.findViewById(R.id.name);
        edtPhone = dialog.findViewById(R.id.phone);
        edtEmail = dialog.findViewById(R.id.email);
        edtUser = dialog.findViewById(R.id.user);
        edtPass = dialog.findViewById(R.id.pass);
        edtPassr = dialog.findViewById(R.id.passr);

        btnBack = (Button)dialog.findViewById(R.id.backbtn);
        btnSave = (Button)dialog.findViewById(R.id.save_btn);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private void signup(){
        String name = edtName.getText().toString();
        String phone = edtPhone.getText().toString();
        String email = edtEmail.getText().toString();
        String user = edtUser.getText().toString();
        String pass = edtPass.getText().toString();
        if (edtName.length() != 0 && edtPhone.length() != 0 && edtEmail.length() != 0 && edtUser.length() != 0
                && edtPass.length() != 0){
            try {
                SignUpUser upUser = new SignUpUser(name.trim()
                        ,phone.trim(),email.trim(),user.trim(),pass.trim());
                db.add(upUser);
                Toast.makeText(getApplicationContext(),"Successfully",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void recyclerview(){
        recycler = (RecyclerView)findViewById(R.id.recyclerview);
        list = new ArrayList<>();
        db = new UserDao(getApplicationContext());
        list= db.getAll();
        setAdapter();
    }
    private void setAdapter(){
        if (recyclerviewAdapter == null){
            recyclerviewAdapter = new RecyclerUser(list,getApplicationContext());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler.setLayoutManager(linearLayoutManager);
            recycler.setAdapter(recyclerviewAdapter);
        } else {
            recyclerviewAdapter.notifyDataSetChanged();

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.them){
            Toast.makeText(getApplicationContext(),"Ban dang cho them",Toast.LENGTH_SHORT).show();
            dialog.show();

        }
        if (item.getItemId() == R.id.doimatkhau){

            Toast.makeText(getApplicationContext(),"Ban dang chon doi mat khau",Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.dangxuat){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bạn có muốn đăng xuất không");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
        return super.onOptionsItemSelected(item);
    }
}
