package com.example.bluetoothsilence;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class OnOffActivity extends AppCompatActivity {
    private final String DEVICE_ADDRESS = "98:D3:21:F7:4E:7C"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    String command; //string variable that will store value to be transmitted to the bluetooth module

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_off);
        if (BTinit()) {
            BTconnect();
        }
        final Button on_off_button = findViewById(R.id.buttonOnOff);
        final ImageView img = (ImageView) findViewById(R.id.imageView);
        if (GlobalClass.onOffButtonB) {
            on_off_button.setBackgroundColor(Color.parseColor("#03DA15"));
            img.setColorFilter(getResources().getColor(R.color.green));
        } else {
            on_off_button.setBackgroundColor(Color.parseColor("#DA0335"));
            img.setColorFilter(getResources().getColor(R.color.red));
        }
        on_off_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalClass.onOffButtonB) {
                    GlobalClass.onOffButtonB = false;
                    command = "F";
                    try {
                        socket.getOutputStream().write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(OnOffActivity.this, "Turning OFF!", Toast.LENGTH_SHORT).show();
                    on_off_button.setBackgroundColor(Color.parseColor("#DA0335"));
                    img.setColorFilter(v.getContext().getResources().getColor(R.color.red));
                } else {
                    GlobalClass.onOffButtonB = true;
                    command = "N";
                    try {
                        socket.getOutputStream().write(command.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(OnOffActivity.this, "Turning ON!", Toast.LENGTH_SHORT).show();
                    on_off_button.setBackgroundColor(Color.parseColor("#03DA15"));
                    img.setColorFilter(v.getContext().getResources().getColor(R.color.green));
                }

            }
        });


    }//onCreate

    public boolean BTinit() {
        boolean found = false;

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) //Checks if the device supports bluetooth
        {
            Toast.makeText(getApplicationContext(), "Device doesn't support bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()) //Checks if bluetooth is enabled. If not, the program will ask permission from the user to enable it
        {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

        if (bondedDevices.isEmpty()) //Checks for paired bluetooth devices
        {
            Toast.makeText(getApplicationContext(), "Please pair the device first", Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : bondedDevices) {
                if (iterator.getAddress().equals(DEVICE_ADDRESS)) {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }

        return found;
    }

    public boolean BTconnect() {
        boolean connected = true;

        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID); //Creates a socket to handle the outgoing connection
            socket.connect();

            Toast.makeText(getApplicationContext(),
                    "Connection to bluetooth device successful", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }

        if (connected) {
            try {
                outputStream = socket.getOutputStream(); //gets the output stream of the socket
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return connected;
    }
}
