package com.icia.food;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{
    Context context;
    List<FoodVO> array;
    FoodDAO dao=new FoodDAO();
    FoodDB helper;
    String name;

    public FoodAdapter(Context context, List<FoodVO> array,String name) {
        this.context = context;
        this.array = array;
        helper=new FoodDB(context);
        this.name=name;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_food,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        FoodVO vo=array.get(position);
        holder.name.setText(vo.getName());
        holder.description.setText(vo.getDescription());
        String sdPath= Environment.getExternalStorageDirectory().getAbsolutePath();
        String strImage=sdPath+"/Pictures/"+vo.getImage();
        holder.image.setImageBitmap(BitmapFactory.decodeFile(strImage));

        if(vo.getKeep()==1) {
            holder.keep.setImageResource(R.drawable.ic_keep_on);
        }else {
            holder.keep.setImageResource(R.drawable.ic_keep_off);
        }
        AlertDialog.Builder box=new AlertDialog.Builder(context);//fragment 라서
        holder.keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                box.setTitle("메세지");
                if(vo.getKeep()==1){
                    holder.keep.setImageResource(R.drawable.ic_keep_on);
                    box.setMessage("즐겨찾기를 취소하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.updateKeep(helper, 0, vo.get_id());
                            holder.keep.setImageResource(R.drawable.ic_keep_off);
                            vo.setKeep(0);

                            if(name.equals("keep")){
                                array.remove(vo);
                                notifyDataSetChanged();
                            }
                        }
                    });
                }else{
                    holder.keep.setImageResource(R.drawable.ic_keep_off);
                    box.setMessage("즐겨찾기에 등록하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao.updateKeep(helper, 1, vo.get_id());
                            vo.setKeep(1);
                            holder.keep.setImageResource(R.drawable.ic_keep_on);
                        }
                    });
                }
                box.setNegativeButton("아니오",null);
                box.show();
            }
        });
        //아이템을 클릭한경우
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, FoodInfoActivity.class);
                intent.putExtra("id", vo.get_id());
                if(name.equals("keep")) {
                    ((Activity)context).startActivityForResult(intent,
                            100);
                }else{
                    ((Activity)context).startActivityForResult(intent,
                            200);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, keep;
        TextView name,description;
        CardView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            description=itemView.findViewById(R.id.description);
            keep=itemView.findViewById(R.id.keep);
            item=itemView.findViewById(R.id.item);

        }
    }
}
