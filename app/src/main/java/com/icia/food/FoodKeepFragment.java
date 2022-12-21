package com.icia.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class FoodKeepFragment extends Fragment {
    FoodDAO dao=new FoodDAO();
    ArrayList<FoodVO> array=new ArrayList<>();
    FoodAdapter adapter;
    RecyclerView list;
    StaggeredGridLayoutManager manager;
    int type=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food_list, container, false);

        //데이터생성
        FoodDB helper=new FoodDB(getContext());
        array=dao.keepList(helper);
        System.out.println("데이터갯수..."+array.size());
        adapter=new FoodAdapter(getContext(),array,"keep");

        list=view.findViewById(R.id.list_food);
        list.setAdapter(adapter);

        manager=new StaggeredGridLayoutManager(type, StaggeredGridLayoutManager.VERTICAL);
        list.setLayoutManager(manager);//위에 하나씩 출력 하겠다는 뜻

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