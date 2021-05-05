package com.example.tabletorder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tabletorder.LoginActivity.ID;
import static com.example.tabletorder.MainActivity.cate_list;
import static com.example.tabletorder.StoreCellectAdapter.store_name;
import static com.example.tabletorder.ViewPagerAdapter.tab_position;

public class FragCommon extends Fragment {

    View v;
    private RecyclerView myrecyclerview; //, sizerecyclerview, addirecyclerview
    private List<Common> lstCommon;
    public JSONArray dd, ee;
    private int tabnum;

//    public FragCommon(int _tabnum) {
//        this.tabnum=_tabnum;
//    }


    public int getTabnum() {
        return tabnum;
    }

    public void setTabnum(int tabnum) {
        this.tabnum = tabnum;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_common,container,false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.common_recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),lstCommon);
        myrecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),4));
        myrecyclerview.setAdapter(recyclerViewAdapter);

//        sizerecyclerview = (RecyclerView) v.findViewById(R.id.size_recyclerview);
//        RecyclerViewAdapter recyclerViewAdapter1 = new RecyclerViewAdapter(dd);


        return v;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OrderApi service = OrderApi.retrofit.create(OrderApi.class);


        lstCommon = new ArrayList<>();
        service.getMenu(ID, store_name, cate_list.get(tabnum).toString()).enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                if (response.isSuccessful()) {
                    //Log.v("TEST", "0번째 스위치 성공");
                    Log.v("TEST", "현재 불러온 카테고리 포지션은" + tab_position);
                    List<Map<String, Object>> storeResult = (List<Map<String, Object>>) response.body();
                    ArrayList<Map<String, Object>> jsonList = (ArrayList) storeResult;
                    for (int i = 0; i < jsonList.size(); i++) {       // 카테고리 부분 동적할당
                        String aa, bb, cc;
                        try {
                            JSONObject ff = new JSONObject(jsonList.get(i).get("topping").toString());
                            if (ff.has("size")) {
                                dd = ff.getJSONArray("size");
                            } else {
                                dd = null;
                            }
                            if (ff.has("additional")) {
                                ee = ff.getJSONArray("additional");
                            } else {
                                ee = null;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        aa = jsonList.get(i).get("name").toString();
                        bb = jsonList.get(i).get("price").toString();
                        cc = jsonList.get(i).get("img_url").toString();
                        lstCommon.add(new Common(aa, bb, cc, dd, ee));
                        Log.v("TEST", "for문에서 방금 이 메뉴 불러왔어" + aa);
                    }
                    myrecyclerview.getAdapter().notifyDataSetChanged();
                } else {
                    Log.v("TEST", "아무것도 받은 게 없어");
                    //lstCommon.add(new Common());
                }
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable throwable) {
                Log.v("TEST", "0번째 스위치 실패");
            }
        });

//
//        lstCommon = new ArrayList<>();
////            민수 생성자
////            auth a = new auth("id","token");
//
//        // 카테고리별로 메뉴구성을 따로 받아야 하므로, if문 사용하던가 해서 구별
//        for(int i=0;i<10;i++){              // 카테고리별 메뉴구성 리사이클러뷰
//            lstCommon.add(new Common(i + "아메리카노","Americano","4,100원",R.drawable.ic_launcher_background));
//        }

    }
}