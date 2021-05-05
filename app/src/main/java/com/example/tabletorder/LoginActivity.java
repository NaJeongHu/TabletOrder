package com.example.tabletorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONObject;
import org.json.JSONException;


import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText id;
    EditText password;
    Button login;
    String response = null;
    public Context context;
    public static String ID;
    public static String PW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        context = this.context;
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ID = id.getText().toString();
                PW = password.getText().toString();

                Pair<String,String> test_1 = Pair.create(ID,PW);

                validateUserTask test = new validateUserTask();
                test.execute(ID,PW);

//                JSONObject json = new JSONObject();
//                try {
//                    json.put("id", ID);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    json.put("password",PW);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
//                try {
//                    Response<auth> Auth = NetRetrofit.getInstance().getService().login(body).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (TextUtils.isEmpty(ID)) {
//                    LoginActivity.this.id.setError("이메일을 입력하세요.");
//                }
//                if (TextUtils.isEmpty(PW)) {
//                    password.setError("비밀번호를 입력하세요.");
//                }
//
//                if (!TextUtils.isEmpty(ID) && !TextUtils.isEmpty(PW)) {
//
//                    Response<auth> res = null;
//                    try {
//                        res = NetRetrofit.getInstance().getService().login(body).execute();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (res.isSuccessful()) {
//                        if (res.body() != null) { //null 뿐 아니라 오류 값이 들어올 때도 처리해줘야 함.
//                            auth users = res.body(); //body()는, json 으로 컨버팅되어 객체에 담겨 지정되로 리턴됨.
////여기서는 지정을 Call<지정타입> 이므로 List<User> 가 리턴타입이 됨.
//                            //new Log("Main 통신", response.body().get(0).toString());
//// DO SOMETHING HERE with users!
//                            Intent i = new Intent(LoginActivity.this, CellectStoreActivity.class);
//                            startActivity(i);
//
//                        } else {
//                            Toast.makeText(LoginActivity.this, "Sorry!! Incorrect Username or Password", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Sorry!! Incorrect Username or Password", Toast.LENGTH_SHORT).show();
//                    }
////                    res.enqueue(new Response<auth>() {
////                            @Override
////                            public void onResponse(Response<auth> call, Response<auth> response) {
////
////                                if (response.isSuccessful()) {
////                                    if (response.body() != null) { //null 뿐 아니라 오류 값이 들어올 때도 처리해줘야 함.
////                                        auth users = response.body(); //body()는, json 으로 컨버팅되어 객체에 담겨 지정되로 리턴됨.
//////여기서는 지정을 Call<지정타입> 이므로 List<User> 가 리턴타입이 됨.
////                                        //new Log("Main 통신", response.body().get(0).toString());
////// DO SOMETHING HERE with users!
////                                        Intent i = new Intent(LoginActivity.this, CellectStoreActivity.class);
////                                        startActivity(i);
////
////                                    } else {
////                                        Toast.makeText(LoginActivity.this, "Sorry!! Incorrect Username or Password", Toast.LENGTH_SHORT).show();
////                                    }
////                                } else {
////                                    Toast.makeText(LoginActivity.this, "Sorry!! Incorrect Username or Password", Toast.LENGTH_SHORT).show();
////                                }
////                            }
////
////                        @Override
////                        public void onFailure(Call<auth> call, Throwable throwable) {
////                            Toast.makeText(LoginActivity.this, "Sorry!! Incorrect Username or Password", Toast.LENGTH_SHORT).show();
////                        }
////                    });
//                }
            }
        });
    }

        private class validateUserTask extends AsyncTask<String, Void, String> {
        @Override

        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
//            String ID = this@LoginActivity.view.id.getText().toString();
//            String PW = password.getText().toString();
            JSONObject json = new JSONObject();
            try {
                json.put("id",params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                json.put("password",params[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json.toString());
            try {
                OrderApi orderapi = OrderApi.retrofit.create(OrderApi.class);
                Call<auth> login = orderapi.login(body);
                Response res = login.execute();
                if(res.isSuccessful()){
                    Intent i = new Intent(LoginActivity.this, CellectStoreActivity.class);
                    startActivity(i);
                }
                else{
//                    Handler mHandler = new Handler(Looper.getMainLooper());
//                    mHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
////                            Toast.makeText(context, "Sorry!! Incorrect Username or Password",Toast.LENGTH_SHORT).show();
//                        }
//                    },0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

//            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
//            postParameters.add(new BasicNameValuePair("username", params[0] ));
//            postParameters.add(new BasicNameValuePair("password", params[1] ));
            String res = null;
//            try {
//                response = CustomHttpClient.executeHttpPost("http://akinads.0fees.net/check.php", postParameters);
//                res=response.toString();
//                res= res.replaceAll("\\s+","");
//            }
//            catch (Exception e) {
//                txt_Error.setText(e.toString());
//            }
            return res;
        }//close doInBackground

        @Override
        protected void onPostExecute(String result) {

        }//close onPostExecute
    }// close validateUserTask

}
