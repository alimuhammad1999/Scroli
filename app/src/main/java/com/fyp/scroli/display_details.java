package com.fyp.scroli;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.fyp.scroli.Data.Person;
import com.fyp.scroli.databinding.ActivityDisplayDetailsBinding;

public class display_details extends AppCompatActivity {

    ActivityDisplayDetailsBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(display_details.this,R.layout.activity_display_details);

        binddata((Person) getIntent().getSerializableExtra("Person"));

    }

    private void binddata(Person p){

        Glide.with(this).load(p.getAvatar()).into(bi.avatar);
        bi.id.setText(Integer.toString(p.getId()));
        bi.name.setText(String.format("%s %s", p.getFirst_name(), p.getLast_name()));
        bi.email.setText(p.getEmail());
        bi.gender.setText(p.getGender());
        bi.address.setText(String.format("%s, %s", p.getCity(), p.getCountry()));
        bi.about.setText(p.getAbout());

    }

}

    //Person p;
        //Intent intent = getIntent();
        //p = (Person) intent.getSerializableExtra("Person");
