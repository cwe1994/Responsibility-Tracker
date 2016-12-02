package com.example.andrew.radar;

import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import java.util.*;
import java.lang.*;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> pairedDevices = BTAdapter.getBondedDevices();
    Vector vec_RSSI = new Vector();
    Vector vec_name = new Vector();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                if (BTAdapter.isDiscovering())
                {
                    BTAdapter.cancelDiscovery();
                }
                BTAdapter.startDiscovery();
                TextView rssi_msg = (TextView) findViewById(R.id.textView1);
                rssi_msg.setText("No devices found");

            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {

            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                if (BTAdapter.isDiscovering())
                {
                    BTAdapter.cancelDiscovery();
                }
                TextView vec_msg = (TextView) findViewById(R.id.textView3);

                if (vec_RSSI.size() >= 1)
                {
                    for (int i = 0; i <= vec_RSSI.size(); i++)
                    {
                        //Object o = vec_RSSI.get(i);
                        //Object o2 = vec_name.get(i);
                        //vec_msg.setText(vec_msg.getText()+ " "  + o2 + " " + o + " => dBm \n");
                    }
                }
                else
                {
                    vec_msg.setText("VECTORS ARE EMPTY!!!");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent)
        {

            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action))
            {

                TextView rssi_msg = (TextView) findViewById(R.id.textView1);
                rssi_msg.setText("Searching!");

                TextView vec_msg = (TextView) findViewById(R.id.textView3);
                vec_msg.setText("Getting new data!");

                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                vec_RSSI.addElement(rssi);


                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                vec_name.addElement(name);

                if (vec_name.size()>=2)
                {
                    for(int i = 0; i<vec_name.size(); i++)
                    {
                        if (vec_name.get(i) == vec_name.lastElement())
                        {
                            vec_name.removeElement(i);
                            vec_RSSI.removeElement(i);
                        }
                    }
                }
                rssi_msg.setText(name + " " + rssi + "dBm\n");

            }
            else
            {
                TextView rssi_msg = (TextView) findViewById(R.id.textView1);
                rssi_msg.setText("No devices found");
            }
        }
    };
}
