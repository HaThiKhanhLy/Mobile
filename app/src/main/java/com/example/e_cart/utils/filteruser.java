package com.example.e_cart.utils;

import android.widget.Filter;

import com.example.e_cart.Adapters.ProductAdapter;
import com.example.e_cart.Adapters.ProductUser;
import com.example.e_cart.Model.ProductModel;

import java.util.ArrayList;

public class filteruser extends Filter {
    public filteruser(ProductUser productUser, ArrayList<ProductModel> filterlist) {
        this.productUser = productUser;
        this.filterlist = filterlist;
    }
    ProductUser productUser;
  public ArrayList<ProductModel> filterlist;

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults filterResults = new FilterResults();
        if(charSequence!=null && charSequence.length()>0){
            charSequence =charSequence.toString().toUpperCase();
            //
            //
            ArrayList<ProductModel> filtermodel = new ArrayList<>();

            for(int i =0 ; i<filterlist.size();i++){
                if(filterlist.get(i).getProductTitle().toUpperCase().contains(charSequence)||
                        filterlist.get(i).getProductCategory().toUpperCase().contains(charSequence)){
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
        productUser.productlist= (ArrayList<ProductModel>) filterResults.values;
        productUser.notifyDataSetChanged();

    }
}
