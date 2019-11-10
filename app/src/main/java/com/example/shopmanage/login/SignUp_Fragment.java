package com.example.shopmanage.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopmanage.R;
import com.example.shopmanage.dao.UserDao;
import com.example.shopmanage.model.SignUpUser;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class SignUp_Fragment extends Fragment {
    EditText edtName, edtPhone, edtEmail, edtUser, edtPass, edtPassr;
    CircleImageView circleImageView;
    Button btnBack, btnSave;
    Button btnCamera;
    int REQUEST_CODE_CAMERA = 123;
    int REQUEST_CODE_FOLDER = 456;
    UserDao db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.sign_up,container,false);
        // anh xa
        edtName = view.findViewById(R.id.name);
        edtPhone = view.findViewById(R.id.phone);
        edtEmail = view.findViewById(R.id.email);
        edtUser = view.findViewById(R.id.user);
        edtPass = view.findViewById(R.id.pass);
        edtPassr = view.findViewById(R.id.passr);
        circleImageView = (CircleImageView)view.findViewById(R.id.images);
        btnBack = (Button)view.findViewById(R.id.backbtn);
        btnSave = (Button)view.findViewById(R.id.savebtn);
        btnCamera = (Button)view.findViewById(R.id.action);
        //
        db = new UserDao(getContext());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });



        return view;
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
                Toast.makeText(getContext(),"Successfully",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(getContext(),"Fail",Toast.LENGTH_SHORT).show();
            }
        }
    }



}
