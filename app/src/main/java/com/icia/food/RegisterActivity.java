package com.icia.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

public class RegisterActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment fragmentLocation=new RegisterLocationFragment();
    Fragment fragmentInput=new RegisterInputFragment();
    Fragment fragmentImage=new RegisterImageFragment();

    public  void replaceFragment (String type, Bundle bundle){
        transaction=getSupportFragmentManager().beginTransaction();
        switch (type){
            case "location":
                transaction.replace(R.id.fragment, fragmentLocation).commit();
                break;
            case "input":
                fragmentInput.setArguments(bundle);
                transaction.replace(R.id.fragment, fragmentInput).commit();
                break;
            case "image":
                fragmentImage.setArguments(bundle);
                transaction.replace(R.id.fragment, fragmentImage).commit();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("맛집등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment,fragmentLocation).commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}