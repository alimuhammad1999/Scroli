package com.fyp.scroli.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.fyp.scroli.Data.AppDatabase;
import com.fyp.scroli.Data.DatabaseClient;
import com.fyp.scroli.Data.Models.AddedUser;
import com.fyp.scroli.R;
import com.fyp.scroli.Utils.WebClient.Api;
import com.fyp.scroli.Utils.WebClient.WebClient;
import com.fyp.scroli.databinding.ActivityAddUserBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUser extends AppCompatActivity {

    ActivityAddUserBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_add_user);

        b.idBtnPost.setOnClickListener(view -> {
            adduser();
        });

    }

    private void adduser() {
        String name = b.idEdtName.getText().toString().trim();
        String job = b.idEdtJob.getText().toString().trim();
        if ( name.isEmpty() && job.isEmpty()) {
            Toast.makeText(this, "Please enter both the values", Toast.LENGTH_SHORT).show();
            return;
        }
        // calling a method to post the data and passing our name and job.
        postData(name, job);
        b.idLoadingPB.setVisibility(View.GONE);
    }

    private void postData(String name, String job) {

        b.idLoadingPB.setVisibility(View.VISIBLE);
        b.idLoadingPB.setVisibility(View.VISIBLE);
        b.idLoadingPB.setVisibility(View.VISIBLE);
        AddedUser adduser = new AddedUser(name,job);
        Call<AddedUser> call = WebClient.getInstance(Api.USER_BASE_URL).getMyApi().postUserWebResult(adduser);
        call.enqueue(new Callback<AddedUser>() {
            @Override
            public void onResponse(Call<AddedUser> call, Response<AddedUser> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(AddUser.this,
                            "There was some Error in adding the user "+response.code()
                            , Toast.LENGTH_SHORT).show();
                }else{
                    AppDatabase appDatabase;
                    appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDB();
                    appDatabase.dataAO().insertAddedUser(response.body());

                    AddedUser user = response.body();
                    String responseString = "Response Code : " + response.code() +
                            "\nName : " + user.getName() +
                            "\n" + "Job : " + user.getJob() +
                            "\nID : " + user.getId() +
                            "\nCreatedon : " + user.getCreatedAt();

                    // below line we are setting our
                    // string to our text view.
                    b.idTVResponse.setText(responseString);

                }
                finish();
            }

            @Override
            public void onFailure(Call<AddedUser> call, Throwable t) {
                b.idTVResponse.setText(new StringBuilder().append(t.getLocalizedMessage()).append("............ ").append(t.getMessage()).toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(), "bye !!!!!!!!!!!111", Toast.LENGTH_LONG).show();
        //finish();
    }
}