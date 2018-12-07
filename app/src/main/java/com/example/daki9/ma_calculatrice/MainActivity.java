package com.example.daki9.ma_calculatrice;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MainActivity extends AppCompatActivity {
    ScriptEngine engine;
    String currentText;
    String result_calcul;
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private DatabaseManager databaseManager;
    TextView txtCalcul;
    TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        engine= new ScriptEngineManager().getEngineByName("rhino");
        txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        txtResult=(TextView)findViewById(R.id.txtResult);
        databaseManager= new DatabaseManager( this );


        Intent inte = getIntent();
        String formule = inte.getStringExtra("Formule");
        String resultat = inte.getStringExtra("Resultat");
        if(formule !=null && resultat!=null)
        {
            txtCalcul.setText(formule);
            txtResult.setText(resultat);
        }
        if(savedInstanceState!=null)
        {
           txtCalcul.setText(savedInstanceState.getString("param1"));
           txtResult.setText(savedInstanceState.getString("param2"));

        }
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
//        currentText = savedInstanceState.getString("param", "");
//        txtCalcul.setText(currentText);
//    }

    public void btnClear(View view) {
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        TextView txtResult=(TextView)findViewById(R.id.txtResult);
        txtCalcul.setText("");
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
                chaine="";
        }
        txtCalcul.setText(chaine);
    }
    public void btn_ajout_caract(View view) {
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        TextView txtResult=(TextView)findViewById(R.id.txtResult);
        TextView texte=(TextView) view;
        char someChar='(';
        int count1=0;
        int count2=0;

        String txt = txtCalcul.getText().toString() + texte.getText().toString();
        if( txt.matches("^(-?\\(+)*-?(\\d*|\\d+(\\.\\d*)?)\\)*(\\d\\)*(([+\\-]|[*/]-?)(\\(-?)*(\\d*|\\d+(\\.\\d*)?))?)*$")) {
            if(texte.getText().toString().equals(")"))
            {

                for (int i = 0; i < txt.length(); i++) {
                    if (txt.charAt(i) == someChar)
                        count1++;
                    if (txt.charAt(i)==')')
                        count2++;

                }
                if(count1>=count2)
                    txtCalcul.setText(txt);
            }
            else
              txtCalcul.setText(txt);
        }

    }



    public void btnEgal(View view) throws ScriptException
    {
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        TextView txtResult=(TextView)findViewById(R.id.txtResult);
        double  result=0;
        currentText= txtCalcul.getText().toString();
        int count1=0;
        int count2=0;
        for(int i=0;i<currentText.length();i++)
        {
            if(currentText.charAt(i)=='(')
                count1++;
            if(currentText.charAt(i)==')')
                count2++;
        }
        if(count1>count2)
        {
            for(int j=count2;j<count1;j++)
            {
                currentText += ')';
            }
        }
        try
        {
            result=(double)engine.eval(currentText);
        }catch (Exception e){
            Toast.makeText(this,"Exception Raised",Toast.LENGTH_SHORT).show();
        }
        result_calcul=""+result;
        txtResult.setText(result_calcul);
        databaseManager.insertCalcul(currentText,result_calcul);
    }
    public void btnHist(View view)
    {
        Intent  histActivity = new Intent (MainActivity.this,HistoryActivity.class);
        startActivity(histActivity);
    }

    public void btnVocal(View view) {
        startVoiceInput();
    }
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bonjour,Enoncer votre calcul");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String text;
        double resu=0;
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text=result.get(0);
                    text=text.replace("x","*");
                    txtCalcul.setText(text);
                    try
                    {
                        resu=(double)engine.eval(text);
                    }catch (Exception e){
                        Toast.makeText(this,"Exception Raised",Toast.LENGTH_SHORT).show();
                    }
                    result_calcul=""+resu;
                    txtResult.setText(result_calcul);
                    databaseManager.insertCalcul(text,result_calcul);

                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        TextView txtCalcul=(TextView)findViewById(R.id.txtCalcul);
        TextView txtResult=(TextView)findViewById(R.id.txtResult);
        currentText= txtCalcul.getText().toString();
        result_calcul= txtResult.getText().toString();
        savedInstanceState.putString("param1",currentText);
        savedInstanceState.putString("param2",result_calcul);

    }
}
