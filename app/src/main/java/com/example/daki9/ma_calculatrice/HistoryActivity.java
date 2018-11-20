package com.example.daki9.ma_calculatrice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.history_activity);
        ArrayList<String> data = new ArrayList<String>();
        try
        {
            FileInputStream fin = openFileInput("monFichier.txt");
            InputStreamReader in = new InputStreamReader(fin);
            BufferedReader buffer = new BufferedReader(in);
            String ligne = "";
            while ((ligne = buffer.readLine()) != null) {
                data.add(ligne);
            }
            fin.close();
            ArrayAdapter matching = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
            ListView listview_calcul = (ListView) findViewById(R.id.list_calcul);
            listview_calcul.setAdapter(matching);
            listview_calcul.setOnItemClickListener(this);

            //appliquer sur chaque item un listener
        }catch (Exception e){

            Toast.makeText(this,"FileException Raised",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView texte=(TextView) view;
        Intent mainActivity = new Intent (HistoryActivity.this,MainActivity.class);
        mainActivity.putExtra("CALCUL", texte.getText().toString());
        startActivity(mainActivity);
    }
}





