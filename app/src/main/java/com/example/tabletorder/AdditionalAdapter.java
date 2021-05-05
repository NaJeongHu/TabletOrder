package com.example.tabletorder;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
public class AdditionalAdapter extends RecyclerView.Adapter<AdditionalAdapter.MyViewHolder> {

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);

    private JSONArray addi_data = new JSONArray();
    public AdditionalAdapter(JSONArray result){
        this.addi_data = result;
    }

    public JSONArray additional = new JSONArray();
    public ViewGroup vg;


    @NonNull
    @Override
    public AdditionalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_btn2,parent,false);
        vg = parent;
        final MyViewHolder vHolder = new MyViewHolder(v);

//        vHolder.btn_top.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //에디셔널쪽들 누르면 생기는 이벤트
//                Log.v("TEST","addi 클릭리스너 들어왔어");
//                int position = vHolder.getAdapterPosition();
//                toggleItemSelected(position);
//            }
//        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemView.setSelected(isItemSelected(position));
        try {
            holder.option_n.setText(addi_data.getJSONObject(position).getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            holder.option_p.setText(addi_data.getJSONObject(position).getString("price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return addi_data.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView option_n;
        private TextView option_p;
        private LinearLayout btn_top;
        private TextView option_c;
        private Button option_plus;
        private Button option_minus;
        private LinearLayout option_cnt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_top = (LinearLayout) itemView.findViewById(R.id.btn_topping2);
            option_n = (TextView) itemView.findViewById(R.id.option_name2);
            option_p = (TextView) itemView.findViewById(R.id.option_price2);
            option_c = (TextView) itemView.findViewById(R.id.option_count);
            option_plus = (Button) itemView.findViewById(R.id.option_plus);
            option_minus = (Button) itemView.findViewById(R.id.option_minus);
            option_cnt = (LinearLayout) itemView.findViewById(R.id.option_cnt);
            option_cnt.setVisibility(View.GONE);
            option_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cnt = Integer.parseInt(option_c.getText().toString());
                    cnt+=1;
                    option_c.setText(Integer.toString(cnt));
                }
            });
            option_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cnt = Integer.parseInt(option_c.getText().toString());
                    if(cnt > 0) {
                        cnt -= 1;
                        option_c.setText(Integer.toString(cnt));
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v("TEST","addi 클릭리스너 들어왔어");
                    int position = getAdapterPosition();
                    toggleItemSelected(position);
                    if(mSelectedItems.get(position, false)){
                        option_cnt.setVisibility(View.VISIBLE);
                        notifyDataSetChanged();
                    }
                    else{

                        option_c.setText("0");
                        option_cnt.setVisibility(View.GONE);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
    private void toggleItemSelected(int position) {

        if (mSelectedItems.get(position, false)) {
            mSelectedItems.delete(position);
            notifyItemChanged(position);
        } else {
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

    public void countSelectedItem() {

        for(int i=0;i<addi_data.length();i++){
            if(mSelectedItems.get(i, false)){// 눌려졌다면
                JSONObject topping_obj = new JSONObject();
                try {
                    topping_obj.put("name", addi_data.getJSONObject(i).getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView option_c = vg.getChildAt(i).findViewById(R.id.option_count);
                try {
                    topping_obj.put("count", option_c.getText().toString()); //Integer.parseInt(option_c.getText().toString())
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    topping_obj.put("price", addi_data.getJSONObject(i).getString("price"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if(topping_obj.getInt("count")!=0){
                        additional.put(topping_obj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    additional.add(addi_data.getJSONObject(i).getString("name"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }

        Log.v("TEST","additional 만든 결과는 "+additional);
    }

    public int additional_price(){
        int price=0;
        for(int i=0; i<addi_data.length()-1;++i){
            if(mSelectedItems.get(i,false)){
                try {
                    TextView tv = vg.getChildAt(i).findViewById(R.id.option_count);
                    int cnt = Integer.parseInt(tv.getText().toString());
                    price+=Integer.parseInt(addi_data.getJSONObject(i).getString("price"))* cnt;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return price;
    }
}
