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
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class OnOffActivity extends MainActivity {
    private final String DEVICE_ADDRESS = "98:D3:21:F7:4E:7C"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    public static InputStream inputStream;
    String command; //string variable that will store value to be transmitted to the bluetooth module
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_off);
        if (BTinit()) {
            System.out.println("Verifying the BTconnect()");
            BTconnect();
        }
        MainActivity.active = true;
        final Button on_off_button = findViewById(R.id.buttonOnOff);
        readingData();

        if (GlobalClass.onOffButtonB) {
            on_off_button.setBackgroundColor(Color.parseColor("#03DA15"));
        } else {
            on_off_button.setBackgroundColor(Color.parseColor("#DA0335"));
        }

        on_off_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalClass.onOffButtonB) {
                    GlobalClass.onOffButtonB = false;
                    command = "F";
                    try {
                        socket.getOutputStream().write(command.getBytes());

                        System.out.println("Verifying command = F");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(OnOffActivity.this, "Turning OFF!", Toast.LENGTH_SHORT).show();
                    on_off_button.setBackgroundColor(Color.parseColor("#DA0335"));
                    //img.setColorFilter(v.getContext().getResources().getColor(R.color.red));
                } else {
                    GlobalClass.onOffButtonB = true;
                    command = "N";
                    try {
                        socket.getOutputStream().write(command.getBytes());
                        System.out.println("Verifying command = N");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(OnOffActivity.this, "Turning ON!", Toast.LENGTH_SHORT).show();
                    on_off_button.setBackgroundColor(Color.parseColor("#03DA15"));
                    // img.setColorFilter(v.getContext().getResources().getColor(R.color.green));
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

    public void readingData() {

        final ImageView mImg = (ImageView) findViewById(R.id.imageView);
        mImg.setColorFilter(getResources().getColor(R.color.grey));
        mTextView = (TextView)findViewById(R.id.textView);

        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = inputStream.available();
                        if (bytesAvailable > 0) {
                            if (!MainActivity.active) {
                                inputStream.close();
                            }
                            byte[] packetBytes = new byte[bytesAvailable];
                            inputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        public void run() {

                                        }
                                    });
                                    System.out.println("Verifying the Data: " + data);

                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                    if(b==49){
                                        mImg.setColorFilter(getResources().getColor(R.color.green));
                                    }else if (b==48){
                                        mImg.setColorFilter(getResources().getColor(R.color.red));
                                    }

                                }
                            }
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }

            }
        });

        workerThread.start();


    }


}
