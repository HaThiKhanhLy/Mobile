package com.example.e_cart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_cart.Activity.Catageoryproduct;
import com.example.e_cart.Model.CatagoryModel;
import com.example.e_cart.R;

import java.util.ArrayList;

public class cata extends RecyclerView.Adapter<cata.viewholder> {
    Context context;
    ArrayList<CatagoryModel>list;

    public cata(Context context, ArrayList<CatagoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.catageory_shown,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        CatagoryModel catagoryModel = list.get(position);
     holder.textView.setText(catagoryModel.getName());
     try {
         Glide.with(context)
                 .load(catagoryModel.getImage())
                 .placeholder(R.drawable.holderitem)
                 .into(holder.imageView);
     }catch (Exception e){}
     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent intent =  new Intent(context, Catageoryproduct.class);
             intent.putExtra("name",catagoryModel.getName());
             context.startActivity(intent);
         }
     });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text);
            imageView=itemView.findViewById(R.id.image);
        }

    }
}
