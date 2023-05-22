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

public class OrderApdater extends RecyclerView.Adapter<OrderApdater.OrderHolder> {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public OrderApdater(TextView subtotal, Context context, ArrayList<ModelOrder> orderlist) {
        this.subtotal = subtotal;
        this.context = context;
        this.orderlist = orderlist;
    }

    TextView subtotal ;
    Context context;
    ArrayList<ModelOrder> orderlist;
    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.order_row,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, @SuppressLint("RecyclerView") int position) {
        ModelOrder modelOrder = orderlist.get(position);
        String id = modelOrder.getProduct_id();
        holder.itemTitle.setText("Product Name :"+modelOrder.getTitle());
        holder.itemqunatity.setText("Quantity:"+modelOrder.getQuantity());
        holder.itemprice.setText(modelOrder.getTotal_price());

        // the total amount


     // This is the click on the remove button
        holder.itemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeorder(id);
            }

        });
        double sum = 0.0;
        for (int i = 0; i < orderlist.size(); i++) {
            modelOrder = orderlist.get(i);
             sum = sum + Double.parseDouble(modelOrder.getTotal_price());
            subtotal.setText("Total : "+sum);
        }
    }
 // This is the method for Removing Cart items
    private void removeorder( String id) {

        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference.child(firebaseAuth.getUid()).child("User Order").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context,"Your data is removed",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();


            }

        });





    }



    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        TextView itemprice,itemqunatity, itemTitle,itemRemove;
        Button Checkoutbutton;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle=itemView.findViewById(R.id.Title);
            itemprice= itemView.findViewById(R.id.price1);
            itemRemove =itemView.findViewById(R.id.remove);
            itemqunatity = itemView.findViewById(R.id.quantitiy1);

        }
    }
}
