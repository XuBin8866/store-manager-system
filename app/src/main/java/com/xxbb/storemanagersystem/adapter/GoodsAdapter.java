package com.xxbb.storemanagersystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xxbb.storemanagersystem.R;
import com.xxbb.storemanagersystem.entity.Goods;

import java.util.List;

public class GoodsAdapter extends BaseAdapter {
    List<Goods> goods;
    Context context;

    public GoodsAdapter(Context context, List<Goods> goods) {
        this.context = context;
        this.goods = goods;
    }

    @Override
    public int getCount() {
        return goods.size();
    }

    @Override
    public Object getItem(int position) {
        return goods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.goods_item,parent,false);
        }
        TextView tvId= convertView.findViewById(R.id.id_tv);
        TextView tvgoodsname =  convertView.findViewById(R.id.goods_name_tv);
        TextView tvAmount= convertView.findViewById(R.id.amount_tv);


        //设置信息
        tvId.setText(String.valueOf(goods.get(position).getId()));
        tvgoodsname.setText(goods.get(position).getGoodsName());
        tvAmount.setText(String.valueOf(goods.get(position).getAmount()));

        return convertView;
    }
}
