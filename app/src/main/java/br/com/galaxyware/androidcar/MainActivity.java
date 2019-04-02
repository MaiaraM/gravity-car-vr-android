package br.com.galaxyware.androidcar;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//-------------Graficos e dados de LEFT BRAKE
        GraphView graphLB =  findViewById(R.id.graph);
        LineGraphSeries<DataPoint> dadosLB = new LineGraphSeries<>();


        TextInputEditText lbMin = (TextInputEditText) findViewById(R.id.lbMin);
        TextInputEditText lbMax = (TextInputEditText) findViewById(R.id.lbMax);
        TextInputEditText lbAv = (TextInputEditText) findViewById(R.id.lbAv);
        TextInputEditText lbCur = (TextInputEditText) findViewById(R.id.lbCur);
        TextInputEditText lbMA = (TextInputEditText) findViewById(R.id.lbMA);


        GraphView graph1 =  findViewById(R.id.graph1);
        GraphView graph2 = findViewById(R.id.graph2);

        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>() ;

        String jsonString = stringFromAsset(this, "texto.json");
        try{

            JSONArray dados = new JSONArray(jsonString);
            String res = "";

            String result = "";
            for (int i = 0; i < dados.length(); i++) {
                JSONObject city = dados.getJSONObject(i);
                //new Gson().fromJson(city.toString(), City.class);
                result += "RB : " + city.getInt("RB") + "\n" +
                        "LB : " + city.getInt("LB") + "\n" +
                        "SA:" + city.getInt("SA")  + "\n";

                dadosLB.appendData(new DataPoint(i+1, city.getInt("RB")) , false , 10 );
                series1.appendData(new DataPoint(i+1, city.getInt("LB")) , false , 500 );
                series2.appendData(new DataPoint(i+1, city.getInt("SA")) , false , 500 );

                lbMin.setText("0");
                lbMax.setText("500");
                lbAv.setText("250");
                lbCur.setText("0");
                lbMA.setText("120");
            }


        }catch (Exception ex){
            Log.d("erro:", ex.getLocalizedMessage());
        }


        graphLB.addSeries(dadosLB);
        graph1.addSeries(series1);
        graph2.addSeries(series2);


    }

    public String stringFromAsset(Context context, String assertFileName){
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(assertFileName);
            String result = stringFromStream(is);
            is.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String stringFromStream(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
