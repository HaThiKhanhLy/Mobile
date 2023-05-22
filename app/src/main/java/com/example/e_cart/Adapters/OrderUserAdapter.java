package com.example.e_cart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cart.Activity.order;
import com.example.e_cart.Model.ModelOrederUser;
import com.example.e_cart.R;
import com.example.e_cart.utils.filter;
import com.example.e_cart.utils.filterorderuser;

import java.util.ArrayList;

public class OrderUserAdapter extends RecyclerView.Adapter<OrderUserAdapter.OrderHolder> {
    Context context;
    ArrayList<ModelOrederUser> myOrderlist ;

    public OrderUserAdapter(Context context, ArrayList<ModelOrederUser> myOrderlist) {
        this.context = context;
        this.myOrderlist = myOrderlist;

    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_user_row,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        ModelOrederUser modelOrederUser = myOrderlist.get(position);
        holder.Order.setText("Order ID # "+modelOrederUser.getOrder_ID());
        holder.shopname.setText("Shop Name :"+modelOrederUser.getShop_name());
        holder.date.setText("Date : "+modelOrederUser.getOrder_Date());
        holder.Total.setText("Total Amount : "+modelOrederUser.getOrder_Total());
        holder.status.setText("Order Status : "+modelOrederUser.getOrder_Status());





    }

    @Override
    public int getItemCount() {
        return myOrderlist.size();
    }



    public class OrderHolder extends RecyclerView.ViewHolder {
        TextView Order,Total,date,shopname, status;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            Order = itemView.findViewById(R.id.OrderNo);
            Total =itemView.findViewById(R.id.TotalAmount);
            date= itemView.findViewById(R.id.date);
            status=itemView.findViewById(R.id.ordeStatus);
            shopname=itemView.findViewById(R.id.shopnmae);
        }
    }
}
