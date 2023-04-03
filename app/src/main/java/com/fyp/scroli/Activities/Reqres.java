package com.fyp.scroli.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.scroli.Data.AppDatabase;
import com.fyp.scroli.Data.DatabaseClient;
import com.fyp.scroli.Data.Models.User;
import com.fyp.scroli.R;
import com.fyp.scroli.Utils.Adapter.UserAdapter;
import com.fyp.scroli.Utils.WebClient.Api;
import com.fyp.scroli.Utils.WebClient.WebClient;
import com.fyp.scroli.databinding.ActivityReqresBinding;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Reqres extends AppCompatActivity implements UserAdapter.itemClickListner {

    ActivityReqresBinding b;
    private List<User> user_list;
    private ProgressDialog loading;
    private RecyclerView.LayoutManager layoutManager;
    private static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(Reqres.this,R.layout.activity_reqres);
        
        Init();
    }

    private void Init() {
        user_list = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        b.mylist.setLayoutManager(layoutManager);
        appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDB();
        //Retrofit_req();
        getItems();
    }

    private void getItems() {

        loading = ProgressDialog.show(this, "Fetching your data", "please wait", false, true);
        user_list = appDatabase.dataAO().getAllUsers();

        if (!user_list.isEmpty()) {

            loading.dismiss();
            UserAdapter adapter = new UserAdapter(user_list, Reqres.this);
            b.mylist.setAdapter(adapter);

        } else {

            Retrofit_req();

        }
    }

    private void Retrofit_req() {

        Call<JsonObject> call1 = WebClient.getInstance(Api.USER_BASE_URL).getMyApi().getUserWebResults("1");
        Call<JsonObject> call2 = WebClient.getInstance(Api.USER_BASE_URL).getMyApi().getUserWebResults("2");

        call(call1);
        call(call2);

    }

    private void call(Call<JsonObject> call){
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                loading.dismiss();
                if(!response.isSuccessful() || response.body() == null){

                    Log.d("TAG: ", "onResponseErrorCode: " + response.code());
                    Toast.makeText(Reqres.this,
                            "Error occured with code: " + response.code(),
                            Toast.LENGTH_SHORT).show();
                    return;

                }

                JsonArray jarray = response.body().getAsJsonArray("data");

                Gson gson = new Gson();
                if(user_list.size() != 0){
                    user_list.addAll(gson.fromJson(jarray.toString(), new TypeToken<List<User>>(){}.getType()));
                    appDatabase.dataAO().insertAllUser(user_list);
                    UserAdapter adapter = new UserAdapter(user_list,Reqres.this);
                    b.mylist.setAdapter(adapter);
                }else{
                    user_list = gson.fromJson(jarray.toString(), new TypeToken<List<User>>(){}.getType());
                }


                /*List<Person> plist = appDatabase.dataAO().getAllPerson();
                if(plist.size() != 0){
                    for(User user: user_list){
                        user.setAvatar(plist.get(user.getId()).getAvatar());
                    }
                }*//*
                appDatabase.dataAO().insertAllUser(user_list);;
                UserAdapter adapter = new UserAdapter(user_list,Reqres.this);
                b.mylist.setAdapter(adapter);*/

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                loading.dismiss();
                Log.d("TAG", "onFailure: "+t.getMessage());

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Reqres.this, display_details.class);
        intent.putExtra("User", user_list.get(position));
        startActivity(intent);
    }
}