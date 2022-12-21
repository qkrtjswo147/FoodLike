package com.icia.food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    RelativeLayout mainDrawer;
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    Cursor cursor;

    //프래그먼트 생성
    Fragment foodListFragment=new FoodListFragment();
    Fragment foodMapFragment=new FoodMapFragment();
    Fragment foodKeepFragment=new FoodKeepFragment();

    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout=findViewById(R.id.drawerLayout);
        mainDrawer=findViewById(R.id.main_drawer);

        transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content, foodListFragment).commit();

        //데이터베이스 생성
        helper=new FoodDB(this);
        db=helper.getWritableDatabase();

        getSupportActionBar().setTitle("맛집리스트");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView=findViewById(R.id.drawer_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction= getSupportFragmentManager().beginTransaction();//commit 하기 전에 begin 해주어야함
                switch (item.getItemId()){
                    case R.id.nav_profile:
                        Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_list://맛집리스트
                        transaction.replace(R.id.main_content, foodListFragment).commit();
                        getSupportActionBar().setTitle("맛집리스트");
                        break;
                    case R.id.nav_map://지도리스트
                        transaction.replace(R.id.main_content, foodMapFragment).commit();
                        getSupportActionBar().setTitle("지도리스트");
                        break;
                    case R.id.nav_keep://즐겨찾기
                        transaction.replace(R.id.main_content, foodKeepFragment).commit();
                        getSupportActionBar().setTitle("즐겨찾기");
                        break;
                    case R.id.nav_register://맛집등록
                        intent=new Intent(MainActivity.this,RegisterActivity.class);
                        startActivityForResult(intent,200);
                        break;
                }
                drawerLayout.closeDrawer(mainDrawer);
                return true;
            }
        });
        onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(mainDrawer)){
                    drawerLayout.closeDrawer(mainDrawer);
                }else{
                    drawerLayout.openDrawer(mainDrawer);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TextView strName=findViewById(R.id.profile_name);
        SharedPreferences preferences=getSharedPreferences("profile",AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        strName.setText(preferences.getString("name","무기명"));
        String strImage=preferences.getString("image","");

        if(!strImage.equals("")){
            CircleImageView icon=findViewById(R.id.profile_icon);
            icon.setImageBitmap(BitmapFactory.decodeFile(strImage));
        }
    }

    public void mClick(View v){
        Intent intent=null;
        switch (v.getId()){
            case R.id.profile_icon:
                intent=new Intent(this,ProfileIconActivity.class);
                break;
            case R.id.profile_name:
                intent=new Intent(this,ProfileActivity.class);
                break;
        }
        //drawerLayout.closeDrawer(mainDrawer);
        startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                foodKeepFragment=new FoodKeepFragment();
                transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content, foodKeepFragment).commit();
                break;
            case 200:
                System.out.println("FoodList............");
                foodListFragment=new FoodListFragment();
                transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_content, foodListFragment).commit();
                break;
        }
    }
}