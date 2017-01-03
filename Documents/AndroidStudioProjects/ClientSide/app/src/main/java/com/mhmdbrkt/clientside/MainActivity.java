package com.mhmdbrkt.clientside;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ip,port;
    Button connectBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip= (EditText)findViewById(R.id.IPeditText);
        port= (EditText)findViewById(R.id.PorteditText);
        connectBTN = (Button) findViewById(R.id.ConnectBTN);

        connectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String IP = ip.getText().toString();
                int portNo = Integer.parseInt(port.getText().toString());
                MyClientTask newTask = new MyClientTask(IP,portNo,"Client Connected");
                newTask.execute();
                Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), PlayfairActivity.class);
                i.putExtra("IP",IP);
                i.putExtra("Port",portNo);
                startActivity(i);

            }
        });


    }
}
