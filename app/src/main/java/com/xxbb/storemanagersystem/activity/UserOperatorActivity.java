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

public class UserOperatorActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    EditText edUsername;
    EditText edPassword;
    Spinner spPower;
    Button btUpdate;
    Button btDelete;
    Intent intent;
    User user;
    StoreManagerDBHelper smb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_operator);
        intent=getIntent();
        Bundle bundle=intent.getBundleExtra("user");
        user= (User) bundle.getSerializable("user");
        initView();
    }
    public void initView(){
        //初始化控件
        edUsername=findViewById(R.id.username_ed);
        edPassword=findViewById(R.id.password_ed);
        spPower=findViewById(R.id.power_sp);
        btUpdate=findViewById(R.id.update_bt);
        btDelete=findViewById(R.id.delete_bt);
        smb=new StoreManagerDBHelper(this);
        btDelete.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        //设置控件参数
        edUsername.setText(user.getUsername());
        edUsername.setEnabled(false);//去掉点击时编辑框下面横线:
        edUsername.setFocusable(false);//不可编辑
        edUsername.setFocusableInTouchMode(false);//不可编辑

        edPassword.setText(user.getPassword());

        if(user.getPower()==1){
            spPower.setSelection(0);
        }
        if(user.getPower()==2){
            spPower.setSelection(1);
        }
        //获取修改后的下拉列表参数
       spPower.setOnItemSelectedListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.update_bt:
                user.setPassword(edPassword.getText().toString().trim());
                smb.updateUserInfo(user);
                Toast.makeText(UserOperatorActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                //跳转
                Intent intent1=new Intent(UserOperatorActivity.this,UserListActivity.class);
                startActivity(intent1);
                break;
            case R.id.delete_bt:
                smb.deleteUser(user.getUsername());
                Toast.makeText(UserOperatorActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                //跳转
                Intent intent2=new Intent(UserOperatorActivity.this,UserListActivity.class);
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
