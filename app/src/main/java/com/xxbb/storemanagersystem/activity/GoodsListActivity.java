package com.xxbb.storemanagersystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.xxbb.storemanagersystem.R;
import com.xxbb.storemanagersystem.adapter.GoodsAdapter;
import com.xxbb.storemanagersystem.adapter.UserAdapter;
import com.xxbb.storemanagersystem.db.StoreManagerDBHelper;
import com.xxbb.storemanagersystem.entity.Goods;
import com.xxbb.storemanagersystem.entity.User;

import java.util.List;

public class GoodsListActivity extends AppCompatActivity implements View.OnClickListener {
    Button btInsert;
    Button btSelect;
    Button btExit;
    Button btImport;
    Button btExport;
    ListView lvGoods;
    GoodsAdapter goodsAdapter;
    StoreManagerDBHelper smb;
    Intent intent;
    int power;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取权限
        Bundle bundle = null;
        intent = getIntent();
        bundle = intent.getBundleExtra("user");
        if(bundle!=null){
            user=(User) bundle.getSerializable("user");
            power = user.getPower();
        }
        if (power == 2) {
            setContentView(R.layout.activity_goods_list_port);
        } else {
            setContentView(R.layout.activity_goods_list);
        }
        initView();
    }

    //初始化控件
    public void initView() {
        btInsert = findViewById(R.id.insert_bt);
        btSelect = findViewById(R.id.select_bt);
        btExit = findViewById(R.id.exit_bt);
        btImport = findViewById(R.id.import_bt);
        btExport = findViewById(R.id.export_bt);
        lvGoods = findViewById(R.id.goods_lv);
        smb = new StoreManagerDBHelper(this);
        //监听按钮
        if (btInsert != null) {
            btInsert.setOnClickListener(this);
        }
        btSelect.setOnClickListener(this);
        btImport.setOnClickListener(this);
        btExport.setOnClickListener(this);
        btExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert_bt:
                System.out.println("添加");
                final Intent intent = new Intent(GoodsListActivity.this, GoodsInsertActivity.class);
                startActivity(intent);
                break;
            case R.id.select_bt:
                System.out.println("查询");
                //加载自定义窗口布局文件
                final AlertDialog dialog = new AlertDialog.Builder(this).create();
                LinearLayout line = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_goods_search, null);
                dialog.setTitle("查询");
                dialog.setView(line);
                dialog.show();
                //初始化控件
                Button btn = line.findViewById(R.id.search_bt_dialog);
                final EditText edGoodsName = line.findViewById(R.id.goods_name_ed_dialog);
                final TextView tvAmount = line.findViewById(R.id.amount_tv_dialog);
                final TextView tvId = line.findViewById(R.id.id_tv_dialog);
                tvAmount.setVisibility(View.INVISIBLE);
                tvId.setVisibility(View.INVISIBLE);
                //点击查询
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String goodsName = edGoodsName.getText().toString().trim();
                        if ("".equals(goodsName)) {
                            Toast.makeText(GoodsListActivity.this, "请输入产品名", Toast.LENGTH_SHORT).show();
                        } else {
                            Goods g = smb.searchGoods(goodsName);
                            //判断用户是否存在
                            if (goodsName.equals(g.getGoodsName())) {
                                tvAmount.setText("数量：" + g.getAmount());
                                tvId.setText("编号：" + g.getId());
                                tvAmount.setVisibility(View.VISIBLE);
                                tvId.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(GoodsListActivity.this, "该产品不存在", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                break;
            case R.id.import_bt:
                System.out.println("入库");
                //加载自定义窗口布局文件
                final AlertDialog dialog2 = new AlertDialog.Builder(this).create();
                LinearLayout line2 = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_goods_import, null);
                dialog2.setTitle("入库");
                dialog2.setView(line2);
                dialog2.show();
                //初始化控件
                Button btn2 = line2.findViewById(R.id.import_bt_dialog);
                final EditText edImport = line2.findViewById(R.id.goods_name_ed_dialog);
                final EditText edAmount = line2.findViewById(R.id.import_num_ed_dialog);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取控件参数，设置Goods值
                        String goodsName = edImport.getText().toString().trim();
                        String amounts = edAmount.getText().toString().trim();
                        int amount = Integer.valueOf(amounts);
                        Goods g2 = new Goods(goodsName, amount);
                        //判断产品是否存在
                        Goods tempGoods = smb.searchGoods(goodsName);
                        if (tempGoods.getGoodsName() != null) {
                            int newAmount = tempGoods.getAmount() + g2.getAmount();
                            g2.setAmount(newAmount);
                            g2.setId(tempGoods.getId());
                            smb.updateGoodsInfo(g2);
                            Toast.makeText(GoodsListActivity.this, "入库成功", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(GoodsListActivity.this, GoodsListActivity.class);
                            Bundle bundle2=new Bundle();
                            bundle2.putSerializable("user",user);
                            intent2.putExtra("user",bundle2);
                            startActivity(intent2);
                        } else {
                            Toast.makeText(GoodsListActivity.this, "该商品不在", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.export_bt:
                System.out.println("出库");
                //加载自定义窗口布局文件
                final AlertDialog dialog3 = new AlertDialog.Builder(this).create();
                LinearLayout line3 = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_goods_export, null);
                dialog3.setTitle("出库");
                dialog3.setView(line3);
                dialog3.show();
                //初始化控件
                Button btn3 = line3.findViewById(R.id.export_bt_dialog);
                final EditText edExport = line3.findViewById(R.id.goods_name_ed_dialog);
                final EditText edExAmount = line3.findViewById(R.id.export_num_ed_dialog);
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取控件参数，设置Goods值
                        String goodsName3 = edExport.getText().toString().trim();
                        String amounts3 = edExAmount.getText().toString().trim();
                        int amount3 = Integer.valueOf(amounts3);
                        Goods g3 = new Goods(goodsName3, amount3);
                        //判断产品是否存在
                        Goods tempGoods3 = smb.searchGoods(goodsName3);
                        if (tempGoods3.getGoodsName() != null) {
                            int newAmount3 = tempGoods3.getAmount() - g3.getAmount();
                            if (newAmount3 >= 0) {
                                g3.setAmount(newAmount3);
                                g3.setId(tempGoods3.getId());
                                smb.updateGoodsInfo(g3);
                                Toast.makeText(GoodsListActivity.this, "出库成功", Toast.LENGTH_SHORT).show();
                                Intent intent3 = new Intent(GoodsListActivity.this, GoodsListActivity.class);
                                Bundle bundle3=new Bundle();
                                bundle3.putSerializable("user",user);
                                intent3.putExtra("user",bundle3);
                                startActivity(intent3);
                            } else {
                                Toast.makeText(GoodsListActivity.this, "出库超出库存，请重新输入", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(GoodsListActivity.this, "该商品不在", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            case R.id.exit_bt:
                System.out.println("退出");
                Intent intent4 = new Intent(GoodsListActivity.this, LoginActivity.class);
                startActivity(intent4);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //从数据库中查询所有学生信息
        Goods g = new Goods();
        final List<Goods> goodsList = smb.getAllGoods();

        //绑定适配器
        goodsAdapter = new GoodsAdapter(this, goodsList);
        lvGoods.setAdapter(goodsAdapter);
        //商品管理员才有修改和删除权限
        if(power==2){
            System.out.println("没有修改产品信息的权限");
        }else{
            //点击事件
            lvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(GoodsListActivity.this, GoodsOperatorActivity.class);
                    Goods g = goodsList.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("goods", g);
                    intent.putExtra("goods", bundle);
                    startActivity(intent);
                }
            });
        }
    }

}
