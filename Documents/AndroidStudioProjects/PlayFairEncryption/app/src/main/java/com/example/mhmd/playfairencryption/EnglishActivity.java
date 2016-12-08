package com.example.mhmd.playfairencryption;


import android.content.Context;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EnglishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.english_activity);


        final Button decryptButton = (Button) findViewById(R.id.decryptBTN);
        final Button encryptButton = (Button) findViewById(R.id.EncryptBTN);
        final TextView tvCipherText = (TextView) findViewById(R.id.tvCipherText);
        final TextView tvKeyText = (TextView) findViewById(R.id.tvKeyPlain);
        final TextView tvMatrixText = (TextView) findViewById(R.id.tvMatrix);
        final EditText etPlainText = (EditText) findViewById(R.id.etPlaintext);
        final EditText etkey = (EditText)findViewById(R.id.etKey);
        final TextView tvResult = (TextView) findViewById(R.id.result);



        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etPlainText.length()==0 ) {

                    Toast.makeText(getApplicationContext(), "Plaintext is missing", Toast.LENGTH_SHORT).show();

                } else if (etkey.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Key is missing", Toast.LENGTH_SHORT).show();


                } else {

                    tvResult.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvResult.setText("Encrypted");

                    String key = etkey.getText().toString();
                    String text= etPlainText.getText().toString();
                    String englishAlphabets = getIntent().getStringExtra("Alphabets");
                    PlayFair english = new PlayFair(englishAlphabets,text,key);

                    // Tracing & print Start
                    String printedKey = english.getKey();
                    String printedText= english.getPlainText();
                    char[][] printedMatrix = english.getMatrix();
                    StringBuilder printedMatrixString = new StringBuilder();

                    for (int i = 0; i < 6; i++) {

                    for (int j = 0; j < 6; j++) {
                        printedMatrixString.append(printedMatrix[i][j]+"\t\t\t");

                        }
                        printedMatrixString.append("\n");


                    }

                    tvKeyText.setText("Plaintext:\t"+printedText+"\nFinal Key:\t"+printedKey);
                    tvMatrixText.setText(printedMatrixString);
                    // Tracing & print End

                    String cipherText = english.encrypt(english.getMatrix(),english.getPlainText());
                    tvCipherText.setText(cipherText);

//                     Hide Keyboard After clicking Encrypt
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                }


            }
        });


        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etPlainText.length()==0 ) {

                    Toast.makeText(getApplicationContext(), "Plaintext is missing", Toast.LENGTH_SHORT).show();

                } else if (etkey.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Key is missing", Toast.LENGTH_SHORT).show();


                } else {

                    tvResult.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvResult.setText("Decrypted");

                    String key = etkey.getText().toString();
                    String text= etPlainText.getText().toString();
                    String englishAlphabets = getIntent().getStringExtra("Alphabets");

                    PlayFair english = new PlayFair(englishAlphabets,text,key);

                    // Tracing & print Start
                    String printedKey = english.getKey();
                    String printedText= english.getPlainText();
                    char[][] printedMatrix = english.getMatrix();
                    StringBuilder printedMatrixString = new StringBuilder();

                    for (int i = 0; i < 6; i++) {

                        for (int j = 0; j < 6; j++) {
                            printedMatrixString.append(printedMatrix[i][j]+"\t\t\t");

                        }
                        printedMatrixString.append("\n");


                    }

                    tvKeyText.setText("Plaintext:\t"+printedText+"\nFinal Key:\t"+printedKey);
                    tvMatrixText.setText(printedMatrixString);
                    // Tracing & print End


                    String cipherText = english.decrypt(english.getMatrix(),english.getPlainText());
                    tvCipherText.setText(cipherText);

                    // Hide Keyboard After clicking Encrypt
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                }

            }
        });




    }

}
