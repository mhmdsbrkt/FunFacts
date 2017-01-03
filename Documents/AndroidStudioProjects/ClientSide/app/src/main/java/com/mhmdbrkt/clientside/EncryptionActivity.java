package com.mhmdbrkt.clientside;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EncryptionActivity extends AppCompatActivity {
    int [] fflArray;
    boolean oddflag=false;
    String encryptedMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);


        final String EnglishAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final String ArabicAlphabets = "اأإءآىبپتةثجچحخدذرزسشصضطظعغفڤقكلمنهوؤيئ٠١٢٣٤٥٦٧٨٩";
        final Typeface kawkabMono = Typeface.createFromAsset(getAssets(), "fonts/KawkabMono-Light.ttf");

        final Button decryptButton = (Button) findViewById(R.id.decryptBTN);
        final Button encryptButton = (Button) findViewById(R.id.EncryptBTN);

        final TextView tvPlain = (TextView) findViewById(R.id.tvPlaintext);
        final TextView tvKey = (TextView) findViewById(R.id.tvmatrixSize);

        final TextView tvCipherText = (TextView) findViewById(R.id.tvCipherText);
        final TextView tvKeyText = (TextView) findViewById(R.id.tvKeyPlain);
        final TextView tvMatrixText = (TextView) findViewById(R.id.tvMatrix);
        final TextView tvResult = (TextView) findViewById(R.id.result);

        final EditText etPlainText = (EditText) findViewById(R.id.etPlaintext);
        final EditText etkey = (EditText) findViewById(R.id.etKey);

        final Button sendBTN = (Button) findViewById(R.id.MtarixTogBTN);



        final int row = getIntent().getIntExtra("row", 0);
        final int col = getIntent().getIntExtra("col", 0);
        final int matrixSize = getIntent().getIntExtra("Size", 0);
        final String Alphabet = getIntent().getStringExtra("Alphabets");
        final char fillerLetter = getIntent().getCharExtra("fLetter", '@');
        final int ip = getIntent().getIntExtra("ip", 0);
        final int port = getIntent().getIntExtra("port", 0);



        if (Alphabet.equalsIgnoreCase(EnglishAlphabets)) {

            tvPlain.setText("Message");
            etPlainText.setHint("English Letters & Numbers Only");

            tvKey.setText("Encryption Key");
            etkey.setHint("English Letters & Numbers Only");

            decryptButton.setText("Decrypt");
            encryptButton.setText("Encrypt");
            tvResult.setText("The Result");

            sendBTN.setText("Sned");

        } else if (Alphabet.equalsIgnoreCase(ArabicAlphabets)) {


            tvPlain.setText("الرسالة");
            etPlainText.setHint("فقط حروف وأرقام عربية");

            tvKey.setText("مفتاح التشفير");
            etkey.setHint("فقط حروف وأرقام عربية");

            decryptButton.setText("فك التشفير");
            encryptButton.setText("تشفير");
            tvResult.setText("النتيجة");

            sendBTN.setText("إرسال");

        } else {

            etPlainText.setHint("");
            etkey.setHint("");


        }


        //
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Validator = etPlainText.getText().toString().toUpperCase() + etkey.getText().toString().toUpperCase();
                Validator = Validator.replaceAll(" ", "");

                boolean isValid = true;
                for (int i = 0; i < Validator.length(); i++) {
                    String validLetter = Character.toString(Validator.charAt(i));
                    if (Alphabet.indexOf(validLetter) == -1) {
                        isValid = false;
                        break;

                    }

                }

                if (isValid == false) {

                    Toast.makeText(getApplicationContext(), "Invalid Letters", Toast.LENGTH_SHORT).show();
                    tvCipherText.setText("");
                    tvKeyText.setText("");
                    tvMatrixText.setText("");


                } else if (etPlainText.length() == 0) {

                    Toast.makeText(getApplicationContext(), "Plaintext is missing", Toast.LENGTH_SHORT).show();

                } else if (etkey.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Key is missing", Toast.LENGTH_SHORT).show();


                } else {

                    tvResult.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvResult.setText("Encrypted");


                    String key = etkey.getText().toString();
                    String text = etPlainText.getText().toString();

                    final PlyfairCipher Encryption = new PlyfairCipher(Alphabet, col, row, fillerLetter, text, key);

                    String cipherText = Encryption.encrypt(Encryption.getMatrix(), Encryption.getPlainText());
                    tvCipherText.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvCipherText.setText(cipherText);
                    final String cipher = cipherText;

                    fflArray=Encryption.getFllArray();
                    oddflag=Encryption.isOddFlag();
                    encryptedMessage=cipherText;

//                     Hide Keyboard After clicking Button


                }


            }
        });


        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String Validator = etPlainText.getText().toString().toUpperCase() + etkey.getText().toString().toUpperCase();
                Validator = Validator.replaceAll(" ", "");
                boolean isValid = true;

                for (int i = 0; i < Validator.length(); i++) {
                    String validLetter = Character.toString(Validator.charAt(i));
                    if (Alphabet.indexOf(validLetter) == -1) {
                        isValid = false;
                        break;

                    }

                }

                if (isValid == false) {

                    Toast.makeText(getApplicationContext(), "Invalid Letters", Toast.LENGTH_SHORT).show();
                    tvCipherText.setText("");
                    tvCipherText.setText("");
                    tvKeyText.setText("");
                    tvMatrixText.setText("");


                } else if (etPlainText.length() == 0) {

                    Toast.makeText(getApplicationContext(), "Plaintext is missing", Toast.LENGTH_SHORT).show();

                } else if (etkey.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Key is missing", Toast.LENGTH_SHORT).show();


                } else {

                    tvResult.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    tvResult.setText("Decrypted");

                    String key = etkey.getText().toString();
                    String text = etPlainText.getText().toString();

                    final PlyfairCipher Decryption = new PlyfairCipher(Alphabet, col, row, fillerLetter, text, key);
                    Decryption.setFllArray(fflArray);
                    Decryption.setOddFlag(oddflag);
                    String cipherText = Decryption.decrypt(Decryption.getMatrix(), Decryption.getPlainText());
                    tvCipherText.setTextColor(getResources().getColor(R.color.colorAccent));
                    tvCipherText.setText(cipherText);
                    final String cipher = cipherText;


//                     Hide Keyboard After clicking Button


                }


            }
        });


        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tMsg = encryptedMessage;
                String IP = Integer.toString(ip);
                int PORT = port;

//                if(tMsg.equals("")){
//                    tMsg = null;
//                    Toast.makeText(getApplicationContext(), "No Welcome Msg sent", Toast.LENGTH_SHORT).show();
//                }

                MyClientTask myClientTask = new MyClientTask(IP,PORT,tMsg);
                myClientTask.execute();
            }
        });



    }
}
