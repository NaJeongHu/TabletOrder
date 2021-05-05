package com.example.tabletorder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;

import static com.example.tabletorder.LoginActivity.ID;
import static com.example.tabletorder.LoginActivity.PW;

public class StoreCellectAdapter extends RecyclerView.Adapter<StoreCellectAdapter.MyViewHolder> {

    Context mContext;
    Dialog tDialog;
    public static String tablenum;
    public static String store_name;
    public static final String socket_url = "http://52.79.236.212:5000";
    public static Socket socket_io;

    static {
        try {
            socket_io = IO.socket(socket_url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void sock_on() {
        Log.d("Connecting......","Socketio");
        socket_io.connect();
        JSONObject json = new JSONObject();
        try {
            json.put("id",ID);
            json.put("store_name",store_name);
            json.put("manager",false);
            json.put("table_number",tablenum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("identify","tablenum:"+tablenum);
        socket_io.emit("identify",json);
    }

    private ArrayList<Map<String,Object>> items = new ArrayList<Map<String,Object>>();

    public StoreCellectAdapter(ArrayList<Map<String,Object>> resultList){
        this.items = resultList;
    }

    @NonNull
    @Override
    public StoreCellectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store , parent, false);

        tDialog = new Dialog(parent.getContext());
        tDialog.setContentView(R.layout.popup_tnum);

        final MyViewHolder vHolder = new MyViewHolder(itemView);

        vHolder.item_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView store_name_tv = (TextView) tDialog.findViewById(R.id.table_name);
                ImageView store_img_tv = (ImageView) tDialog.findViewById(R.id.table_img);
                store_name_tv.setText(items.get(vHolder.getAdapterPosition()).get("name").toString());
                store_name = store_name_tv.getText().toString();
                //store_img_tv.setImageResource(items.get(vHolder.getAdapterPosition()).get("img_url").toString());
                Picasso.get()
                        .load(items.get(vHolder.getAdapterPosition()).get("img_url").toString())
                        .into(store_img_tv);

                tDialog.show();

                final Button button = (Button) tDialog.findViewById(R.id.create_table);
                final EditText tnum = (EditText) tDialog.findViewById(R.id.table_num);

                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {    // 번호를 메인메뉴판 위에 띄우고, 메인으로 연결
                        Context context = view.getContext();
                        tablenum = tnum.getText().toString();
                        Intent i = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(i);
                        sock_on();
                    }
                });
            }
        });


//        vHolder.item_common.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.dialog_name);
//                ImageView dialog_img_tv = (ImageView) myDialog.findViewById(R.id.dialog_img);
//                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getKorean());
//                dialog_img_tv.setImageResource(mData.get(vHolder.getAdapterPosition()).getPhoto());
//
//                myDialog.show();
//            }
//        });

        //return new MyViewHolder(itemView);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreCellectAdapter.MyViewHolder holder, int position) {
        Map<String, Object> item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item_store;
        public TextView tvAddress, tvName;
        public ImageView ivImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_store = itemView.findViewById(R.id.item_store);
            tvAddress = itemView.findViewById(R.id.store_address);
            tvName = itemView.findViewById(R.id.store_name);
            ivImage = itemView.findViewById(R.id.store_image);
        }
        public void setItem(Map<String,Object> item){
            tvAddress.setText(item.get("address").toString());
            tvName.setText(item.get("name").toString());
//            Log.d("TESTTEST",item.get("img_url").toString());
            Picasso.get()
                    .load(item.get("img_url").toString())
                    .into(ivImage);
            //ivImage.setImageResource(R.drawable.ic_launcher_background);    // 이미지 url로 받아와야 되는데 할줄 모름..
        }
    }
}
