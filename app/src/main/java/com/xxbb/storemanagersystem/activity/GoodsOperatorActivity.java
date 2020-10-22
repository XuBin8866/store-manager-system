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
import com.xxbb.storemanagersystem.entity.User;


public class GoodsOperatorActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edId;
    EditText edGoodsName;
    EditText edAmount;
    Button btUpdate;
    Button btDelete;
    Intent intent;
    Goods goods;
    StoreManagerDBHelper smb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_operator);
        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("goods");
        goods = (Goods) bundle.getSerializable("goods");
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX------------->"+goods);
        initView();
    }

    public void initView() {
        //初始化控件
        edId = findViewById(R.id.id_ed);
        edGoodsName = findViewById(R.id.goods_name_ed);
        edAmount = findViewById(R.id.amount_ed);
        btUpdate = findViewById(R.id.update_bt);
        btDelete = findViewById(R.id.delete_bt);
        smb = new StoreManagerDBHelper(this);
        btDelete.setOnClickListener(this);
        btUpdate.setOnClickListener(this);

        //设置控件参数
        edId.setText(String.valueOf(goods.getId()));
        edId.setEnabled(false);//去掉点击时编辑框下面横线:
        edId.setFocusable(false);//不可编辑
        edId.setFocusableInTouchMode(false);//不可编辑

        edGoodsName.setText(goods.getGoodsName());
        edAmount.setText(String.valueOf(goods.getAmount()));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_bt:
                goods.setGoodsName(edGoodsName.getText().toString().trim());
                goods.setAmount(Integer.parseInt(edAmount.getText().toString().trim()));
                smb.updateGoodsInfo(goods);
                Toast.makeText(GoodsOperatorActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                //跳转
                Intent intent1 = new Intent(GoodsOperatorActivity.this, GoodsListActivity.class);
                Bundle bundle1=new Bundle();
                User user1=new User(1);
                bundle1.putSerializable("user",user1);
                intent1.putExtra("user",bundle1);
                startActivity(intent1);
                startActivity(intent1);
                break;
            case R.id.delete_bt:
                smb.deleteGoods(goods.getId());
                Toast.makeText(GoodsOperatorActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                //跳转
                Intent intent2 = new Intent(GoodsOperatorActivity.this, GoodsListActivity.class);
                Bundle bundle2=new Bundle();
                User user2=new User(1);
                bundle2.putSerializable("user",user2);
                intent2.putExtra("user",bundle2);
                startActivity(intent2);
                break;
        }
    }

}