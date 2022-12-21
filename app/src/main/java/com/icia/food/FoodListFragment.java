package com.icia.food;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodListFragment extends Fragment {
    SQLiteDatabase db;
    SQLiteOpenHelper helper;
    RecyclerView list;
    int type=1;
    FoodAdapter adapter;
    ArrayList<FoodVO> array=new ArrayList<>();
    FoodDAO dao=new FoodDAO();
    StaggeredGridLayoutManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_food_list, container, false);

        array=dao.list(new FoodDB(getContext()));

        System.out.println("목록........."+array.size());

        list=view.findViewById(R.id.list_food);

        manager=new StaggeredGridLayoutManager(type, StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(manager);//위에 하나씩 출력 하겠다는 뜻
        adapter=new FoodAdapter(getContext(), array,"list");
        list.setAdapter(adapter);

        //타입 변경버튼을 클릭한 경우
        ImageView listType=view.findViewById(R.id.list_type);
        listType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==1){
                    type=2;
                    listType.setImageResource(R.drawable.ic_list2);
                }else{
                    type=1;
                    listType.setImageResource(R.drawable.ic_list);
                }
                manager=new StaggeredGridLayoutManager(type, StaggeredGridLayoutManager.VERTICAL);
                list.setLayoutManager(manager);
            }
        });
        return view;
    }
}