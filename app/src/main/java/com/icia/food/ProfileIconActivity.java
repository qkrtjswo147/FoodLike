package com.icia.food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileIconActivity extends AppCompatActivity {
    String strImage;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CircleImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_icon);
        icon=findViewById(R.id.profile_icon);

        getSupportActionBar().setTitle("프로필 이미지 변경");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        preferences=getSharedPreferences("profile",AppCompatActivity.MODE_PRIVATE);
        editor=preferences.edit();

        strImage=preferences.getString("image","");
        if(!strImage.equals("")){
            icon.setImageBitmap(BitmapFactory.decodeFile(strImage));
        }
    }

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
                if(strImage.equals("")){
                    Toast.makeText(this,"변경할 이미지가 없습니다.",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder box=new AlertDialog.Builder(this);
                    box.setTitle("메세지");
                    box.setMessage("변경하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preferences=getSharedPreferences("profile",AppCompatActivity.MODE_PRIVATE);
                            editor=preferences.edit();
                            editor.putString("image",strImage);
                            editor.commit();
                            finish();
                        }
                    });
                    box.setNegativeButton("아니오",null);
                    box.show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void mClick(View v){
        switch (v.getId()){
            case R.id.album:
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100: //앨범
                Cursor cursor=getContentResolver().query(data.getData(),
                        null, null, null, null);
                cursor.moveToFirst();
                strImage=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                System.out.println("이미지경로........."+strImage);
                icon.setImageBitmap(BitmapFactory.decodeFile(strImage));
                break;
        }
    }
}