package com.example.daki9.ma_calculatrice;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class HistoryActivity extends AppCompatActivity {
    DatabaseManager databaseManager;
    ListView listview_calcul;
    HistoListAdapter adapter;
    ArrayList<Calcul> les_calculs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);
        listview_calcul = (ListView) findViewById(R.id.list_calcul);
        databaseManager=new DatabaseManager( this );
        creer_liste();

    }
    /*Créer la liste d'adapter*/
    private void creer_liste()
    {
        les_calculs=databaseManager.read_calcul();
        if(les_calculs!=null)
        {
            listview_calcul = (ListView) findViewById(R.id.list_calcul);
            adapter= new HistoListAdapter(this,les_calculs);
            listview_calcul.setAdapter(adapter);

        }

    }
    public void delAll(View view) {
        databaseManager.delete_all_calcul();
        les_calculs.clear();
        adapter.notifyDataSetChanged();
    }




}





