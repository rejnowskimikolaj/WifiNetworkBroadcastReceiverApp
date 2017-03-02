package com.example.rent.wifinetworkbroadcastreceiverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView wifiStatusTextView;
    WifiStatusBroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiStatusTextView = (TextView) findViewById(R.id.wifiStatus_textView);
        receiver = new WifiStatusBroadcastReceiver();
        checkWifiStatus();

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(receiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    private class WifiStatusBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            checkWifiStatus();
        }
    }

    private void checkWifiStatus() {

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                 activeNetwork.getType()==ConnectivityManager.TYPE_WIFI;

        if(isConnected) wifiStatusTextView.setText("Connected");
        else wifiStatusTextView.setText("Disconnected");
    }
}
