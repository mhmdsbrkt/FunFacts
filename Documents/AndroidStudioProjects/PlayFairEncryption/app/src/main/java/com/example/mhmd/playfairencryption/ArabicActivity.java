package com.example.mhmd.playfairencryption;

import android.content.Context;
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

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ArabicActivity extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    protected void onCreate(Bundle savedInstanceState) {

        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("SourceSansPro-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arabic);


        final Button decryptButton = (Button) findViewById(R.id.decryptBTN);
        Button encryptButton = (Button) findViewById(R.id.EncryptBTN);

        final TextView tvCipherText = (TextView) findViewById(R.id.tvCipherText);
        final TextView tvKeyText = (TextView) findViewById(R.id.tvKeyPlain);
        final TextView tvMatrixText = (TextView) findViewById(R.id.tvMatrix);
        final EditText etPlainText = (EditText) findViewById(R.id.etPlaintext);
        final EditText etkey = (EditText) findViewById(R.id.etKey);
        final TextView tvResult = (TextView) findViewById(R.id.result);
        final ToggleButton matrixToggle = (ToggleButton) findViewById(R.id.MtarixTogBTN);
        final Typeface kawkabMono = Typeface.createFromAsset(getAssets(), "fonts/KawkabMono-Light.ttf");
        tvMatrixText.setTypeface(kawkabMono);


        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etPlainText.length() == 0) {

                    Toast.makeText(getApplicationContext(), "أدخل النص", Toast.LENGTH_SHORT).show();

                } else if (etkey.length() == 0) {
                    Toast.makeText(getApplicationContext(), "أدخل مفتاح التشفير", Toast.LENGTH_SHORT).show();


                } else {

                    tvResult.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvResult.setText("تشفير");

                    String key = etkey.getText().toString();
                    String text = etPlainText.getText().toString();
                    String alphabets = getIntent().getStringExtra("Alphabets");
                    ;

                    final PlayFair arabicEncrypt = new PlayFair(alphabets, text, key);


                    matrixToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                // The toggle is enabled

                                char[][] printedMatrix = arabicEncrypt.getMatrix();
                                StringBuilder printedMatrixString = new StringBuilder();

                                for (int i = 0; i < 7; i++) {
                                    printedMatrixString.append("صف"+(i+1)+":\t");

                                    for (int j = 0; j < 7; j++) {
                                        printedMatrixString.append("\t\t" + printedMatrix[i][j] + "\t\t");

                                    }
                                    printedMatrixString.append("\n");


                                }
                                tvMatrixText.setText(printedMatrixString);
                            } else {
                                tvKeyText.setText("");
                                tvMatrixText.setText("");
                            }
                        }
                    });


                    String cipherText = arabicEncrypt.encrypt(arabicEncrypt.getMatrix(), arabicEncrypt.getPlainText());
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


                if (etPlainText.length() == 0) {

                    Toast.makeText(getApplicationContext(), "أدخل النص", Toast.LENGTH_SHORT).show();

                } else if (etkey.length() == 0) {
                    Toast.makeText(getApplicationContext(), "أدخل مفتاح التشفير", Toast.LENGTH_SHORT).show();


                } else {

                    tvResult.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvResult.setText("فك التشفير");

                    String key = etkey.getText().toString();
                    String text = etPlainText.getText().toString();
                    String alphabets = getIntent().getStringExtra("Alphabets");
                    ;

                    final PlayFair arabicDecrypt = new PlayFair(alphabets, text, key);


                    matrixToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                // The toggle is enabled

                                char[][] printedMatrix = arabicDecrypt.getMatrix();
                                StringBuilder printedMatrixString = new StringBuilder();

                                for (int i = 0; i < 7; i++) {

                                    printedMatrixString.append("صف"+(i+1)+":\t");

                                    for (int j = 0; j < 7; j++) {

                                        printedMatrixString.append("\t\t" + printedMatrix[i][j] + "\t\t");

                                    }
                                    printedMatrixString.append("\n");


                                }

                                tvMatrixText.setText(printedMatrixString);

                            } else {
                                tvKeyText.setText("");
                                tvMatrixText.setText("");

                            }
                        }
                    });


                    String cipherText = arabicDecrypt.decrypt(arabicDecrypt.getMatrix(), arabicDecrypt.getPlainText());
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

                    for (int i = 0; i < 7; i++) {
                        printedMatrixString.append("صف"+(i+1)+":\t");

                        for (int j = 0; j < 7; j++) {
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
