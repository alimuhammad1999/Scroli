package com.fyp.scroli.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.fyp.scroli.Data.Models.Person;
import com.fyp.scroli.Data.Models.User;
import com.fyp.scroli.R;
import com.fyp.scroli.databinding.ActivityDisplayDetailsBinding;

public class display_details extends AppCompatActivity {

    ActivityDisplayDetailsBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(display_details.this, R.layout.activity_display_details);

        Person p = (Person) getIntent().getSerializableExtra("Person");
        if(p != null){
            binddata(p);
        }else{
            binddata ((User) getIntent().getSerializableExtra("User"));
        }


    }

    private void binddata(User u) {

        Glide.with(this).load(u.getAvatar()).into(bi.avatar);
        bi.id.setText(Integer.toString(u.getId()));
        bi.name.setText(String.format("%s %s", u.getFirst_name(), u.getLast_name()));
        bi.email.setText(u.getEmail());
        bi.gender.setText("Empty Gender String");
        bi.address.setText(String.format("Empty Address String"));
        bi.about.setText("Empty About String");

    }
    private void binddata(Person p) {

        Glide.with(this).load(p.getAvatar()).into(bi.avatar);
        bi.id.setText(Integer.toString(p.getId()));
        bi.name.setText(String.format("%s %s", p.getFirst_name(), p.getLast_name()));
        bi.email.setText(p.getEmail());
        bi.gender.setText(p.getGender());
        bi.address.setText(String.format("%s, %s", p.getCity(), p.getCountry()));
        bi.about.setText(p.getAbout());

    }

}
