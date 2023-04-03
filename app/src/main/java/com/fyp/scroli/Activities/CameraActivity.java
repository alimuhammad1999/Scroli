package com.fyp.scroli.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import com.fyp.scroli.R;
import com.fyp.scroli.databinding.ActivityCameraBinding;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {

    private static final int PERMISSIONS_CODE = 100;
    private static final int REQUEST_CAPTURE = 101;
    private static final int IMAGE = 0;
    private static final int VIDEO = 1;
    String imageFilePath;
    private static final String TAG = "Fourth Camera";
    private static Uri photoURI;
    private int type;
    ActivityCameraBinding b;
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(CameraActivity.this,R.layout.activity_camera);
        b.imageButton.setOnClickListener(view -> {
            type = IMAGE;
            getpermissions();
        });
        b.videoButton.setOnClickListener(view -> {
            type = VIDEO;
            getpermissions();
        });
    }

    private void getpermissions() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissions, PERMISSIONS_CODE);
        } else
            openCameraIntent();
    }

    private void openCameraIntent() {

        Intent intent;
        if (type == IMAGE) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            b.video.setVisibility(View.GONE);
            b.image.setVisibility(View.VISIBLE);
        } else {
            intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            b.image.setVisibility(View.GONE);
            b.video.setVisibility(View.VISIBLE);
        }
        photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (Exception ex) {
            Log.d(TAG, "openCameraIntent: " + ex.getMessage());
        }
        if (photoFile != null) {

            photoURI = FileProvider.getUriForFile(this, "com.example.cameraapp.fileprovider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(intent, REQUEST_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("mmss",
                Locale.getDefault()).format(new Date()).substring(0, 4);
        String filetype , filepath , filename;
        if (type == IMAGE) {
            filetype = ".jpg";
            filepath = "/Pictures";
            filename = "IMG_" + timeStamp;
        } else{
            filetype = ".mp4";
            filepath = "/Movies";
            filename = "VID_" + timeStamp;
        }
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + filepath);
        File image = new File(storageDir.getAbsolutePath(), filename + filetype);
        imageFilePath = image.getAbsolutePath();

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CAPTURE) {
            Toast.makeText(this, "Sucess!!!", Toast.LENGTH_SHORT).show();

            if (data != null) {

                if (type == IMAGE) {
                    b.image.setImageURI(photoURI);
                } else{
                    photoURI = Uri.parse(data.getData().toString());
                    b.video.setVideoURI(photoURI);
                    b.video.start();
                }

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && requestCode == PERMISSIONS_CODE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED)
            openCameraIntent();
        else
            Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show();
    }

}