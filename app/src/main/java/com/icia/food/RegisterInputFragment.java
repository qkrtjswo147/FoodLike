package com.icia.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class RegisterInputFragment extends Fragment {
    FoodVO vo=new FoodVO();
    EditText name,address,tel,description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register_input, container, false);
        vo=(FoodVO)getArguments().getSerializable("vo");

        System.out.println("............."+vo);
        name=view.findViewById(R.id.name);
        address=view.findViewById(R.id.address);
        tel=view.findViewById(R.id.tel);
        description=view.findViewById(R.id.description);

        address.setText(vo.getAddress());
        Button prev=view.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("vo", vo);
                ((RegisterActivity) getActivity()).replaceFragment("location", bundle);
            }
        });
        Button next=view.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vo.setName(name.getText().toString());
                vo.setAddress(address.getText().toString());
                vo.setTel(tel.getText().toString());
                vo.setDescription(description.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("vo", vo);
                ((RegisterActivity) getActivity()).replaceFragment("image", bundle);
            }
        });
        return view;
    }
}