package com.example.craig.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class main_Menu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        addListenerOnButton();
    }


    public void addListenerOnButton() {
        final Context context = this;

        Button HostName = (Button) findViewById(R.id.btnHostGame);
        HostName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, playCards.class);
                intent.putExtra("ACTION","HOST");
                intent.putExtra("HOSTNAME", getLocalIpAddress());
                startActivity(intent);
                finish();
            }
        });
        Button JoinGame = (Button) findViewById(R.id.btnJoinGame);
        JoinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, join_game.class);
                startActivity(intent);
                finish();

            }
        });
    }

    public String getLocalIpAddress(){
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        String IP = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return IP;
    }
}