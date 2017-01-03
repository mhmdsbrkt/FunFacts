package com.mhmdbrkt.clientside;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        final Button construct = (Button) findViewById(R.id.constructBTN);
        final EditText alphabet = (EditText) findViewById(R.id.etAlphabets);
        final EditText cSize = (EditText) findViewById(R.id.etCSize);
        final EditText rSize = (EditText) findViewById(R.id.etRSize);
        final EditText filler = (EditText) findViewById(R.id.etFillerLetter);


        construct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), EncryptionActivity.class);
                String alpha = alphabet.getText().toString().toUpperCase();
                alpha = alpha.replaceAll(" ", "");
                String fillerLetter = filler.getText().toString().toUpperCase();


                String row = rSize.getText().toString();
                String col = cSize.getText().toString();
                int matrixSize = 0;

                try {
                    int rowNo = 0;
                    rowNo = Integer.parseInt(row);
                    int colNo = 0;
                    colNo = Integer.parseInt(col);
                    matrixSize = colNo * rowNo;

                    if (rowNo != colNo) {
                        Toast.makeText(getApplicationContext(), "Row Number must be equal to Column Number", Toast.LENGTH_SHORT).show();
                    } else if (colNo < 2 || rowNo < 2) {
                        Toast.makeText(getApplicationContext(), "Minimum Matrix Size 4 i.e 2X2", Toast.LENGTH_SHORT).show();
                    } else if ((colNo * rowNo) != alpha.length()) {
                        Toast.makeText(getApplicationContext(), "Alphabet Length must be = Row*Column", Toast.LENGTH_SHORT).show();
                    } else if (alpha.indexOf(fillerLetter) == -1) {
                        Toast.makeText(getApplicationContext(), "Invalid Filler Letter", Toast.LENGTH_SHORT).show();
                    } else if (filler.getText().toString().length() > 1) {
                        Toast.makeText(getApplicationContext(), "Only one Filler Letter is Allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        char fletter = fillerLetter.charAt(0);
                        i.putExtra("Alphabets", alpha);
                        i.putExtra("col", (colNo));
                        i.putExtra("row", (rowNo));
                        i.putExtra("Size", (matrixSize));
                        i.putExtra("fLetter", fletter);
                        startActivity(i);
                    }
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Please Enter valid Number", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
