package com.example.bluetoothsilence;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;

    private final String DEVICE_ADDRESS = "98:D3:21:F7:4E:7C"; //MAC Address of Bluetooth Module
    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueIv;
    Button mOnBtn, mOffBtn, mStartAppBtn, mPairedBtn;
    BluetoothAdapter mBlueAdapter;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusBlueTv = findViewById(R.id.statusBluetoothTv);
        mPairedTv = findViewById(R.id.pairedTv);
        mBlueIv = findViewById(R.id.bluetoothIv);
        mOnBtn = findViewById(R.id.onBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mStartAppBtn = findViewById(R.id.startAppBtn);
        mPairedBtn = findViewById(R.id.pairedBtn);
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        final BluetoothDevice hc05_Oussama = btAdapter.getRemoteDevice("98:D3:21:F7:4E:7C");
        final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        final boolean[] connected = new boolean[1];
        //********************************************************
        BluetoothSocket btSocket = null;
        try {
            btSocket = hc05_Oussama.createRfcommSocketToServiceRecord(mUUID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final BluetoothSocket finalBtSocket = btSocket;
        //********************************************************


        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        // check if bluetooth is available
        if (mBlueAdapter == null) {
            mStatusBlueTv.setText("Bluetooth makaynch");
        } else {
            mStatusBlueTv.setText("Bluetooth Kayn");
        }

        // Set image of bluetooth according to bluetooth status
        if (mBlueAdapter.isEnabled()) {
            mBlueIv.setImageResource(R.drawable.ic_action_name);
        } else {

            mBlueIv.setImageResource(R.drawable.ic_action_off);
        }
        //onBtn click
        mOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBlueAdapter.isEnabled()) {
                    showToast("Turning ON Bluetooth...");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);

                    if (connected[0] == false) {
                        int counter = 0; // try to connect the socket 3 times
                        do {
                            try {
                                finalBtSocket.connect();
                                connected[0] = true;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            counter++;
                        } while (!finalBtSocket.isConnected() && counter < 3);

                    }

                } else {
                    showToast("Bluetooth is already ON");
                }

            }
        });//mOnBtn.setOn..

        //offBtn click
        mOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()) {
                    mBlueAdapter.disable();
                    showToast("Turning Bluetooth OFF");
                    mBlueIv.setImageResource(R.drawable.ic_action_off);
                    // mBlueIv.setColorFilter(R.color.red);
                } else {
                    showToast("Bluetooth is already OFF");
                }
            }
        });//mOffBtn.setOn..

        //StartApp bluetooth
        mStartAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()) {
                    openActivity();
                } else {
                    showToast("You have to turn ON bluetooth first");
                }

            }
        });//mDiscoverBtn.setOn..

        //Paired Btn
        mPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBlueAdapter.isEnabled()) {
                    mPairedTv.setText("Paired Devices");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for (BluetoothDevice device : devices) {
                        mPairedTv.append("\nDevice: " + device.getName() + " , " + device);
                    }
                } else {
                    showToast("Turn ON Bluetooth to get paired devices.");
                }

            }
        });//mPairedBtn.setOn..

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
