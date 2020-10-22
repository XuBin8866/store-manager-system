package com.xxbb.storemanagersystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.xxbb.storemanagersystem.entity.Goods;
import com.xxbb.storemanagersystem.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 用户注册登录/学生信息/成绩信息的数据库操作
 */
public class StoreManagerDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "storemanager.db3";
    private static final int VERSION = 1;
    //数据库字段名
    private static final String KY_USERNAME = "username";
    private static final String KY_PASSWORD = "password";
    private static final String KY_POWER = "power";//权限：商品管理员/出入库员

    private static final String KY_ID = "id";//商品编号
    private static final String KY_GOODSNAME = "goods_name";
    private static final String KY_AMOUNT = "amount";//商品数量
    //数据库表名
    private static final String TABLE_USER = "user";
    private static final String TABLE_GOODS = "goods";
    SQLiteDatabase db;

    public StoreManagerDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表
        final String createUser = "create table " + TABLE_USER + "(" + KY_USERNAME + " text primary key,"
                + KY_PASSWORD + " text not null,"
                + KY_POWER + " text not null);";
        System.out.println(createUser);
        db.execSQL(createUser);
        //创建成绩表
        final String createGoods = "create table " + TABLE_GOODS + "(" + KY_ID + " integer primary key autoincrement,"
                + KY_GOODSNAME + " text,"
                + KY_AMOUNT + " integer);";
        System.out.println(createGoods);
        db.execSQL(createGoods);
    }

    //获取用户
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User u = null;
        Cursor cursor = db.query(TABLE_USER, new String[]{KY_USERNAME, KY_PASSWORD, KY_POWER}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            u = new User();
            u.setUsername(cursor.getString(0));
            u.setPassword(cursor.getString(1));
            u.setPower(cursor.getInt(2));
            users.add(u);
        }
        System.out.println("StoreManagerDBHelper.getAllUser.size->>>" + users.size());
        return users;
    }

    //获取用户
    public List<Goods> getAllGoods() {
        List<Goods> goods = new ArrayList<>();
        Goods g = null;
        Cursor cursor = db.query(TABLE_GOODS, new String[]{KY_ID, KY_GOODSNAME, KY_AMOUNT}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            g = new Goods();
            g.setId(cursor.getInt(0));
            g.setGoodsName(cursor.getString(1));
            g.setAmount(cursor.getInt(2));
            goods.add(g);
        }
        System.out.println("StoreManagerDBHelper.getAllGoods.size->>>" + goods.size());
        return goods;
    }

    //添加用户
    public void insertUser(User user) {
        System.out.println("开始执行添加用户方法");
        ContentValues values = new ContentValues();
        values.put(KY_USERNAME, user.getUsername());
        values.put(KY_PASSWORD, user.getPassword());
        values.put(KY_POWER, user.getPower());
        db.insert(TABLE_USER, null, values);
        System.out.println("添加成功！！！");
    }

    //添加产品
    public void insertGoods(Goods goods) {
        System.out.println("开始执行添加产品方法");
        ContentValues values = new ContentValues();
        values.put(KY_GOODSNAME, goods.getGoodsName());
        values.put(KY_AMOUNT, goods.getAmount());
        db.insert(TABLE_GOODS, null, values);
        System.out.println("添加成功！！！");
    }

    //删除用户
    public void deleteUser(String username) {
        System.out.println("使用删除用户方法");
        db.delete(TABLE_USER, KY_USERNAME + "=?", new String[]{username});
        System.out.println("删除成功！！！");
    }

    //删除产品
    public void deleteGoods(int id) {
        System.out.println("使用删除产品方法");
        db.delete(TABLE_GOODS, KY_ID + "=?", new String[]{String.valueOf(id)});
        System.out.println("删除成功！！！");
    }

    //更新用户信息
    public void updateUserInfo(User user) {
        System.out.println("开始执行更新用户方法");
        ContentValues values = new ContentValues();
        values.put(KY_PASSWORD, user.getPassword());
        values.put(KY_POWER, user.getPower());
        db.update(TABLE_USER, values, KY_USERNAME + "=?", new String[]{user.getUsername()});
        System.out.println("更新用户成功！！！");
    }

    //更新产品信息
    public void updateGoodsInfo(Goods goods) {
        System.out.println("开始执行更新产品方法");
        ContentValues values = new ContentValues();
        values.put(KY_GOODSNAME, goods.getGoodsName());
        values.put(KY_AMOUNT, goods.getAmount());
        db.update(TABLE_GOODS, values, KY_ID + "=?", new String[]{String.valueOf(goods.getId())});
        System.out.println("更新用户成功！！！");
    }

    //通过用户名查询用户信息
    public User searchUser(String username) {
        Cursor cursor = db.query(TABLE_USER, new String[]{KY_USERNAME, KY_PASSWORD, KY_POWER}, KY_USERNAME + "=?", new String[]{username}, null, null, null);
        User u = new User();
        if (cursor.moveToFirst()) {
            u.setUsername(cursor.getString(0));
            u.setPassword(cursor.getString(1));
            u.setPower(cursor.getInt(2));
        }
        return u;
    }

    //通过产品名查询产品信息
    public Goods searchGoods(String goodsName) {
        Cursor cursor = db.query(TABLE_GOODS, new String[]{KY_ID, KY_GOODSNAME, KY_AMOUNT}, KY_GOODSNAME + "=?", new String[]{goodsName}, null, null, null);
        Goods g = new Goods();
        if (cursor.moveToFirst()) {
            g.setId(cursor.getInt(0));
            g.setGoodsName(cursor.getString(1));
            g.setAmount(cursor.getInt(2));
        }
        return g;
    }

    //升级方法，删除旧表增加新表
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOODS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
        onCreate(db);
    }


}
