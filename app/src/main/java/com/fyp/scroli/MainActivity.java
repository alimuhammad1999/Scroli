package com.fyp.scroli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fyp.scroli.Data.AppDatabase;
import com.fyp.scroli.Data.DatabaseClient;
import com.fyp.scroli.Data.Person;
import com.fyp.scroli.Utils.Adapter.MyAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.itemClickListner {

    private List<Person> person_list;
    private RecyclerView dataview;
    private ProgressDialog loading;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView date;

    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Scroli");

        date = (TextView) findViewById(R.id.head);
        dataview = findViewById(R.id.dataview);
        layoutManager = new LinearLayoutManager(this);
        dataview.setLayoutManager(layoutManager);
        appDatabase =  DatabaseClient.getInstance(getApplicationContext()).getAppDB();

        getItems();
        getItems();
    }

    private void getItems() {

        loading =  ProgressDialog.show(this,"Fetching your data","please wait",false,true);
        person_list = new ArrayList<>();
        person_list = appDatabase.dataAO().getAll();

        if(!person_list.isEmpty()){
            loading.dismiss();
           // Toast.makeText(this, "Data Fetched from local DB", Toast.LENGTH_SHORT).show();
            MyAdapter adapter = new MyAdapter(person_list,MainActivity.this,MainActivity.this);
            dataview.setAdapter(adapter);
        }else{
            make_server_req();
        }
    }

    private void make_server_req(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String api = "https://mocki.io/v1/d555dfbc-9dde-4a34-be29-356cb8b9b71f";
        Toast.makeText(this, "making request to server", Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        parsejasonobj(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.dismiss();

            }
        });

        queue.add(stringRequest);

    }

    private void parsejasonobj(String response){

        person_list = new ArrayList<>();
        person_list = Arrays.asList(new Gson().fromJson(response, Person[].class));

        appDatabase.dataAO().insertAll(person_list);

        adapter = new MyAdapter(person_list,this,MainActivity.this);
        dataview.setAdapter(adapter);

    }
    //this is the change i made to my main activity
    // so i can see them on git hub
    public static Date addDays(Date date, int days){

        //making changes

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);

        date = calendar.getTime();

        return date;
    }
    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(MainActivity.this,display_deails.class);
        intent.putExtra("Person",person_list.get(position));
        startActivity(intent);
    }
}