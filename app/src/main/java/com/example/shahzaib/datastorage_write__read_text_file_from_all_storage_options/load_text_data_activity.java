package com.example.shahzaib.datastorage_write__read_text_file_from_all_storage_options;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class load_text_data_activity extends AppCompatActivity {

    TextView textView;
    public static final String DEFAULT_VALUE = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_text_data_activity);
        textView = findViewById(R.id.textView);
    }


    public void clearTV(View view)
    {
        textView.setText("");
    }

    private String loadData(File file)
    {
        try
        {
            FileInputStream fin = new FileInputStream(file);
            StringBuffer data = new StringBuffer();
            int read = -1;
            read = fin.read();
            while (read != -1)
            {
                data.append((char)read);
                read = fin.read();
            }
            Snackbar.make(textView,"Successfully Retrieve",Snackbar.LENGTH_SHORT).show();
            return data.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return DEFAULT_VALUE;
        }
    }








    public void loadFrom_sharedPreferences(View view)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("myFile",MODE_PRIVATE);
        String data = sharedPreferences.getString("myData",DEFAULT_VALUE);
        textView.setText(data);
        if(!data.equals(DEFAULT_VALUE))
        {
            Snackbar.make(textView,"Successfully Retrieve",Snackbar.LENGTH_SHORT).show();
        }
    }

    public void loadFrom_internal_cache(View view)
    {
        File Folder = getCacheDir();
        File file = new File(Folder.getAbsolutePath(),"myFile.txt");
        textView.setText(loadData(file));
    }

    public void loadFrom_external_cache(View view)
    {
        File Folder = getExternalCacheDir();
        File file = null;
        if (Folder != null)
        {
            file = new File(Folder.getAbsolutePath(),"myFile.txt");
        }
        textView.setText(loadData(file));
    }

    public void loadFrom_internal_storage(View view)
    {

        try
        {
            FileInputStream fin = openFileInput("myFile.txt");
            StringBuffer data = new StringBuffer();
            int read = -1;
            read = fin.read();
            while (read != -1)
            {
                data.append((char)read);
                read = fin.read();
            }
            Snackbar.make(textView,"Successfully Retrieve",Snackbar.LENGTH_SHORT).show();
            textView.setText(data.toString());

        } catch (IOException e) {
            e.printStackTrace();
            textView.setText(DEFAULT_VALUE);
        }

    }

    public void loadFrom_external_storage_private(View view)
    {
        File file = getExternalFilesDir("myFolder/myFile.txt");
        textView.setText(loadData(file));
    }

    public void loadFrom_external_storage_public(View view)
    {
        File file = Environment.getExternalStoragePublicDirectory("myFolder/myFile.txt");
        textView.setText(loadData(file));
    }
}
