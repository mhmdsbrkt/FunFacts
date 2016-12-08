package com.example.mhmd.playfairencryption;


import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


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
        final ToggleButton matrixToggle = (ToggleButton) findViewById(R.id.MtarixTogBTN);
        final Typeface kawkabMono = Typeface.createFromAsset(getAssets(), "fonts/KawkabMono-Light.ttf");
        tvMatrixText.setTypeface(kawkabMono);


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
                    final PlayFair english = new PlayFair(englishAlphabets,text,key);

                    matrixToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                // The toggle is enabled

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

//                        tvKeyText.setText("Plaintext:\t"+printedText+"\nFinal Key:\t"+printedKey);
                        tvMatrixText.setText(printedMatrixString);
                            } else {
                                tvKeyText.setText("");
                                tvMatrixText.setText("");
                            }
                        }
                    });



                    String cipherText = english.encrypt(english.getMatrix(),english.getPlainText());
                    tvCipherText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
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

                    final PlayFair english = new PlayFair(englishAlphabets,text,key);
                    matrixToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                // The toggle is enabled

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

//                    tvKeyText.setText("Plaintext:\t"+printedText+"\nFinal Key:\t"+printedKey);
                    tvMatrixText.setText(printedMatrixString);
                            } else {
                                tvKeyText.setText("");
                                tvMatrixText.setText("");
                            }
                        }
                    });



                    String cipherText = english.decrypt(english.getMatrix(),english.getPlainText());
                    tvCipherText.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvCipherText.setText(cipherText);

                    // Hide Keyboard After clicking Encrypt
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                }

            }
        });

        matrixToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String englishAlphabets = getIntent().getStringExtra("Alphabets");

                if (isChecked) {


                    StringBuilder printedMatrixString = new StringBuilder();
                    int counter =0;

                    for (int i = 0; i < 6; i++) {

                        for (int j = 0; j < 6; j++) {
                            printedMatrixString.append(englishAlphabets.charAt(counter)+"\t\t\t");
                            counter++;

                        }
                        printedMatrixString.append("\n");


                    }

                    tvMatrixText.setText(printedMatrixString);
                } else {
                    tvKeyText.setText("");
                    tvMatrixText.setText("");
                }

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });


    }

}
