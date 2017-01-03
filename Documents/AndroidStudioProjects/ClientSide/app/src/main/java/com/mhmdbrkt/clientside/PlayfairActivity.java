package com.mhmdbrkt.clientside;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PlayfairActivity extends AppCompatActivity {

    TextView msg, infoip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfair);
        final Button englishStart = (Button) findViewById(R.id.englishBTN);
        final Button arabicStart = (Button) findViewById(R.id.arabicBTN);
        final Button mixStart = (Button) findViewById(R.id.mixBTN);
        final Button customStart = (Button) findViewById(R.id.customBTN);




        englishStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EncryptionActivity.class);
                final int ip = getIntent().getIntExtra("ip", 0);
                final int port = getIntent().getIntExtra("port", 0);
                i.putExtra("Alphabets", "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
                i.putExtra("col", (6));
                i.putExtra("row", (6));
                i.putExtra("Size", (36));
                i.putExtra("fLetter", 'X');
                i.putExtra("ip", ip);
                i.putExtra("port", port);

                startActivity(i);

            }
        });


        arabicStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EncryptionActivity.class);
                i.putExtra("Alphabets", "اأإءآىبپتةثجچحخدذرزسشصضطظعغفڤقكلمنهوؤيئ٠١٢٣٤٥٦٧٨٩");
                i.putExtra("col", (7));
                i.putExtra("row", (7));
                i.putExtra("Size", (49));
                i.putExtra("fLetter", 'ظ');

                startActivity(i);

            }
        });


        mixStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EncryptionActivity.class);
                i.putExtra("Alphabets", "ABCDEFGHIJKLMNOPQRSTUVWXYZابتثجحخدذرزسشصضطظعغفقكلمنهوي0123456789");
                i.putExtra("col", (8));
                i.putExtra("row", (8));
                i.putExtra("Size", (64));
                i.putExtra("fLetter", 'Z');

                startActivity(i);

            }
        });

        customStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CustomActivity.class);
                startActivity(i);

            }
        });


    }





    }

