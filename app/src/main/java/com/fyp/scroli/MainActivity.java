package com.fyp.scroli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.fyp.scroli.Data.AppDatabase;
import com.fyp.scroli.Data.DatabaseClient;
import com.fyp.scroli.Data.Person;
import com.fyp.scroli.Utils.Adapter.MyAdapter;
import com.fyp.scroli.Utils.WebClient.WebClient;
import com.fyp.scroli.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements MyAdapter.itemClickListner {

    private List<Person> person_list;
    //private RecyclerView dataview;
    private ProgressDialog loading;
    //private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ActivityMainBinding bi;

    private static AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        setTitle("Scroli");

        Init();
        getItems();
        bi.copybtn.setOnClickListener(view -> cpy_dir());
        bi.deletebtn.setOnClickListener(view -> sendEmail());

    }
    private void Init() {

        person_list = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        bi.dataview.setLayoutManager(layoutManager);
        appDatabase = DatabaseClient.getInstance(getApplicationContext()).getAppDB();

    }

    private void getItems() {

        loading = ProgressDialog.show(this, "Fetching your data", "please wait", false, true);
        person_list = appDatabase.dataAO().getAll();

        if (!person_list.isEmpty()) {

            loading.dismiss();
            MyAdapter adapter = new MyAdapter(person_list, MainActivity.this, MainActivity.this);
            bi.dataview.setAdapter(adapter);

        } else {

            Retrofit_req();

        }
    }

    private void Retrofit_req() {

        if (true) {

            Call<List<Person>> call = WebClient.getInstance().getMyApi().getWebResults();   //api.getWebResults();
            call.enqueue(new Callback<List<Person>>() {
                @Override
                public void onResponse(Call<List<Person>> call, retrofit2.Response<List<Person>> response) {

                    if (!response.isSuccessful()) {

                        Log.d("TAG: ", "onResponseErrorCode: " + response.code());
                        Toast.makeText(MainActivity.this,
                                        "Error occured with code: " + response.code(),
                                        Toast.LENGTH_SHORT)
                                .show();
                        return;

                    }

                    loading.dismiss();
                    person_list = response.body();
                    parsejasonobj();

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

    private void parsejasonobj() {
        appDatabase.dataAO().insertAll(person_list);
        MyAdapter adapter = new MyAdapter(person_list, MainActivity.this, MainActivity.this);
        bi.dataview.setAdapter(adapter);

    }

    private void cpy_dir(){
        File db_Dir = new File(this.getDatabasePath("mydatabase").getParent());
        File destination = new File(this.getDatabasePath("mydatabase").getParent() + "/backups");

        if (db_Dir.isDirectory()) {
            File[] files = db_Dir.listFiles(file -> (file.isFile()));
            if (files != null && files.length > 0) {
                for (File file : files) {
                    copyfile(file,destination);
                }
            }
        }
    }

    private File copyfile(File myfile,File directory){

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
            Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
        }
        return null;
    }

   /* public void get_dir(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/octet-stream");

        Uri uri = new FileProvider().getDatabaseURI(this);

        intent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(intent, "Backup via:"));
    }*/

 /*   public void dir(){
        File exportFile = this.getDatabasePath("mydatabase"); // new approach

        return getFileUri(this, exportFile);
    }

    public Uri getFileUri(Context c, File f){
        return getUriForFile(c, "com.url.myapp.fileprovider", f);
    }
*/


    public void sendEmail(){
        appDatabase.dataAO().checkpoint(new SimpleSQLiteQuery("pragma wal_checkpoint(full)"));
        /*File privateRootDir = getFilesDir();
        File db = new File(this.getDatabasePath("mydatabase").getParent()+"/mydatabase");
        File sendfile = copyfile(db,privateRootDir);*/
        File db = this.getDatabasePath("mydatabase");
        Uri u = FileProvider.getUriForFile(this,this.getPackageName() + ".fileprovider",db);
        //Uri uri = FileProvider.getUriForFile(this, this.getPackageName() + ".fileprovider", sendfile);
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
}