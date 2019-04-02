package br.com.galaxyware.androidcar;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class TabActivity extends AppCompatActivity {

    private AdapterTab mSectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mSectionAdapter = new AdapterTab(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        AdapterTab adapter = new AdapterTab(getSupportFragmentManager());
        adapter.addFragment(new FragLeft(), "Left");
        adapter.addFragment(new FragRight(), "Right");

        viewPager.setAdapter(adapter);
    }

}
