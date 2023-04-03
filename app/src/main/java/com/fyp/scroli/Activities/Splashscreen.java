package com.fyp.scroli.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.scroli.R;

public class Splashscreen<T> extends AppCompatActivity {

    Handler handler;
    Runnable r;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        handler = new Handler();
        Intent intent = new Intent(Splashscreen.this, MainActivity.class);
        int delay = 1000;
        bundle = getIntent().getExtras();

        if(bundle != null){
            //delay = 10000;
            //initIntent();
        }

        r = () -> {
            try{
                startActivity(intent);
                //finish();
            }catch (Exception e){
                Log.d("TAG", "onCreate: "+e.getMessage());
            }

        };
        handler.postDelayed(r,delay);
    }

    private void initIntent() {
        ImageView img = (ImageView) findViewById(R.id.logo_id);
        Intent i = getIntent();
        Uri uri = (Uri)bundle.get(Intent.EXTRA_STREAM);

        if (i.getType().contains("image/")) {
            Toast.makeText(getApplicationContext(), "Just trying out the Intent feature!!", Toast.LENGTH_SHORT).show();
            if(uri != null){
                img.setImageURI(uri);
            }else{
                Log.d("TAG", "initIntent: "+uri);
                Toast.makeText(this, "The Uri from getData() is null", Toast.LENGTH_SHORT).show();
            }

        } else if (i.getType().equals("text/plain")) {
            Toast.makeText(getApplicationContext(),
                    "Just trying out the Intent feature!! " +
                            i.getDataString() +
                            "\n This is your data passed to my activity!!!",
                    Toast.LENGTH_SHORT).show();
        }

        Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
        setResult(Activity.RESULT_OK, result);
        finish();

    }
    /*private static JSONArray cur2Json(Cursor cursor) {
        //http://stackoverflow.com/questions/13070791/android-cursor-to-jsonarray
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            int i;// = 0;
            for (  i = 0; i < totalColumn; i++) {

                if (cursor.getColumnName(i) != null) {
                    try {
                        String getcol = cursor.getColumnName(i),
                                getstr = cursor.getString(i);


                        Log.d(TAG, "cur2Json: "+"ColumnName(i):\t " + getcol + "\t: " + getstr);
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i)
                        );

                    } catch (JSONException e) {
                        Log.d(TAG, "cur2Json: "+e.fillInStackTrace());
                    }
                }
            }//for
            Log.d(TAG, "cur2Json: "+"columns i:\t " + i + "\totalColumn:\t " + totalColumn);
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        return resultSet;
    }//cur2Json

    public ArrayList<Person> readPersons(Cursor cursor) {
        // on below line we are creating a
        // database for reading our database.


        // on below line we are creating a new array list.
        ArrayList<Person> courseModalArrayList
                = new ArrayList<>();

        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                //Log.d(""+cursor.getPosition(), "readPersons: "+cursor.getString(3));
                // on below line we are adding the data from
                // cursor to our array list.
                courseModalArrayList.add(new Person(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        return courseModalArrayList;
    }

    private void deletethis(){
        try {
            AppDatabase DB = DatabaseClient.getInstance(this).getAppDB();
            SimpleSQLiteQuery Query = new SimpleSQLiteQuery("SELECT * FROM " + "Person");
            Cursor cursor
                    = DB.dataAO().get(Query);
            Class<?> clazz = Class.forName("com.fyp.scroli.Data.Models.Person");

//            HashMap<String, class> map = new HashMap<>();
//            map.put("Person",Person);
            JSONArray j = cur2Json(cursor);
            Log.d(TAG, "onCreate: "+new TypeToken<Person>(){}.getType());
            Log.d(TAG, "onCreate: "+j);
            Gson gson = new Gson();
            p_list = gson.fromJson(j.toString(),new TypeToken<List<Person>>(){}.getType());
            Log.d(TAG, "onCreate: "+p_list.size());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
