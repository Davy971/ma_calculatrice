package com.example.daki9.ma_calculatrice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    ScriptEngine engine;
    String currentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        engine= new ScriptEngineManager().getEngineByName("rhino");
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        Intent inte = getIntent();
        String calcul = inte.getStringExtra("CALCUL");
        if(calcul !=null)
        {
            txtCalcul.setText(calcul);
        }
        if(savedInstanceState!=null)
        {
           txtCalcul.setText(savedInstanceState.getString("param"));
        }
    }

   /* @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        currentText = savedInstanceState.getString("param", "");
        txtCalcul.setText(currentText);
    }*/

    public void btnClear(View view) {
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        TextView txtResult=(TextView)findViewById(R.id.txtResult);
        txtCalcul.setText("0");
        txtResult.setText("0");
    }

    public void btnSuppr(View view) {
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        TextView txtResult=(TextView)findViewById(R.id.txtResult);
        txtResult.setText("");

        String chaine=txtCalcul.getText().toString();
        if(chaine.length()!=0) {
            chaine = chaine.substring(0, chaine.length() - 1);
            if(chaine.length()==0)
                chaine="0";
        }
        txtCalcul.setText(chaine);
    }
    public void btn_ajout_caract(View view) {
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        TextView txtResult=(TextView)findViewById(R.id.txtResult);
        TextView texte=(TextView) view;
        if( txtCalcul.getText().toString().equals("0") && !(texte.getText().toString().equals(",")))
            {
                txtCalcul.setText(texte.getText().toString());
            }
        else
            {
                txtCalcul.setText(txtCalcul.getText().toString()+texte.getText().toString());
            }

    }



    public void btnEgal(View view) throws ScriptException
    {
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        TextView txtResult=(TextView)findViewById(R.id.txtResult);
        double  result=0;
        String result_calcul;
        currentText= txtCalcul.getText().toString();
        try
        {
            result=(double)engine.eval(currentText);
        }catch (Exception e){
            Toast.makeText(this,"Exception Raised",Toast.LENGTH_SHORT).show();
        }
        result_calcul=""+result;
        txtResult.setText(result_calcul);
        try {
            FileOutputStream fout = openFileOutput("monFichier.txt", Context.MODE_APPEND);
            OutputStreamWriter out = new OutputStreamWriter(fout);
            out.write(txtCalcul.getText().toString()+"\n");
            out.close();
        }catch (Exception e)
        {
            Toast.makeText(this,"FileException Raised",Toast.LENGTH_SHORT).show();
        }
    }
    public void btnHist(View view)
    {
        Intent  histActivity = new Intent (MainActivity.this,HistoryActivity.class);
        startActivity(histActivity);
    }

    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        currentText= txtCalcul.getText().toString();
        savedInstanceState.putString("param",currentText);
    }
}
