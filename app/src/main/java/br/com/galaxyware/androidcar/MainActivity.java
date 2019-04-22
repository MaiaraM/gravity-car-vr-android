package br.com.galaxyware.androidcar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private static String TAG = "MainActivity";

    private AdapterTab mSectionAdapter;
    private TextView textPower;
    private final static int REQUEST_ENABLE_BT = 1;
    private static Map<String, String> mDevices;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final UUID MY_UUID = UUID.randomUUID();
    private BluetoothSocket mmSocket = null;
    private BluetoothDevice mmDevice = null;
    public static final int STATE_CONNECTING = 1; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 2; // now connected to a remote device

    private InputStream mmInStream = null;
    private OutputStream mmOutStream = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        mDevices = new HashMap<>();

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


    private void setupViewPager(ViewPager viewPager) {
        AdapterTab adapter = new AdapterTab(getSupportFragmentManager());
        adapter.addFragment(new FragLeft(), "Left Brake");
        adapter.addFragment(new FragRight(), "Right Brake");
        adapter.addFragment(new SteeringAngle(), "Steering Angle");
        adapter.addFragment(new Messages(), "Option");


        viewPager.setAdapter(adapter);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
            textPower.setText("Connect");
            connectArduino();
        } else {
            textPower.setText("Desconnect");
            Toast.makeText(MainActivity.this, "OFF", Toast.LENGTH_SHORT).show();
        }


    }

    private void connectArduino() {

        String a = "";
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "Device don't have support for bluetooth");
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    // Add the name and address to an array adapter to show in a ListView
                    mDevices.put(device.getName(), device.getAddress());
                    a += device.getName() + "/";
                    conectar(device);
                }
                Toast.makeText(MainActivity.this, a, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void conectar(BluetoothDevice device){
        String address = device.getAddress();
// Obtem o BluetoothDevice
        mmDevice = mBluetoothAdapter.getRemoteDevice(address);
        try {
// Cria o socket utilizando o UUID
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
// Conecta ao dispositivo escolhido
            mmSocket.connect();
// Obtem os fluxos de entrada e saida que lidam com transmissões através do socket
            mmInStream = mmSocket.getInputStream();
            mmOutStream = mmSocket.getOutputStream();

// Saida:
// Envio de uma mensagem pelo .write
//            String enviada = "Teste Rone";
//            byte[] send = enviada.getBytes();
//            mmOutStream.write(send);

// Entrada:
// bytes returnados da read()
            int bytes;
// buffer de memória para o fluxo
            byte[] read = new byte[1024];

// Continuar ouvindo o InputStream enquanto conectado
// O loop principal é dedicado a leitura do InputStream
            while (true) {
                try {
// Read from the InputStream
                    bytes = mmInStream.read(read);

                    String readMessage = new String(read);
                    Toast.makeText(this, readMessage, Toast.LENGTH_LONG).show();
                    Log.i("Mensagem:" ,readMessage );

                } catch (IOException e) {
                    Toast.makeText(this, "Ocorreu um erro no recebimento da mensagem!", Toast.LENGTH_LONG).show();
                }
            }

        }
        catch(IOException e){
            Toast.makeText(this, "Ocorreu um erro!", Toast.LENGTH_LONG).show();
        }
    }

}
