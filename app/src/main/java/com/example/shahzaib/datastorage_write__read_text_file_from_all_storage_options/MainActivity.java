package com.example.shahzaib.datastorage_write__read_text_file_from_all_storage_options;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 99) // permission is granted or denied for wirte_external_storage permission
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {// permission is granted
                Toast.makeText(this, "Permission is Granted", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 99);
            }
        }

    }*/

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
    }


    private String getDataFromEditText() {
        String data = editText.getText().toString();
        if (data.length() > 0) {
            return data;
        } else {
            return null;
        }
    }

    private void saveData(File file, String data) {
        try {

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            Toast.makeText(this, "Data Saved Successfully at: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    public void store_in_sharedPreferences(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("myFile", MODE_PRIVATE);
        String data = getDataFromEditText();
        if (data != null) {
            sharedPreferences.edit().putString("myData", data).apply();
            Toast.makeText(this, "Saved in SharedPreferences Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void store_in_internal_cache(View view) {

        String data = getDataFromEditText();
        if (data != null) {
            File chacheDir = getCacheDir();
            File file = new File(chacheDir.getAbsolutePath(), "myFile.txt");
            saveData(file, data);
        }


    }

    public void store_in_external_cache(View view) {
        String data = getDataFromEditText();

        File externalCacherDir = getExternalCacheDir();

        if (data != null && externalCacherDir != null) {
            File file = new File(externalCacherDir.getAbsolutePath(), "myFile.txt");
            saveData(file, data);
        }

    }

    public void store_in_internal_storage(View view) {
        String data = getDataFromEditText();
        if (data != null) {
            try {

                FileOutputStream fos = openFileOutput("myFile.txt", MODE_PRIVATE);
                fos.write(data.getBytes());
                Toast.makeText(this, "Data Successfully Save in internal Storage ", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void store_in_external_storage_private(View view) {
        String data = getDataFromEditText();
        if (data != null) {
            File folder = getExternalFilesDir("myFolder");
            File file = new File(folder, "myFile.txt");
            saveData(file, data);
        }
    }


    public void store_in_external_storage_public(View view) {
        String data = getDataFromEditText();
        if (data != null) {

            // FIRST check state
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // if media is available

                // check for permisssion
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
//                        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File folder = Environment.getExternalStoragePublicDirectory("myFolder");
                        folder.mkdir();
                        File file = new File(folder.getAbsolutePath(), "myFile.txt");
                        saveData(file, data);
                    }
                    else
                    {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},99);
                    }
                }
                else
                { // if device is less then MarshMellow no need to request permission
                    File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    File file = new File(folder, "myFile.txt");
                    saveData(file, data);
                }


            }
        }
    }







    public void go_to_load_text_data_activity(View view)
    {
        startActivity(new Intent(this,load_text_data_activity.class));
    }
}
