package com.xxbb.storemanagersystem.entity;

import java.io.Serializable;

public class Goods implements Serializable {
    private int id;
    private String goodsName;
    private int amount;

    public Goods() {
    }

    public Goods(int id, String goodsName, int amount) {
        this.id = id;
        this.goodsName = goodsName;
        this.amount = amount;
    }

    public Goods(String goodsName, int amount) {
        this.goodsName = goodsName;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
