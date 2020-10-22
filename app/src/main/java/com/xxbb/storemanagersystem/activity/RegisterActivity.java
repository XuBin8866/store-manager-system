package com.xxbb.storemanagersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xxbb.storemanagersystem.R;
import com.xxbb.storemanagersystem.db.StoreManagerDBHelper;
import com.xxbb.storemanagersystem.entity.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edUsername;
    EditText edPassword;
    Button btRegister;
    Button btExit;
    StoreManagerDBHelper smb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        //监听按钮
        btExit.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }
    public void initView(){
        edUsername=findViewById(R.id.username_ed);
        edPassword=findViewById(R.id.password_ed);
        btRegister=findViewById(R.id.register_bt);
        btExit=findViewById(R.id.exit_bt);
        smb=new StoreManagerDBHelper(this);
    }
    //监听注册和退出按钮
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.register_bt:
                System.out.println("注册");
                //获取账号信息
                String username=edUsername.getText().toString().trim();
                String password=edPassword.getText().toString().trim();
                User s=new User();
                s.setUsername(username);
                s.setPassword(password);
                s.setPower(0);
                //数据库操作
                smb.insertUser(s);
                //跳转到主页面
                Intent intent1=new Intent(RegisterActivity.this,UserListActivity.class);
                startActivity(intent1);
                break;
            case R.id.exit_bt:
                System.out.println("退出");
                Intent intent2=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
