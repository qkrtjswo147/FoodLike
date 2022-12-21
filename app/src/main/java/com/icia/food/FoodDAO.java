package com.icia.food;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FoodDAO {
    SQLiteDatabase db;
    //맛집등록
    public void insert(FoodDB helper, FoodVO vo){
        db=helper.getWritableDatabase();
        String sql="insert into tbl_food(name,address,tel,latitude,longitude,image,description,keep) values(";
        sql+= "'"+vo.getName()+"',";
        sql+= "'"+vo.getAddress()+"',";
        sql+= "'"+vo.getTel()+"',";
        sql+=vo.getLatitude()+",";
        sql+=vo.getLongitude()+",";
        sql+="'"+vo.getImage()+"',";
        sql+= "'"+vo.getDescription()+"',0)";
        db.execSQL(sql);
    }
    //음식점 정보
    @SuppressLint("Range")
    public FoodVO read(FoodDB helper, int id){
        db=helper.getWritableDatabase();
        FoodVO vo=new FoodVO();
        String sql="select * from tbl_food where _id="+id;
        Cursor cursor=db.rawQuery(sql, null);
        if(cursor.moveToNext()){
            vo.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            vo.setName(cursor.getString(cursor.getColumnIndex("name")));
            vo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            vo.setTel(cursor.getString(cursor.getColumnIndex("tel")));
            vo.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            vo.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            vo.setImage(cursor.getString(cursor.getColumnIndex("image")));
            vo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            vo.setKeep(cursor.getInt(cursor.getColumnIndex("keep")));
        }
        return  vo;
    }
    //즐겨찾기 변경
    public void updateKeep(FoodDB helper, int keep, int id){
        db=helper.getWritableDatabase();
        String sql="update tbl_food set keep="+keep+" where _id="+id;
        db.execSQL(sql);
    }

    //즐겨찾기 목록
    @SuppressLint("Range")
    public ArrayList<FoodVO> keepList(FoodDB helper){
        ArrayList<FoodVO> array=new ArrayList<>();
        db=helper.getWritableDatabase();

        String sql="select * from tbl_food where keep=1 order by _id desc";
        Cursor cursor=db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            FoodVO vo=new FoodVO();
            vo.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            vo.setName(cursor.getString(cursor.getColumnIndex("name")));
            vo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            vo.setTel(cursor.getString(cursor.getColumnIndex("tel")));
            vo.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            vo.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            vo.setImage(cursor.getString(cursor.getColumnIndex("image")));
            vo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            vo.setKeep(cursor.getInt(cursor.getColumnIndex("keep")));

            array.add(vo);
        }
        return array;
    }
    //음식목록
    @SuppressLint("Range")
    public ArrayList<FoodVO> list(FoodDB helper){
        ArrayList<FoodVO> array=new ArrayList<>();
        db=helper.getWritableDatabase();

        String sql="select * from tbl_food order by _id desc";
        Cursor cursor=db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            FoodVO vo=new FoodVO();
            vo.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
            vo.setName(cursor.getString(cursor.getColumnIndex("name")));
            vo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            vo.setTel(cursor.getString(cursor.getColumnIndex("tel")));
            vo.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            vo.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            vo.setImage(cursor.getString(cursor.getColumnIndex("image")));
            vo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            vo.setKeep(cursor.getInt(cursor.getColumnIndex("keep")));

            array.add(vo);
        }
        return array;
    }
}
