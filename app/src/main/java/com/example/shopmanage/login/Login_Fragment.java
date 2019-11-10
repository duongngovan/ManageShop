package com.example.shopmanage.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopmanage.MainActivity;
import com.example.shopmanage.R;
import com.example.shopmanage.dao.UserDao;

public class Login_Fragment extends Fragment {
    EditText edtUser, edtPass;
    Button btnLogin;
    UserDao userDao;
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        edtPass = view.findViewById(R.id.password);
        edtUser = view.findViewById(R.id.username);
        btnLogin = view.findViewById(R.id.login);
        userDao = new UserDao(getContext());
        intent = new Intent(getContext(), MainActivity.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        return view;
    }

    private void login() {


        String user = edtUser.getText().toString();
        String pass = edtPass.getText().toString();

        if (userDao.checkUser(user.trim(), pass.trim())) {
            Toast.makeText(getContext(), "Successfully", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {

            Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
        }

    }


}

