package com.icia.food;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.FileObserver;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class RegisterImageFragment extends Fragment {
    FoodVO vo=new FoodVO();
    FoodDAO dao=new FoodDAO();
    String strImage="";
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_image, container, false);
        vo=(FoodVO)getArguments().getSerializable("vo");
        System.out.println("............."+vo);
        image=view.findViewById(R.id.image);

        ImageView camera=view.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });
        Button prev=view.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("vo", vo);
                ((RegisterActivity) getActivity()).replaceFragment("input", bundle);
            }
        });
        Button complete=view.findViewById(R.id.complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder box=new AlertDialog.Builder(getContext());
                box.setTitle("메세지");
                box.setMessage("맛집을 등록하시겠습니까?");
                box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FoodDB helper=new FoodDB(getContext());
                        dao.insert(helper,vo);
                        getActivity().finish();
                    }
                });
                box.setNegativeButton("아니오",null);
                box.show();
            }
        });
        return view;
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Cursor cursor = getContext().getContentResolver().query(data.getData(), null, null, null, null);
        cursor.moveToFirst();
        strImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        image.setImageBitmap(BitmapFactory.decodeFile(strImage));
        String strFile=strImage.substring(strImage.lastIndexOf("/")+1);
        System.out.println("........."+strFile);
        cursor.close();
        vo.setImage(strFile);
    }
}