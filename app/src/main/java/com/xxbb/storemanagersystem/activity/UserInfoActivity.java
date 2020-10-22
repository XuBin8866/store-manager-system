package com.xxbb.storemanagersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xxbb.storemanagersystem.R;
import com.xxbb.storemanagersystem.adapter.UserAdapter;
import com.xxbb.storemanagersystem.db.StoreManagerDBHelper;
import com.xxbb.storemanagersystem.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {
    Button btExit;
    ListView lvUser;
    UserAdapter userAdapter;
    Intent intent;
    StoreManagerDBHelper smb;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        intent=getIntent();
        username=intent.getStringExtra("username");
        initView();
    }
    public void initView(){
        lvUser=findViewById(R.id.user_lv);
        btExit=findViewById(R.id.exit_bt);
        smb=new StoreManagerDBHelper(this);
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserInfoActivity.this,UserListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        User user=new User();
        System.out.println(username);
        user=smb.searchUser(username);
        final List<User> users=new ArrayList<>();
        users.add(user);

        //绑定适配器
        userAdapter=new UserAdapter(this,users);
        lvUser.setAdapter(userAdapter);

        //点击事件
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(UserInfoActivity.this,UserOperatorActivity.class);
                User u=users.get(position);
                if(u.getPower()==0){
                    Toast.makeText(UserInfoActivity.this,"超级管理员用户不允许修改",Toast.LENGTH_SHORT).show();
                }else{
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("user",u);
                    intent.putExtra("user",bundle);
                    startActivity(intent);
                }

            }
        });
    }
}
