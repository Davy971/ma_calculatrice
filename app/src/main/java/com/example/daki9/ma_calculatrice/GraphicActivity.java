package com.example.daki9.ma_calculatrice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class GraphicActivity extends AppCompatActivity {
    TextView txtCalcul;
    String currentText;
    ScriptEngine engine;
    GraphView graph;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graphic_activity);
        engine= new ScriptEngineManager().getEngineByName("rhino");
        graph = (GraphView) findViewById(R.id.graph);
        txtCalcul=(TextView)findViewById(R.id.txtFormule);

        if(savedInstanceState!=null)
        {
            txtCalcul.setText(savedInstanceState.getString("paramG1"));


        }


    }
    public void btn_ajout_caract_G(View view) {
        TextView txtCalcul=(TextView)findViewById(R.id.txtFormule);
        TextView texte=(TextView) view;


        String txt = txtCalcul.getText().toString() + texte.getText().toString();
        if( txt.matches("^(-?\\(+)*-?(x|\\d*|\\d+(\\.\\d*)?)((?<!\\))((?<![\\d\\.])x|(?<!x)\\d)\\)*(([+\\-]|[*\\/]-?)(\\(-?)*(x|\\d*|\\d+(\\.\\d*)?))?)*$")) {

                txtCalcul.setText(txt);
        }

    }
    public void btnClear_G(View view) {
        TextView txtCalcul=(TextView)findViewById(R.id.txtFormule);
        txtCalcul.setText("");
        graph.removeAllSeries();
    }
    public void btnSuppr_G(View view) {
        TextView txtCalcul=(TextView)findViewById(R.id.txtFormule);
        String chaine=txtCalcul.getText().toString();
        if(chaine.length()!=0) {
            chaine = chaine.substring(0, chaine.length() - 1);
            if(chaine.length()==0)
                chaine="";
        }
        txtCalcul.setText(chaine);
    }
    public void btnEgal_G(View view) throws ScriptException
    {
        TextView txtCalcul=(TextView)findViewById(R.id.txtFormule);
        double  result=0;

        LineGraphSeries<DataPoint> Series;
        DataPoint[] dp = new DataPoint[21];
        int cpt=0;

       for(int i=-10;i<=10;i++)
       {
           try
           {
                currentText= txtCalcul.getText().toString();
                String tmp = String.valueOf(i);
                currentText=currentText.replace("x",tmp);
                result = (double) engine.eval(currentText);
           } catch (Exception e) {
               Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();

           }
           DataPoint point=new DataPoint(i, result);
           dp[cpt]= point;
           cpt++;
       }
        Series = new LineGraphSeries<>(dp);
        graph.addSeries(Series);
        graph.getViewport().setMinX(-10);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setScalable(true);

    }
    public void cos_G(View view) throws ScriptException
    {
        TextView txtCalcul=(TextView)findViewById(R.id.txtFormule);
        double  result=0;

        LineGraphSeries<DataPoint> Series;
        DataPoint[] dp = new DataPoint[21];
        int cpt=0;
        double Y=0;
        for(int i=-10;i<=10;i++)
        {
            Y= Math.cos(i);
            DataPoint point=new DataPoint(i, Y);
            dp[cpt]= point;
            cpt++;
        }
        Series = new LineGraphSeries<>(dp);
        graph.addSeries(Series);
        graph.getViewport().setMinX(-10);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setScalable(true);

    }
    public void sin_G(View view) throws ScriptException
    {
        TextView txtCalcul=(TextView)findViewById(R.id.txtFormule);
        double  result=0;

        LineGraphSeries<DataPoint> Series;
        DataPoint[] dp = new DataPoint[21];
        int cpt=0;
        double Y=0;
        for(int i=-10;i<=10;i++)
        {
            Y= Math.sin(i);
            DataPoint point=new DataPoint(i, Y);
            dp[cpt]= point;
            cpt++;
        }
        Series = new LineGraphSeries<>(dp);
        graph.addSeries(Series);
        graph.getViewport().setMinX(-10);
        graph.getViewport().setMaxX(10);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setScalable(true);

    }

    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        TextView txtCalcul=(TextView)findViewById(R.id.txtFormule);
        currentText= txtCalcul.getText().toString();
        savedInstanceState.putString("paramG1",currentText);


    }
}
