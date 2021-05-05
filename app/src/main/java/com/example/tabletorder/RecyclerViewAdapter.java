package com.example.tabletorder;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tabletorder.LoginActivity.ID;
import static com.example.tabletorder.MainActivity.receipt;
import static com.example.tabletorder.StoreCellectAdapter.socket_io;
import static com.example.tabletorder.StoreCellectAdapter.store_name;
import static com.example.tabletorder.StoreCellectAdapter.tablenum;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Common> mData;
    Dialog myDialog, orderList;
    TextView cellect_s, cellect_a;
    Button cansul, getitem, keep, order, sel_del, all_del;
    static private List<OrderInfo> lstOrder = new ArrayList<>();
    List addition;
    String sizee;

    private RecyclerView sizerecyclerview, addirecyclerview, receiptrecyclerview;

    public void socket_order(){
        JSONObject json = new JSONObject();
        try {
            json.put("id",ID);
            json.put("store_name",store_name);
            json.put("table_number",tablenum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket_io.emit("order",json);
    }

    public RecyclerViewAdapter(Context mContext, List<Common> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v, vv;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_common,parent,false);
        //vv = LayoutInflater.from(mContext).inflate(R.layout.popup_common,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        // Dialog ini

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.popup_common);
        cellect_s = (TextView) myDialog.findViewById(R.id.cellect_size);
        cellect_a = (TextView) myDialog.findViewById(R.id.cellect_additional);
        cansul = (Button) myDialog.findViewById(R.id.cancel_item);
        getitem = (Button) myDialog.findViewById(R.id.put_item);
//        sizerecyclerview = (RecyclerView) v.findViewById(R.id.size_recyclerview);
//        RecyclerViewAdapter recyclerViewAdapter1 = new RecyclerViewAdapter(mData.get(vHolder.getAdapterPosition()).getSize());
//        sizerecyclerview.setLayoutManager(new LinearLayoutManager());
//        sizerecyclerview.setAdapter(recyclerViewAdapter1);

        orderList = receipt;
        keep = (Button) orderList.findViewById(R.id.keep_going);
        order = (Button) orderList.findViewById(R.id.last_order);
        sel_del = (Button) orderList.findViewById(R.id.del_cel);
        all_del = (Button) orderList.findViewById(R.id.del_all);

        vHolder.item_common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizerecyclerview = (RecyclerView) myDialog.findViewById(R.id.size_recyclerview);
                if(mData.get(vHolder.getAdapterPosition()).getSize()==null || mData.get(vHolder.getAdapterPosition()).getSize().length() == 0){
                    sizerecyclerview.setVisibility(View.GONE);
                    cellect_s.setVisibility(View.GONE);
                }
                else{
                    cellect_s.setVisibility(View.VISIBLE);
                    SizeAdapter sizeAdapter = new SizeAdapter(mData.get(vHolder.getAdapterPosition()).getSize());
                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    sizerecyclerview.setLayoutManager(llm);
                    sizerecyclerview.setAdapter(sizeAdapter);
                    sizerecyclerview.setVisibility(View.VISIBLE);
                }

                addirecyclerview = (RecyclerView) myDialog.findViewById(R.id.addi_recyclerview);
                if(mData.get(vHolder.getAdapterPosition()).getAdd()==null || mData.get(vHolder.getAdapterPosition()).getAdd().length() == 0){
                    addirecyclerview.setVisibility(View.GONE);
                    cellect_a.setVisibility(View.GONE);
                }
                else{
                    cellect_a.setVisibility(View.VISIBLE);
                    AdditionalAdapter additionalAdapter = new AdditionalAdapter(mData.get(vHolder.getAdapterPosition()).getAdd());
                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    addirecyclerview.setLayoutManager(llm);
                    addirecyclerview.setAdapter(additionalAdapter);
                    addirecyclerview.setVisibility(View.VISIBLE);
                }

                final TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.dialog_name);
                ImageView dialog_img_tv = (ImageView) myDialog.findViewById(R.id.dialog_img);
                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());
                Picasso.get()
                        .load(mData.get(vHolder.getAdapterPosition()).getImg_url().toString())
                        .into(dialog_img_tv);
                //dialog_img_tv.setImageResource(mData.get(vHolder.getAdapterPosition()).getImg_url());   // picasso

                myDialog.show();

                cansul.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                        // myDialog 끄기
                    }
                });
                getitem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {// 주문 메뉴명, 사이즈, 옵션들 LIST로 주문표 리사이클러뷰로 전달해야댐
                        final SizeAdapter sizeAdapter = (SizeAdapter) sizerecyclerview.getAdapter();
                        String aaa = sizeAdapter.size;
                        Log.v("TEST","aaa에 저장된 size 값은"+aaa);
                        if(sizeAdapter.size == null || sizeAdapter.size == "null"){
                            Log.v("TEST","사이즈 누르는 부분 이상해");
                            Log.v("TEST","사이즈가 뭐냐면" + sizeAdapter.size);
                            Toast. makeText(mContext, "사이즈 눌러라", Toast. LENGTH_SHORT);
                        }
                        else{
                            int Price = Integer.parseInt(sizeAdapter.size_price);
                            receiptrecyclerview = (RecyclerView) orderList.findViewById(R.id.order_recycler);
                            final ReceiptAdapter receiptAdapter = new ReceiptAdapter(mContext,lstOrder);
                            LinearLayoutManager llm = new LinearLayoutManager(mContext);
                            llm.setOrientation(LinearLayoutManager.VERTICAL);
                            receiptrecyclerview.setLayoutManager(llm);
                            receiptrecyclerview.setAdapter(receiptAdapter);
                            AdditionalAdapter adapter = (AdditionalAdapter) addirecyclerview.getAdapter();
                            if(addirecyclerview.getVisibility()==View.VISIBLE){
                                AdditionalAdapter adapter3 = (AdditionalAdapter) addirecyclerview.getAdapter();
                                adapter3.countSelectedItem();
                                Price+=adapter.additional_price();
                                Log.d("TESTADD",adapter.additional.toString());
                                lstOrder.add(new OrderInfo(mData.get(vHolder.getAdapterPosition()).getName(),Integer.toString(Price),sizeAdapter.size,adapter.additional));
                            }
                            else{
                                JSONArray temp = new JSONArray();
                                lstOrder.add(new OrderInfo(mData.get(vHolder.getAdapterPosition()).getName(),Integer.toString(Price),sizeAdapter.size,temp));
                            }
                            orderList.show();
                            //lstOrder.add(new OrderInfo(dialog_name_tv.toString(),"price","size","addi"));// 이런식으로 주문표상 리사이클러에 자료 줘야댐

                            sel_del.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {// 선택항목들을 리사이클러뷰 list에서 삭제 후 리사이클러뷰 다시 가동, 잘 안되면 새로고침 방법 찾아야댐
                                    orderList.dismiss();
                                    ReceiptAdapter adapter5 = (ReceiptAdapter) receiptrecyclerview.getAdapter();
                                    adapter5.sel_delfunc();
                                    //if 리사이클러뷰에 자료가 없으면 else도 같이 선언 해줘야댐
                                    orderList.show();// 이렇게 껐다키면 새로고침 될 거 같아서 일단 이렇게 구현해 놓음
                                }
                            });
                            all_del.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {// 전체 항목을 리사이클러뷰 list에서 삭제제
                                    orderList.dismiss();
                                    for(int i=lstOrder.size() - 1;i>=0;i--){
                                        lstOrder.remove(i);
                                    }
                                    //if 리사이클러뷰에 자료가 없으면 else도 같이 선언 해줘야댐
                                    orderList.show();
                                }
                            });

                            keep.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    orderList.dismiss();
                                    myDialog.dismiss();
                                    // 주문표 그대로 두고 다이얼로그만 2개 다 종료
                                }
                            });
                            order.setOnClickListener(new View.OnClickListener() {// 소켓통신 필요, 주문표 비워야댐
                                @Override
                                public void onClick(View view) {
                                    orderList.dismiss();
                                    myDialog.dismiss();
                                    JSONObject json = new JSONObject();
                                    JSONArray orderarr= new JSONArray();
                                    ReceiptAdapter adapter5 = (ReceiptAdapter) receiptrecyclerview.getAdapter();
                                    for(int i=0; i<adapter5.getItemCount();++i){
                                        JSONObject orderobj = new JSONObject();
                                        try{
                                            OrderInfo data = adapter5.mData.get(i);
                                            JSONObject topping = new JSONObject();
                                            orderobj.put("name",data.getName());
                                            orderobj.put("price",data.getPrice());
                                            orderobj.put("count",1);
                                            topping.put("size",data.getSize());
                                            topping.put("additional",data.getAddi());
                                            orderobj.put("topping",topping);
                                            orderarr.put(orderobj);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    try {
                                        json.put("id",ID);
                                        json.put("store_name",store_name);
                                        json.put("table_number",tablenum);
                                        json.put("order_lists",orderarr.toString());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    OrderApi service = OrderApi.retrofit.create(OrderApi.class);
                                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json.toString());
                                    service.insert_order(body).enqueue(new Callback<Void>() {
                                        @Override
                                        public void onResponse(Call<Void> call, Response<Void> response) {
                                            if(response.isSuccessful()){
                                                Handler mHandler = new Handler(Looper.getMainLooper());
                                                mHandler.postDelayed(new Runnable() {
                                                                         @Override
                                                                         public void run() {
                                                                             Toast.makeText(mContext,"주문 성공",Toast.LENGTH_SHORT).show();
                                                                             receiptAdapter.mData.clear();
                                                                         }
                                                                     }

                                                        , 0);
                                                socket_order();
                                            }
                                            else{
                                                Handler mHandler = new Handler(Looper.getMainLooper());
                                                mHandler.postDelayed(new Runnable() {
                                                                         @Override
                                                                         public void run() {
                                                                             Toast.makeText(mContext,"오류 발생, 주문 실패.",Toast.LENGTH_SHORT).show();

                                                                         }
                                                                     }

                                                        , 0);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Void> call, Throwable throwable) {
                                            Handler mHandler = new Handler(Looper.getMainLooper());
                                            mHandler.postDelayed(new Runnable() {
                                                                     @Override
                                                                     public void run() {
                                                                         Toast.makeText(mContext,"오류 발생, 주문 실패.",Toast.LENGTH_SHORT).show();

                                                                     }
                                                                 }

                                                    , 0);
                                        }
                                    });

                                }
                            });
                        }

                    }
                });
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tv_korean.setText(mData.get(position).getName());
        holder.tv_price.setText(mData.get(position).getPrice());
        Picasso.get()
                .load(mData.get(position).getImg_url().toString())
                .into(holder.tv_photo);
        //holder.tv_photo.setImageResource(mData.get(position).getPhoto());   // picasso

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout item_common;
        private TextView tv_korean;
        private TextView tv_price;
        private ImageView tv_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_common = (LinearLayout) itemView.findViewById(R.id.item_common);
            tv_korean = (TextView) itemView.findViewById(R.id.kor_common);
            tv_price = (TextView) itemView.findViewById(R.id.pri_common);
            tv_photo = (ImageView) itemView.findViewById(R.id.img_common);

        }
    }
}