package br.com.galaxyware.androidcar;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FragLeft extends Fragment {
    private static final String TAG = "FragLeft";

    @BindView(R.id.graph) GraphView graphLB;
    @BindView(R.id.lbMin) TextInputEditText lbMin;
    @BindView(R.id.lbMax) TextInputEditText lbMax;
    @BindView(R.id.lbAv) TextInputEditText lbAv;
    @BindView(R.id.lbCur) TextInputEditText lbCur;
    @BindView(R.id.lbMA) TextInputEditText lbMA;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_left, container, false);
        ButterKnife.bind(this,view);

        LineGraphSeries<DataPoint> dadosLB = new LineGraphSeries<>();

        String jsonString = stringFromAsset(view.getContext(), "texto.json");
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

                dadosLB.appendData(new DataPoint(i, city.getInt("LB")) , false , 10 );


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
        return view;
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
