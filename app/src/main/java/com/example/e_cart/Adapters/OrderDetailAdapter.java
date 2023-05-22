package com.example.e_cart.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cart.Model.ModelOrder;
import com.example.e_cart.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderHolder> {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public OrderDetailAdapter(Context context, ArrayList<ModelOrder> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }

    Context context;
    ArrayList<ModelOrder> orderlist;
    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order2,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, @SuppressLint("RecyclerView") int position) {
        ModelOrder modelOrder = orderlist.get(position);

        String id = modelOrder.getProduct_id();
        holder.itemTitle.setText("Product Name :" + modelOrder.getTitle());
        holder.itemqunatity.setText("Quantity:" + modelOrder.getQuantity());
        holder.itemprice.setText("PKR:" + modelOrder.getTotal_price());




    }




    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        TextView itemprice,itemqunatity, itemTitle;
        Button Checkoutbutton;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle=itemView.findViewById(R.id.Title);
            itemprice= itemView.findViewById(R.id.price1);
            itemqunatity = itemView.findViewById(R.id.quantitiy1);

        }
    }
}

