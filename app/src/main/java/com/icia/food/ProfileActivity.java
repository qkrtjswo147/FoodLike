package com.icia.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    EditText gender,birthday,name,tel;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CircleImageView person;
    String strImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        gender=findViewById(R.id.profile_gender);
        birthday=findViewById(R.id.profile_birthday);
        name=findViewById(R.id.profile_name);
        tel=findViewById(R.id.profile_phone);
        tel.setText("010-3526-7635");
        name.setText("이순이");
        person=findViewById(R.id.ic_person);

        preferences=getSharedPreferences("profile",AppCompatActivity.MODE_PRIVATE);
        editor=preferences.edit();

        name.setText(preferences.getString("name","무기명"));
        gender.setText(preferences.getString("gender", ""));
        tel.setText(preferences.getString("tel",""));
        birthday.setText(preferences.getString("birthday",""));

        strImage=preferences.getString("image","");
        if(!strImage.equals("")){
            person.setImageBitmap(BitmapFactory.decodeFile(strImage));
        }

        getSupportActionBar().setTitle("프로필 설정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //성별상자 클릭
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] genders={"여자","남자"};
                AlertDialog.Builder box=new AlertDialog.Builder(ProfileActivity.this);
                box.setTitle("성별 선택");
                box.setItems(genders, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gender.setText(genders[which]);
                    }
                });
                box.show();
            }
        });
        //생일 입력 상자를 클릭한 경우
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GregorianCalendar cal=new GregorianCalendar();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog box=new DatePickerDialog(ProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            String strBirth=String.format("%4d-%02d-%02d",year,month+1,dayOfMonth);
                            birthday.setText(strBirth);
                        }
                    },year,month,day);
                box.show();
            }
        });
        //사진선택버튼을 클릭한 경우
        ImageView change=findViewById(R.id.profile_icon_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,ProfileIconActivity.class);
                startActivity(intent);
            }
        });
    }//create

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.submit:
                AlertDialog.Builder box=new AlertDialog.Builder(this);
                box.setTitle("메세지");
                box.setMessage("설정된 내용을 저장하시겠습니까?");
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.putString("name", name.getText().toString());
                        editor.putString("gender", gender.getText().toString());
                        editor.putString("birthday", birthday.getText().toString());
                        editor.putString("tel", tel.getText().toString());
                        editor.commit();
                        finish();
                    }
                });
                box.setNegativeButton("아니오",null);
                box.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        strImage=preferences.getString("image","");
        if(!strImage.equals("")){
            person.setImageBitmap(BitmapFactory.decodeFile(strImage));
        }
    }
}