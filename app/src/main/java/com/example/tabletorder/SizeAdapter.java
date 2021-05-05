package com.example.tabletorder;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.MyViewHolder> {

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);

    private JSONArray size_data = new JSONArray();
    public SizeAdapter(JSONArray result){
        this.size_data = result;
    }

    public String size = "null";
    public String size_price;


    @NonNull
    @Override
    public SizeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_btn,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        //size = "null";
        vHolder.btn_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사이즈쪽 리사이클러 누르면 생기는 이벤트
                Log.v("TEST","addi 클릭리스너 들어왔어");
                int position = vHolder.getAdapterPosition();
                try {
                    Log.v("TEST","사이즈에 트라이에 드러옴" +size);
                    size = size_data.getJSONObject(position).getString("name");
                    size_price = size_data.getJSONObject(position).getString("price");
                    Log.v("TEST","사이즈에 트라이에 드러옴" +size);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                toggleItemSelected(position);
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setSelected(isItemSelected(position));
        try {
            holder.option_n.setText(size_data.getJSONObject(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.option_p.setText(size_data.getJSONObject(position).getString("price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return size_data.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView option_n;
        private TextView option_p;
        private LinearLayout btn_lin;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_lin = (LinearLayout) itemView.findViewById(R.id.btn_topping);
            option_n = (TextView) itemView.findViewById(R.id.option_name);
            option_p = (TextView) itemView.findViewById(R.id.option_price);
        }
    }

    private void toggleItemSelected(int position) {

        if (mSelectedItems.get(position, false) == true) {
            //mSelectedItems.delete(position);
            //notifyItemChanged(position);
        } else {
            clearSelectedItem();
            mSelectedItems.put(position, true);
            notifyItemChanged(position);
        }
    }

    private boolean isItemSelected(int position) {
        return mSelectedItems.get(position, false);
    }

    public void clearSelectedItem() {
        int position;

        for (int i = 0; i < mSelectedItems.size(); i++) {
            position = mSelectedItems.keyAt(i);
            mSelectedItems.put(position, false);
            notifyItemChanged(position);
        }

        mSelectedItems.clear();
    }

}
