package com.example.tabletorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.TestLooperManager;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.tabletorder.LoginActivity.ID;
import static com.example.tabletorder.StoreCellectAdapter.socket_io;
import static com.example.tabletorder.StoreCellectAdapter.store_name;
import static com.example.tabletorder.StoreCellectAdapter.tablenum;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private String a;
    public static List cate_list;
    Dialog dsocket;
    public static Dialog receipt;
    TextView sckettv;

    //private FragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receipt = new Dialog(this);
        receipt.setContentView(R.layout.receipt);

        dsocket = new Dialog(this);
        dsocket.setContentView(R.layout.socket_popup);
        sckettv = (TextView) dsocket.findViewById(R.id.sockettv);

        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewPager_id);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //a = new String(tablenum);
        getSupportActionBar().setTitle("OB팀 TaO");
//        MenuItem item = new MenuItem()
//        item.getItemId(R.id.table_number).setTitle(a);


        // add fragment here
        cate_list = new ArrayList();

        OrderApi service = OrderApi.retrofit.create(OrderApi.class);
        service.getCategories(ID,store_name).enqueue(new Callback<List<Map<String, Object>>>() {
            @Override
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> response) {
                Log.v("TEST","카테고리는 잘 불러왔어");
                List<Map<String,Object>> storeResult = (List<Map<String, Object>>)response.body();
                ArrayList<Map<String,Object>> jsonList = (ArrayList) storeResult;

                for(int i=0;i<jsonList.size();i++){       // 카테고리 부분 동적할당
                    Log.v("TEST","카테고리는 잘 불러왔어11");
                    String aa;
                    aa = jsonList.get(i).get("name").toString();
                    cate_list.add(aa);
                    FragCommon fc = new FragCommon();
                    fc.setTabnum(i);
                    adapter.AddFragment(fc,aa);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable throwable) {
                Log.v("TEST","fail");
            }
        });


//        for(int i=0;i<size;i++){       // 카테고리 부분 동적할당
//            adapter.AddFragment(new FragCommon(),i + "aa");
//        }

        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        //viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);

        // 뷰페이저 세팅
//        ViewPager viewPager = findViewById(R.id.viewPager);
//        fragmentPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//
//        TabLayout tabLayout = findViewById(R.id.tab_layout);
//        viewPager.setAdapter(fragmentPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
        socket_called(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem action_num = menu.findItem(R.id.table_number);
        action_num.setTitle(tablenum);
        return true;
    }


    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.order_sheet:
                receipt.show();
                Toast.makeText(this.getApplicationContext(),"주문표입니다",Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public String Call_data;
    public void socket_called(Context cont){
        final Context context = cont;
        socket_io.on("call", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("주문 완료!","받아가세요!");
                Call_data = args[0].toString();

                sckettv.setText(Call_data);
                Handler mHanlder = new Handler(Looper.getMainLooper());
                mHanlder.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        dsocket.show();
                    }
                },0);

            }
        });
    }
}