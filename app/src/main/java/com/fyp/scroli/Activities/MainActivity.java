package com.fyp.scroli.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.fyp.scroli.Data.AppDatabase;
import com.fyp.scroli.Data.DatabaseClient;
import com.fyp.scroli.Data.Models.Person;
import com.fyp.scroli.R;
import com.fyp.scroli.Utils.Adapter.PersonAdapter;
import com.fyp.scroli.Utils.WebClient.Api;
import com.fyp.scroli.Utils.WebClient.WebClient;
import com.fyp.scroli.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements PersonAdapter.itemClickListner{

    private List<Person> person_list;
    private ProgressDialog loading;
    private RecyclerView.LayoutManager layoutManager;
    private ActivityMainBinding bi;
    private static AppDatabase appDatabase;
    private PersonAdapter adapter;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        setTitle("Scroli");

        Init();

    }

    private void Init() {

        person_list = new ArrayList<>();
        appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDB();
        adapter = new PersonAdapter(person_list, MainActivity.this,callback);

        bi.dataview.setAdapter(adapter);
        bi.copybtn.setOnClickListener(view -> cpy_dir());
        bi.deletebtn.setOnClickListener(view -> sendDBEmail());

        InitItems();

        InitScrollListner();

    }



    private List<Person> getValues(int from,int len){
        SimpleSQLiteQuery Query = new SimpleSQLiteQuery("SELECT * FROM Person LIMIT "+ from +","+ len);

        return appDatabase.dataAO().getsome(Query);
    }

    private void InitItems() {
        List<Person> temp_list = getValues(0,10);

        if (!temp_list.isEmpty()) {
            person_list.addAll(temp_list);
        } else {
            Retrofit_req();
        }
    }

    private void Retrofit_req() {
        boolean CONDETECTOR = true;
        if (CONDETECTOR) {

            loading = ProgressDialog.show(this, "Fetching your data", "please wait", false, true);
            Call<List<Person>> call = WebClient.getInstance(Api.PERSON_BASE_URL).getMyApi().getPersonWebResults();   //api.getWebResults();
            call.enqueue(new Callback<List<Person>>() {
                @Override
                public void onResponse(Call<List<Person>> call, retrofit2.Response<List<Person>> response) {
                    if (!response.isSuccessful()) {

                        Toast.makeText(MainActivity.this,
                                        "Error occured with code: " + response.code(),
                                        Toast.LENGTH_SHORT).show();
                        return;
                    }
                    loading.dismiss();
                    appDatabase.dataAO().insertAllPerson(response.body());
                    InitItems();
                    adapter.add(person_list);
                    bi.dataview.setAdapter(adapter);
                }
                @Override
                public void onFailure(Call<List<Person>> call, Throwable t) {
                    loading.dismiss();
                    Log.d("Error Message: ", "onFailure: " + t.getMessage());
                }
            });
        } else {
            Log.d("TAG", "Retrofit_req: Unreachable");
        }
    }

    private void InitScrollListner() {
        bi.dataview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == person_list.size() - 1) {
                        //bottom of list!
                        person_list.add(null);
                        adapter.notifyItemInserted(person_list.size() - 1);
                        bi.dataview.smoothScrollToPosition(person_list.size());
                        isLoading = true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadMore();
                            }
                        },2000);
                    }
                }
            }

            private void loadMore() {
                int scrollPosition = person_list.size();
                person_list.remove(scrollPosition-1);
                adapter.notifyItemRemoved(scrollPosition);
                List<Person> temp = getValues(scrollPosition-1,10);

                if(!temp.isEmpty()){
                    person_list.addAll(temp);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(MainActivity.this, "List end reached", Toast.LENGTH_SHORT).show();
                    ProgressBar pb = findViewById(R.id.progressBar);
                    pb.setVisibility(View.GONE);
                    bi.dataview.clearOnScrollListeners();
                }
                isLoading = false;
            }
        });
    }

    private void cpy_dir(){
        File db_Dir;
        db_Dir = new File(Objects.requireNonNull(this.getDatabasePath("mydatabase").getParent()));
        File destination = new File(this.getDatabasePath("mydatabase").getParent() + "/backups");

        if (db_Dir.isDirectory()) {
            File[] files = db_Dir.listFiles(File::isFile);
            if (files != null && files.length > 0) {
                for (File file : files) {
                    copyfile(file,destination);
                }
            }
        }
    }

    public static File copyfile(File myfile,File directory){

        String path = directory.getAbsolutePath() + "/" + myfile.getName();
        File dest = new File(path);
        dest.getParentFile().mkdirs();

        try {
            FileChannel src = new FileInputStream(myfile).getChannel();
            FileChannel dst = new FileOutputStream(dest).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            return dest;
        } catch (Exception e) {
            Log.e("MYAPP", "exception", e);
            //Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void sendDBEmail(){
        appDatabase.dataAO().checkpoint(new SimpleSQLiteQuery("pragma wal_checkpoint(full)"));

        File db = new File(this.getDatabasePath("mydatabase").getParent(),"mydatabase");
        Uri u = FileProvider.getUriForFile(this,this.getPackageName() + ".fileprovider",db);

        String[] mailto = {"alianwar1999@gmail.com"};
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL  , mailto);
        email.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        email.putExtra(Intent.EXTRA_TEXT   , "body of email");
        email.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        email.setDataAndType(
                u,
                "message/rfc822");
        email.putExtra(Intent.EXTRA_STREAM,u);
        try {
            startActivity(Intent.createChooser(email, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, display_details.class);
        intent.putExtra("Person", person_list.get(position));
        startActivity(intent);
    }

    PersonAdapter.TestCallback callback = () ->
            Toast.makeText(MainActivity.this, "Callback without implementing grom ma", Toast.LENGTH_SHORT).show();

}