package com.example.tabletorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.tabletorder.LoginActivity.ID;

public class CellectStoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
//    Button button;
//    public static String tablenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cellect_store);

        recyclerView = findViewById(R.id.cellect_recycler);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        OrderApi service = OrderApi.retrofit.create(OrderApi.class);

        service.getStore(ID).enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                Log.v("TEST","success");
                List<Map<String,Object>> storeResult = (List<Map<String, Object>>)response.body();
                ArrayList<Map<String,Object>> jsonList = (ArrayList) storeResult;   //  어떤 걸get 해야하나 모르겠음
                mAdapter = new StoreCellectAdapter(jsonList);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable throwable) {
                Log.v("TEST","fail");

            }
        });
//        button = (Button) findViewById(R.id.create_table);
//        final EditText tnum = (EditText) findViewById(R.id.table_num);
//        button.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                tablenum = tnum.getText().toString();
//                Intent i = new Intent(CellectStoreActivity.this, MainActivity.class);
//                startActivity(i);
//            }
//        });
    }
}