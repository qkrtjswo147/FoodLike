package com.icia.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

public class FoodInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    FoodVO vo=new FoodVO();
    FoodDAO dao=new FoodDAO();
    ImageView image,keep;
    TextView name,address,tel,description;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        getSupportActionBar().setTitle("음식 정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image=findViewById(R.id.image);
        name=findViewById(R.id.name);
        keep=findViewById(R.id.keep);
        tel=findViewById(R.id.tel);
        address=findViewById(R.id.address);
        description=findViewById(R.id.description);

        Intent intent=getIntent();
        FoodDB helper=new FoodDB(this);

        vo=dao.read(helper, intent.getIntExtra("id",0));
        String sdPath= Environment.getExternalStorageDirectory().getAbsolutePath();
        String strImage=sdPath+"/pictures/"+vo.getImage();
        image.setImageBitmap(BitmapFactory.decodeFile(strImage));
        name.setText(vo.getName());
        if(vo.getKeep()==1){
            keep.setImageResource(R.drawable.ic_keep_on);
        }else{
            keep.setImageResource(R.drawable.ic_keep_off);
        }
        tel.setText(vo.getTel());
        //전화전호를 클릭한경우
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+tel.getText().toString()));
                startActivity(callIntent);
            }
        });
        address.setText(vo.getAddress());
        description.setText(vo.getDescription());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(vo.getKeep()==1) {
            keep.setImageResource(R.drawable.ic_keep_on);
        }else {
            keep.setImageResource(R.drawable.ic_keep_off);
        }

        //즐겨찾기버튼을 클릭한 경우
        AlertDialog.Builder box=new AlertDialog.Builder(this);
        ImageView keep=findViewById(R.id.keep);
        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box.setTitle("메세지");
                if(vo.getKeep()==1){
                    keep.setImageResource(R.drawable.ic_keep_on);
                    box.setMessage("즐겨찾기를 취소하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.updateKeep(helper, 0, vo.get_id());
                            keep.setImageResource(R.drawable.ic_keep_off);
                            vo.setKeep(0);
                        }
                    });
                }else{
                    keep.setImageResource(R.drawable.ic_keep_off);
                    box.setMessage("즐겨찾기에 등록하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.updateKeep(helper, 1, vo.get_id());
                            vo.setKeep(1);
                            keep.setImageResource(R.drawable.ic_keep_on);
                        }
                    });
                }
                box.setNegativeButton("아니오",null);
                box.show();
            }
        });

    }
    /**
     *
     *
     *
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;
        LatLng latLng=new LatLng(vo.getLatitude(), vo.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title(vo.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

    }
}