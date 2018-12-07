package com.example.daki9.ma_calculatrice;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;



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
    public void delAll() {
        databaseManager.delete_all_calcul();
        les_calculs.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_histo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.delAll)
        {new AlertDialog.Builder(this)
                .setTitle("Supprimer tous les calculs")
                .setMessage("Voulez-vous vraiment supprimer tous ses calculs")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delAll();
                       adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Non",null).show();

        }
        return super.onOptionsItemSelected(item);
    }
}





