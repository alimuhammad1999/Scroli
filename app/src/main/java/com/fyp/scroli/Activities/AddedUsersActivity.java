package com.fyp.scroli.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.scroli.Data.AppDatabase;
import com.fyp.scroli.Data.DatabaseClient;
import com.fyp.scroli.Data.Models.AddedUser;
import com.fyp.scroli.R;
import com.fyp.scroli.Utils.Adapter.AddedUserAdapter;
import com.fyp.scroli.databinding.ActivityAddedUsersBinding;

import java.util.ArrayList;
import java.util.List;

public class AddedUsersActivity extends AppCompatActivity implements AddedUserAdapter.refresher {

    ActivityAddedUsersBinding b;
    private List<AddedUser> addeduser_list;
    private AddedUser[] list;
    private RecyclerView.LayoutManager layoutManager;
    private static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b= DataBindingUtil.setContentView(this, R.layout.activity_added_users);

        Init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getItems();
    }

    private void Init() {
        addeduser_list = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        b.mylist.setLayoutManager(layoutManager);
        appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDB();
        b.copybtn.setOnClickListener(v -> add_User());
        getItems();
    }

    private void add_User() {
        Intent i = new Intent(AddedUsersActivity.this,AddUser.class);
        startActivity(i);
    }

    private void getItems() {

        list = appDatabase.dataAO().getAllaAddedUsers();

        if (!(list.length == 0)) {

            AddedUserAdapter adapter = new AddedUserAdapter(list,this);
            b.mylist.setAdapter(adapter);
            /*UserAdapter adapter = new UserAdapter(addeduser_list, Reqres.this);
            b.mylist.setAdapter(adapter);*/

        }
    }

    @Override
    public void refreshview() {
        list = appDatabase.dataAO().getAllaAddedUsers();
        AddedUserAdapter adapter = new AddedUserAdapter(list,this);
        b.mylist.setAdapter(adapter);
    }
}