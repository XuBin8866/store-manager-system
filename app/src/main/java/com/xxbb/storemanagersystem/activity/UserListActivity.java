package com.xxbb.storemanagersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.xxbb.storemanagersystem.R;
import com.xxbb.storemanagersystem.adapter.UserAdapter;
import com.xxbb.storemanagersystem.db.StoreManagerDBHelper;
import com.xxbb.storemanagersystem.entity.User;

import java.util.List;

public class UserListActivity extends AppCompatActivity implements View.OnClickListener{
    Button btInsert;
    Button btSelect;
    Button btExit;
    ListView lvUser;
    UserAdapter userAdapter;
    StoreManagerDBHelper smb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initView();
    }

    //初始化控件
    public void initView(){
        btInsert=findViewById(R.id.insert_bt);
        btSelect=findViewById(R.id.select_bt);
        btExit=findViewById(R.id.exit_bt);
        lvUser=findViewById(R.id.user_lv);
        smb=new StoreManagerDBHelper(this);
        btSelect.setOnClickListener(this);
        btInsert.setOnClickListener(this);
        btExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert_bt:
                System.out.println("添加");
                Intent intent=new Intent(UserListActivity.this,UserInsertActivity.class);
                startActivity(intent);
                break;
            case R.id.select_bt:
                System.out.println("查询");
                //加载自定义窗口布局文件
                final AlertDialog dialog=new AlertDialog.Builder(this).create();
                LinearLayout line=(LinearLayout) getLayoutInflater().inflate(R.layout.dialog_user_search,null);
                dialog.setTitle("查询");
                dialog.setView(line);
                dialog.show();
                //初始化控件
                Button btn=line.findViewById(R.id.search_bt_dialog);
                final EditText edUsername=line.findViewById(R.id.username_ed_dialog);
                //点击查询
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username=edUsername.getText().toString().trim();
                        if("".equals(username)){
                            Toast.makeText(UserListActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                        }else{
                            User u=smb.searchUser(username);
                            //判断用户是否存在
                            if(username.equals(u.getUsername())){
                                Intent intent1=new Intent(UserListActivity.this,UserInfoActivity.class);
                                intent1.putExtra("username",username);
                                startActivity(intent1);
                            }else{
                                Toast.makeText(UserListActivity.this,"该用户不存在",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
            case R.id.exit_bt:
                System.out.println("退出");
                Intent intent2=new Intent(UserListActivity.this,LoginActivity.class);
                startActivity(intent2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //从数据库中查询所有学生信息
        User user=new User();
        final List<User> users=smb.getAllUsers();

        //绑定适配器
        userAdapter=new UserAdapter(this,users);
        lvUser.setAdapter(userAdapter);

        //点击事件
        lvUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(UserListActivity.this,UserOperatorActivity.class);
                User u=users.get(position);
                if(u.getPower()==0){
                    Toast.makeText(UserListActivity.this,"超级管理员用户不允许修改",Toast.LENGTH_SHORT).show();
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
