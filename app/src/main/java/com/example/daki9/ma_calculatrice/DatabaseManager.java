package com.example.daki9.ma_calculatrice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {


    public static final String Name="mabase.db";
    private static final int VERSION_BDD=2;
    String str_create= "CREATE TABLE Calcul ("
            +"ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            +"Formule TEXT NOT NULL,"
            +"Valeur TEXT NOT NULL,"
            +"Date TEXT NOT NULL )";

    String strSql;
    public DatabaseManager(Context context) {
        super(context, Name, null, VERSION_BDD);
    }
    public void insertCalcul(String formule,String valeur)
    {
        formule=formule.replace("'","''");
        valeur=valeur.replace("'","''");
        String mydate= java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String strSql="INSERT INTO Calcul (Formule,Valeur,Date) VALUES ('"
                +formule+"','"
                +valeur +"','"
                +mydate +"')";
        this.getWritableDatabase().execSQL(strSql);
        Log.i("DataBase","Insert Invoked");
    }
    public ArrayList<Calcul> read_calcul()
    {
        ArrayList<Calcul> calcul= new ArrayList<>();
        String strSql="SELECT * FROM Calcul ORDER BY ID ASC";
        Cursor cursor= this.getReadableDatabase().rawQuery(strSql,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            Calcul calculData= new Calcul(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            calcul.add(calculData);
            cursor.moveToNext();
        }
        cursor.close();
        return calcul;
    }
    public void delete_calcul(Calcul calcul)
    {
        String strSql="DELETE FROM Calcul WHERE Calcul.ID="+calcul.getID()+"";
        this.getWritableDatabase().execSQL(strSql);
    }
    public void delete_all_calcul()
    {
        String strSql="DELETE FROM Calcul";
        this.getWritableDatabase().execSQL(strSql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(str_create);
        Log.i("DataBase","Invoked");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
