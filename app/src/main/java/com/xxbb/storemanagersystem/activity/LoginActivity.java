package com.xxbb.storemanagersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xxbb.storemanagersystem.R;
import com.xxbb.storemanagersystem.db.StoreManagerDBHelper;
import com.xxbb.storemanagersystem.entity.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edUsername;
    EditText edPassword;
    CheckBox ckRemember;
    Button btRegister;
    Button btLogin;
    StoreManagerDBHelper smb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        //按钮监听
        btLogin.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    public void initView() {
        edUsername = findViewById(R.id.username_ed);
        edPassword = findViewById(R.id.password_ed);
        ckRemember = findViewById(R.id.remember_ck);
        btRegister = findViewById(R.id.register_bt);
        btLogin = findViewById(R.id.login_bt);
        smb = new StoreManagerDBHelper(this);

    }

    //监听注册和登录按钮
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_bt:
                System.out.println("登录");
                String username = edUsername.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                if ("".equals(username)) {
                    Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    break;
                }
                if ("".equals(password)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    break;
                }
                User u = null;
                u = smb.searchUser(username);
                if (u != null && password.equals(u.getPassword())) {
                    if ("0".equals(String.valueOf(u.getPower()))) {
                        Intent intent = new Intent(LoginActivity.this, UserListActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "超级管理员", Toast.LENGTH_SHORT).show();
                    }
                    if ("1".equals(String.valueOf(u.getPower())) || "2".equals(String.valueOf(u.getPower()))) {
                        Intent intent = new Intent(LoginActivity.this, GoodsListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", u);
                        intent.putExtra("user",bundle);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "欢迎登录", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register_bt:
                System.out.println("注册");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}

