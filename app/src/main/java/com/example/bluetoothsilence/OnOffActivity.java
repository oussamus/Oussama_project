package com.example.bluetoothsilence;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class OnOffActivity extends MainActivity {
    private static final String TAG = "BluetoothActivity";
    private final String DEVICE_ADDRESS = "98:D3:21:F7:4E:7C"; //MAC Address of Bluetooth Module
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public static BluetoothDevice hc05_device;
    public static BluetoothSocket socket = null;
    private OutputStream outputStream;
    public static InputStream inputStream;
    String command; //string variable that will store the value transmitted to the bluetooth module
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
   // This comment is for testing a Git functionality.

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_off);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        hc05_device = bluetoothAdapter.getRemoteDevice(DEVICE_ADDRESS);

        final Button connectBtn = findViewById(R.id.ConnectBtn2);
        final Button light_button = findViewById(R.id.light_btn);
        final Button M5_right = (Button) findViewById(R.id.M5_right_btn);
        final Button M5_left = (Button) findViewById(R.id.M5_left_btn);
        final Button M4_up = (Button) findViewById(R.id.M4_up_btn);
        final Button M4_down = (Button) findViewById(R.id.M4_down_btn);
        final Button M3_up = (Button) findViewById(R.id.M3_up_btn);
        final Button M3_down = (Button) findViewById(R.id.M3_down_btn);
        final Button M2_up = (Button) findViewById(R.id.M2_up_btn);
        final Button M2_down = (Button) findViewById(R.id.M2_down_btn);
        final Button M1_open = (Button) findViewById(R.id.M1_open_btn);
        final Button M1_close = (Button) findViewById(R.id.M1_close_btn);
        readingData();
        //************************************************
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;

                    do{
                        try {
                            socket = hc05_device.createRfcommSocketToServiceRecord(PORT_UUID);
                            if(!socket.isConnected()) {
                                socket.connect();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        count++;
                    }while(count<3);

                System.out.println("Is Socket from OnOffActivity connected? " + socket.isConnected());
                if (socket.isConnected()) {
                    try {
                        outputStream = socket.getOutputStream(); //gets the output stream of the socket
                        inputStream = socket.getInputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        M1_open.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "a";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });
        M1_close.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "b";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });
        M2_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "c";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });
        M2_down.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "d";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });
        M3_up.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "e";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });
        M3_down.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "f";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });
        M4_up.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "g";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });
        M4_down.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "h";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });
        M5_left.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "i";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });
        M5_right.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN && outputStream != null) //MotionEvent.ACTION_DOWN is when you hold a button down
                {
                    command = "j";

                    try
                    {
                        outputStream.write(command.getBytes()); //transmits the value of command to the bluetooth module
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && outputStream != null) //MotionEvent.ACTION_UP is when you release a button
                {
                    command = "..";
                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });


        //*************************************************
        if (GlobalClass.onOffButtonB) {
            light_button.setBackgroundColor(Color.parseColor("#03DA15"));
        } else {
            light_button.setBackgroundColor(Color.parseColor("#DA0335"));
        }
        light_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(outputStream != null){
                    if (GlobalClass.onOffButtonB) {
                        GlobalClass.onOffButtonB = false;
                        command = "F";
                        try {
                            socket.getOutputStream().write(command.getBytes());

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(OnOffActivity.this, "Turning OFF!", Toast.LENGTH_SHORT).show();
                        light_button.setBackgroundColor(Color.parseColor("#DA0335"));
                        //img.setColorFilter(v.getContext().getResources().getColor(R.color.red));
                    } else {
                        GlobalClass.onOffButtonB = true;
                        command = "N";
                        try {
                            socket.getOutputStream().write(command.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(OnOffActivity.this, "Turning ON!", Toast.LENGTH_SHORT).show();
                        light_button.setBackgroundColor(Color.parseColor("#03DA15"));
                        // img.setColorFilter(v.getContext().getResources().getColor(R.color.green));
                    }
                }
            }
        });

    }//onCreate

    /*
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
 */
    public void readingData() {

        final ImageView mImg = (ImageView) findViewById(R.id.imageView);
        mImg.setColorFilter(getResources().getColor(R.color.grey));

        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    if (inputStream != null) {
                        try {
                            int bytesAvailable = inputStream.available();
                            if (bytesAvailable > 0) {
                                //if (!MainActivity.active) {
                                if (true) {
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
                                        if (b == 49) {
                                            mImg.setColorFilter(getResources().getColor(R.color.green));
                                        } else if (b == 48) {
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
            }
        });

        workerThread.start();


    }


}
