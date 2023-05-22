package com.example.e_cart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cart.Activity.Order_Detail_Shop;
import com.example.e_cart.Model.ModelOrderShop;
import com.example.e_cart.Model.ModelOrederUser;
import com.example.e_cart.R;
import com.example.e_cart.utils.filter;
import com.example.e_cart.utils.filterorderuser;

import java.util.ArrayList;

public class ShopOrderAdpater extends RecyclerView.Adapter<ShopOrderAdpater.shopordeholder> implements Filterable {
    public ShopOrderAdpater(Context context, ArrayList<ModelOrderShop> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
        this.filterlist=orderlist;
    }

    Context context;
   public ArrayList<ModelOrderShop> orderlist,filterlist;
    private filterorderuser filterproduct;

    @NonNull
    @Override
    public shopordeholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ordershop,parent,false);

        return new shopordeholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull shopordeholder holder, int position) {
        ModelOrderShop modelOrderShop = orderlist.get(position);
        holder.Order.setText("Order ID # "+modelOrderShop.getOrder_ID());
        holder.shopname.setText("Shop Name :"+modelOrderShop.getShop_name());
        holder.date.setText("Date : "+modelOrderShop.getOrder_Date());
        holder.Total.setText("Total Amount : "+modelOrderShop.getOrder_Total());
        holder.status.setText("Order Status : "+modelOrderShop.getOrder_Status());
        String ordrtid=modelOrderShop.getOrder_ID();
        String orderBy = modelOrderShop.getOrder_BY();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , Order_Detail_Shop.class);
                intent.putExtra("orderid",ordrtid);
                intent.putExtra("orderby", orderBy);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }
    @Override
    public Filter getFilter() {
        if(filterproduct==null){
          filterproduct   =  new filterorderuser(ShopOrderAdpater.this,filterlist);

        }
        return filterproduct;
    }
    public class shopordeholder  extends RecyclerView.ViewHolder{
        TextView Order,Total,date,shopname, status;
        public shopordeholder(@NonNull View itemView) {
            super(itemView);
            Order = itemView.findViewById(R.id.OrderNo);
            Total =itemView.findViewById(R.id.TotalAmount);
            date= itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.ordeStatus);
            shopname=itemView.findViewById(R.id.shopnmae);
        }
    }
}
