package com.example.daki9.ma_calculatrice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;


public class HistoListAdapter extends BaseAdapter {

     ArrayList<Calcul>lesCalculs;
    private LayoutInflater inflater;
    Context moncontext;
    DatabaseManager databaseManager;


    public HistoListAdapter(Context context, ArrayList<Calcul>lesCalculs)
    {
        this.lesCalculs=lesCalculs;
        this.inflater= LayoutInflater.from(context);
        this.moncontext=context;
    }
    //retourne le nombre de ligne de la listview
    @Override
    public int getCount() {
        return lesCalculs.size();
    }
    //retourne l'item de la ligne actuel
    @Override
    public Object getItem(int position) {
        return lesCalculs.get(position);
    }
    //retourne un indice par rapport à la ligne actuel
    @Override
    public long getItemId(int position) {
        return position;
    }
    //retourne la view formaté avec gestion des évènements
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        databaseManager= new DatabaseManager(moncontext);
        if(convertView== null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item, null);
            holder.btnSup = (ImageButton) convertView.findViewById(R.id.btnSup);
            holder.txt1 = (TextView) convertView.findViewById(R.id.formule);
            holder.txt2 = (TextView) convertView.findViewById(R.id.resultat);
            holder.txt3 = (TextView) convertView.findViewById(R.id.date);
            holder.btnEnvoi = (ImageButton) convertView.findViewById(R.id.Envoi);

            //affecter le holder à la vue
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        //Valorisation du contenu du holder
        holder.txt1.setText(lesCalculs.get(position).getFormule()+" =");
        holder.txt2.setText(lesCalculs.get(position).getValeur());
        holder.txt3.setText(lesCalculs.get(position).getDate().toString());
        holder.btnSup.setTag(position);
        holder.btnEnvoi.setTag(position);
        //click sur la croix pour supprimer
        holder.btnSup.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                new AlertDialog.Builder(moncontext)
                .setTitle("Supprimer ce calcul")
                .setMessage("Voulez-vous vraiment supprimer ce calcul")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int position = (int) v.getTag();
                        //demande de suppression
                        databaseManager.delete_calcul(lesCalculs.get(position));
                        lesCalculs.remove(lesCalculs.get(position));
                        //rafraichir
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("Non",null).show();




            }
        });
        holder.btnEnvoi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = (int) v.getTag();
                String formule=lesCalculs.get(position).getFormule().toString();
                String resultat=lesCalculs.get(position).getValeur().toString();
                Intent mainActivity = new Intent (moncontext,MainActivity.class);
                mainActivity.putExtra("Formule", formule);
                mainActivity.putExtra("Resultat", resultat);
                moncontext.startActivity(mainActivity);
            }
        });


        return convertView;
    }
    private class ViewHolder
    {
        ImageButton btnSup;
        TextView txt1;
        TextView txt2;
        TextView txt3;
        ImageButton btnEnvoi;

    }

}