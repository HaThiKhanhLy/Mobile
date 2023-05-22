package com.example.e_cart.utils;

import android.widget.Filter;

import com.example.e_cart.Adapters.ProductUser;
import com.example.e_cart.Adapters.ShopOrderAdpater;
import com.example.e_cart.Model.ModelOrderShop;
import com.example.e_cart.Model.ProductModel;

import java.util.ArrayList;

public class filterorderuser extends Filter {

    public filterorderuser(ShopOrderAdpater shopOrderAdpater, ArrayList<ModelOrderShop> filterlist) {
        this.shopOrderAdpater = shopOrderAdpater;
        this.filterlist = filterlist;
    }

    ShopOrderAdpater shopOrderAdpater;
  public ArrayList<ModelOrderShop> filterlist;

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults filterResults = new FilterResults();
        if(charSequence!=null && charSequence.length()>0){
            charSequence =charSequence.toString().toUpperCase();
            //
            //
            ArrayList<ModelOrderShop> filtermodel = new ArrayList<>();

            for(int i =0 ; i<filterlist.size();i++){
                if(filterlist.get(i).getOrder_Status().toUpperCase().contains(charSequence)){
                    filtermodel.add(filterlist.get(i));
                }
            }
            filterResults.count =filtermodel.size();
            filterResults.values= filtermodel;
        }else{
            filterResults.count =filterlist.size();
            filterResults.values= filterlist;


        }        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        shopOrderAdpater.orderlist= (ArrayList<ModelOrderShop>) filterResults.values;
        shopOrderAdpater.notifyDataSetChanged();

    }
}
