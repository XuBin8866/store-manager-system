package com.xxbb.storemanagersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xxbb.storemanagersystem.R;
import com.xxbb.storemanagersystem.db.StoreManagerDBHelper;
import com.xxbb.storemanagersystem.entity.User;

public class UserInsertActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    EditText edUsername;
    EditText edPassword;
    Spinner spPower;
    Button btInsert;
    Button btBack;
    User user;
    StoreManagerDBHelper smb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);
        initView();
    }
    public void initView(){
        //初始化控件
        edUsername=findViewById(R.id.username_ed);
        edPassword=findViewById(R.id.password_ed);
        spPower=findViewById(R.id.power_sp);
        btInsert=findViewById(R.id.insert_bt);
        btBack=findViewById(R.id.exit_bt);
        smb=new StoreManagerDBHelper(this);
        btInsert.setOnClickListener(this);
        btBack.setOnClickListener(this);
        user=new User();
        //获取修改后的下拉列表参数
        spPower.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert_bt:
                user.setUsername(edUsername.getText().toString().trim());
                user.setPassword(edPassword.getText().toString().trim());
                System.out.println(user);
                smb.insertUser(user);
                Toast.makeText(UserInsertActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                //跳转
                    Intent intent1=new Intent(UserInsertActivity.this,UserListActivity.class);
                startActivity(intent1);
                break;
            case R.id.exit_bt:
                //跳转
                Intent intent2=new Intent(UserInsertActivity.this,UserListActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        user.setPower(position+1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
