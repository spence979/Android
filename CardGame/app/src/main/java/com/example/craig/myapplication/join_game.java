package com.example.craig.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class join_game extends Activity {
    private static EditText HostName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_game);


        HostName = (EditText)findViewById(R.id.txtIPAddr);

        Button myButton = (Button)findViewById(R.id.btnConnectToGame);
        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), playCards.class);
                intent.putExtra("ACTION","JOIN");
                intent.putExtra("HOSTNAME", HostName.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}

