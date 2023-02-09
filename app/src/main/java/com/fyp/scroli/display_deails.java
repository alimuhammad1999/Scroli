package com.fyp.scroli;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fyp.scroli.Data.Person;

import java.util.Date;

public class display_deails extends AppCompatActivity {

    Person p;
    TextView id,name,email,gender,address,about;
    ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_deails);

        avatar = (ImageView) findViewById(R.id.avatar);
        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);
        address = (TextView) findViewById(R.id.address);
        about = (TextView) findViewById(R.id.about);

        Date d =new Date();
        Toast.makeText(this,
                MainActivity.addDays(d,5).toString(), Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        p = (Person) intent.getSerializableExtra("Person");
        binddata(p);



    }
    private void binddata(Person p){

       /* String name_ = p.getFirst_name()+" "+p.getLast_name();
        String id_ = Integer.toString(p.getId());
        String email_ = p.getEmail();
        String gender_ = p.getGender();
        String address_ = p.getCity()+", "+p.getCountry();
        String about_ = p.getAbout();
*/
        Glide.with(this).load(p.getAvatar()).into(avatar);
        id.setText(Integer.toString(p.getId()));
        name.setText(p.getFirst_name()+" "+p.getLast_name());
        email.setText(p.getEmail());
        gender.setText(p.getGender());
        address.setText(p.getCity()+", "+p.getCountry());
        about.setText(p.getAbout());

    }

}