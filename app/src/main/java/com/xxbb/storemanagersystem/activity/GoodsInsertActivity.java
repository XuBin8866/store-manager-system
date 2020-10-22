package com.xxbb.storemanagersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xxbb.storemanagersystem.R;
import com.xxbb.storemanagersystem.db.StoreManagerDBHelper;
import com.xxbb.storemanagersystem.entity.Goods;

public class GoodsInsertActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edGoodsName;
    EditText edAmount;
    Button btInsert;
    Button btBack;
    Goods goods;
    StoreManagerDBHelper smb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_add);
        initView();
    }
    public void initView(){
        //初始化控件
        edGoodsName=findViewById(R.id.goods_name_ed);
        edAmount=findViewById(R.id.amount_ed);
        btInsert=findViewById(R.id.insert_bt);
        btBack=findViewById(R.id.exit_bt);
        smb=new StoreManagerDBHelper(this);
        btInsert.setOnClickListener(this);
        btBack.setOnClickListener(this);
        goods=new Goods();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert_bt:
                goods.setGoodsName(edGoodsName.getText().toString().trim());
                goods.setAmount(Integer.parseInt(edAmount.getText().toString().trim()));
                System.out.println(goods);
                smb.insertGoods(goods);
                Toast.makeText(GoodsInsertActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                //跳转
                Intent intent1=new Intent(GoodsInsertActivity.this,GoodsListActivity.class);
                startActivity(intent1);
                break;
            case R.id.exit_bt:
                //跳转
                Intent intent2=new Intent(GoodsInsertActivity.this,GoodsListActivity.class);
                startActivity(intent2);
                break;
        }
    }


}
