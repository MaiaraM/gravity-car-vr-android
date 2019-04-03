package br.com.galaxyware.androidcar;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindArray;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private AdapterTab mSectionAdapter;
    private TextView textPower;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);

        mSectionAdapter = new AdapterTab(getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        setSupportActionBar(toolbar);


        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.menu_switch);
        actionBar.setTitle("GravityCar Telemetry");
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);


        Switch button = findViewById(R.id.switchAB);
        textPower = findViewById(R.id.textPower);
        button.setOnCheckedChangeListener(this);


    }


    private void setupViewPager(ViewPager viewPager){
        AdapterTab adapter = new AdapterTab(getSupportFragmentManager());
        adapter.addFragment(new FragLeft(), "Left Brake");
        adapter.addFragment(new FragRight(), "Right Brake");
        adapter.addFragment(new SteeringAngle(), "Steering Angle");
        adapter.addFragment(new Messages(), "Option");


        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            textPower.setText("Conectado");
            Toast.makeText(MainActivity.this, "ON", Toast.LENGTH_SHORT).show();
        }else{
            textPower.setText("Desconectado");
            Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
        }


    }
}
