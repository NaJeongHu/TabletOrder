package com.example.tabletorder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.MyViewHolder> {

    Context mContext;
    List<OrderInfo> mData = new ArrayList<OrderInfo>();
    public ViewGroup rvp;

    public ReceiptAdapter(Context mContext, List<OrderInfo> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ReceiptAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_checkbox,parent,false);
        rvp = parent;
        final MyViewHolder vHolder = new MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptAdapter.MyViewHolder holder, int position) {
        String name, price, topping;
        JSONArray json_topping = mData.get(position).getAddi();
        name = mData.get(position).getName();
        price = mData.get(position).getPrice();
        topping = "";
        for(int i=0; i<json_topping.length();++i){
            try {
                if(i!=0)topping+=' ';
                JSONObject jsoni = json_topping.getJSONObject(i);
                topping+=jsoni.getString("name")+" "+jsoni.getString("count");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //holder.tvcontent.setText(mData.get(position).toString());       // mdata에 있는 거 통째로 싩기
        holder.tvcontent.setText(mData.get(position).getName()+ " " + topping + " " + price+"원");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvcontent;
        private CheckBox cbselect;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvcontent = (TextView) itemView.findViewById(R.id.tvContent);
            cbselect = (CheckBox) itemView.findViewById(R.id.checkBox2);
        }
    }
    public void sel_delfunc() {
        for(int i=mData.size() - 1;i>=0;i--){
            CheckBox cbsel = rvp.getChildAt(i).findViewById(R.id.checkBox2);

            if(cbsel.isChecked())
                mData.remove(i);
        }
        //TextView option_c = vg.getChildAt(i).findViewById(R.id.option_count);
    }
}
