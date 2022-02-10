package com.example.bluetoothsilence;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BluetoothActivity";
    private static final int REQUEST_ENABLE_BT = 0;
    private final String DEVICE_ADDRESS = "98:D3:21:F7:4E:7C"; //MAC Address of Bluetooth Module
    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Button connectBtn, mStartAppBtn;

    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public static BluetoothDevice hc05_device;
    public static BluetoothSocket socket = null;
    private OutputStream outputStream;
    public static InputStream inputStream;
    String command;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairedTv);
        mBlueIv = findViewById(R.id.bluetoothIv);
        connectBtn = findViewById(R.id.connectBtn);
        mStartAppBtn = findViewById(R.id.startAppBtn);
        // check if bluetooth is available

/*
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        hc05_device = bluetoothAdapter.getRemoteDevice(DEVICE_ADDRESS);

        int count = 0;
        do{
            try {
                socket = hc05_device.createRfcommSocketToServiceRecord(PORT_UUID);
                socket.connect();
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
*/
        //onBtn click
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(outputStream != null){
                    if (GlobalClass.onOffButtonB) {
                        GlobalClass.onOffButtonB = false;
                        try {
                            command = "F";
                            socket.getOutputStream().write(command.getBytes());
                            System.out.println("socket.getOutputStream() "+socket.getOutputStream());
                            System.out.println("Verifying command = F "+command);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, "Turning OFF!", Toast.LENGTH_SHORT).show();
                        connectBtn.setBackgroundColor(Color.parseColor("#DA0335"));
                        //img.setColorFilter(v.getContext().getResources().getColor(R.color.red));
                    } else {
                        GlobalClass.onOffButtonB = true;
                        try {
                            command = "N";
                            socket.getOutputStream().write(command.getBytes());
                            System.out.println("Verifying command = N "+command);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(MainActivity.this, "Turning ON!", Toast.LENGTH_SHORT).show();
                        connectBtn.setBackgroundColor(Color.parseColor("#03DA15"));
                        // img.setColorFilter(v.getContext().getResources().getColor(R.color.green));
                    }
                }

                 */
            }

        });//mOnBtn.setOn..

mStartAppBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openActivity();
    }

});



    } // onCreate()




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    mBlueIv.setImageResource(R.drawable.ic_action_name);
                    showToast("Bluetooth is ON");
                } else {
                    //mBlueIv.setImageResource(R.drawable.ic_action_off);
                    showToast("Cannot turn ON Bluetooth");
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void openActivity() {
        Intent intent = new Intent(this, OnOffActivity.class);
        startActivity(intent);
    }
}
