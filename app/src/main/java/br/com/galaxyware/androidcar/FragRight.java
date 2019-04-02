package br.com.galaxyware.androidcar;

import android.content.Context;
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

public class FragRight extends Fragment {
    private static final String TAG = "FragRight";

    @BindView(R.id.graph1)
    GraphView graphLB;
    @BindView(R.id.rbMin) TextInputEditText rbMin;
    @BindView(R.id.rbMax) TextInputEditText rbMax;
    @BindView(R.id.rbAv) TextInputEditText rbAv;
    @BindView(R.id.rbCur) TextInputEditText rbCur;
    @BindView(R.id.rbMA) TextInputEditText rbMA;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_right, container, false);
        LineGraphSeries<DataPoint> dadosRB = new LineGraphSeries<>();
        ButterKnife.bind(this,view);
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

                dadosRB.appendData(new DataPoint(i+1, city.getInt("RB")) , false , 10 );


                rbMin.setText("0");
                rbMax.setText("500");
                rbAv.setText("250");
                rbCur.setText("0");
                rbMA.setText("120");
            }


        }catch (Exception ex){
            Log.d("erro:", ex.getLocalizedMessage());
        }


        graphLB.addSeries(dadosRB);
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
