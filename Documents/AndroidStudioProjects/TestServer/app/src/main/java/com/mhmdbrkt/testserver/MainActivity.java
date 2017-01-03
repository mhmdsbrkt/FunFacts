package com.mhmdbrkt.testserver;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView info, infoip, msg, result;
    String message = "";
    ServerSocket serverSocket;
    EditText key;
    String messageFromClient = "";
    String Alphabet,CipherText;
    int col, row;
    String filler;
    char fletter;
    int [] finalFillerArray;
    int [] finalSpaceArray;
    boolean oddFlag;
    JSONObject jObj;
    List<String> spacesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);
        key= (EditText) findViewById(R.id.keyET);
        result = (TextView)findViewById(R.id.plainTextTv);
        Button decrypt = (Button) findViewById(R.id.decryptBTN);

        infoip.setText(getIpAddress());

        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();

        decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


                PlayfairCipher decrypt = new PlayfairCipher(Alphabet,col,row,fletter,messageFromClient,key.getText().toString());



                decrypt.setFllArray(finalFillerArray);
                decrypt.setSpaceArray(finalSpaceArray);
                decrypt.setOddFlag(oddFlag);
                String plaintetx = decrypt.decrypt(decrypt.getMatrix(),CipherText);

                result.setText(plaintetx);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread {

        static final int SocketServerPORT = 8080;
        int count = 0;

        @Override
        public void run() {
            Socket socket = null;
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;

            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() { info.setText("Port: " + serverSocket.getLocalPort());
                    }
                });

                while (true) {
                    socket = serverSocket.accept();
                    dataInputStream = new DataInputStream(
                            socket.getInputStream());
                    dataOutputStream = new DataOutputStream(
                            socket.getOutputStream());


                    //If no message sent from client, this code will block the program
                    messageFromClient = dataInputStream.readUTF();
                    jObj = new JSONObject(messageFromClient);


                    CipherText = jObj.getString("Cipher");
                    Alphabet = jObj.getString("Alpha");
                    col = jObj.getInt("col");
                    row = jObj.getInt("row");
                    String fArray = jObj.getString("Farray");
                    String Sarray = jObj.getString("Sarray");
                    filler = jObj.getString("fLetter");
                    oddFlag = jObj.getBoolean("oddFlag");
                    fletter = filler.charAt(0);

                   if (!fArray.isEmpty()){
                       List<String> fillerList = new ArrayList<>(Arrays.asList(fArray.split(",")));
                       String[] fillerListArray = new String[fillerList.size()];
                       fillerListArray = fillerList.toArray(fillerListArray);
                       finalFillerArray = new int[fillerListArray.length];

                       for (int i = 0; i <fillerListArray.length ; i++) {
                           finalFillerArray[i]=Integer.parseInt(fillerListArray[i]);
                       }
                   }

                   if (!Sarray.isEmpty()){
                       List<String> spacesList = new ArrayList<>(Arrays.asList(Sarray.split(",")));
                       String[] SpacesListArray = new String[spacesList.size()];
                       SpacesListArray = spacesList.toArray(SpacesListArray);
                       finalSpaceArray = new int[SpacesListArray.length];
                       for (int i = 0; i <SpacesListArray.length ; i++) {
                           finalSpaceArray[i]=Integer.parseInt(SpacesListArray[i]);
                       }
                   }



                    count++;
                    message += "#" + count + " From client: " + socket.getInetAddress() + "\n"
                            + "Massage: " + CipherText;

                    MainActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            msg.setText(message);
                        }
                    });

                    String msgReply = "Your "+count+" Message Received Successfully";
                    dataOutputStream.writeUTF(msgReply);

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                final String errMsg = e.toString();
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        msg.setText(errMsg);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += "IP Address: "
                                + inetAddress.getHostAddress() + "\n";
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }

        return ip;
    }



}
