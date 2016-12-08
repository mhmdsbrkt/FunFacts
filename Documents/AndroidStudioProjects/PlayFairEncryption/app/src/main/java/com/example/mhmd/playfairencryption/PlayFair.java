package com.example.mhmd.playfairencryption;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by mhmd on 12/7/16.
 */

public class PlayFair {


    String alphabet = "";
    String plainText = "";
    String key = "";
    String finalMatrixString;
    char[][] Matrix;
    int mateixSize;
    Set<Character> matrixString = new LinkedHashSet<>();
    StringBuilder finalString = new StringBuilder();

    public PlayFair(String alphabet, String plainText, String key) {
        key=formatKey(key);
        this.alphabet = alphabet.toUpperCase();;
        this.plainText = formatText(plainText);
        this.key = key;
        finalMatrixString=finalString(key,alphabet).toString();
        Matrix=constructMatrix();



    }

    public  String formatKey(String Key) {
        Key = Key.toUpperCase();
        Key = Key.replaceAll(" ", "");

        LinkedHashSet<Character> formattedKeyList = new LinkedHashSet<>();
        for (int i = 0; i < Key.length(); i++) formattedKeyList.add(Key.charAt(i));
        StringBuilder formattedKey = new StringBuilder();
        for (Character character : formattedKeyList) {
            formattedKey.append(character);
        }
        Key = formattedKey.toString().toUpperCase();

        return Key;
    }

    public  String formatText(String Message) {
        Message = Message.toUpperCase();
        Message = Message.replaceAll(" ", "");

        if (Message.length() % 2 == 1) {
            if (alphabet.equalsIgnoreCase("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")){
                Message = Message + "X";

            } else if (alphabet.equals("اأإءآىبپتةثجچحخدذرزسشصضطظعغفڤقكلمنهوؤيئ٠١٢٣٤٥٦٧٨٩")){
                Message = Message + "ظ";

            } else {
                Message = Message + "Z";

            }

        }
        return Message;
    }

    public StringBuilder finalString(String key, String alphabets){

        char[] keyChars = key.toCharArray();
        char[] alphaChars = alphabets.toCharArray();

        for (char k : keyChars) {
            matrixString.add(k);
        }
        for (char a : alphaChars) {
            matrixString.add(a);
        }

        for (Character character : matrixString) {
            finalString.append(character);
        }

        return finalString;
    }

    public  char[][] constructMatrix() {

        if (alphabet.equalsIgnoreCase("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")){
            mateixSize=6;
        } else if (alphabet.equals("اأإءآىبپتةثجچحخدذرزسشصضطظعغفڤقكلمنهوؤيئ٠١٢٣٤٥٦٧٨٩")){
            mateixSize=7;
        } else {
            mateixSize=8;
        }
        char[][] finalMatrix = new char[mateixSize][mateixSize];
        int counter = 0;
        int StringLength = finalMatrixString.length();

        for (int row = 0; row < finalMatrix.length; row++) {

            for (int column = 0; column < finalMatrix[row].length; column++) {
                if (StringLength > 0) {
                    finalMatrix[row][column] = finalMatrixString.charAt(counter);
                    StringLength--;
                    counter++;
                }
            }
        }
        return finalMatrix;
    }

    public String getPlainText() {
        return plainText;
    }

    public String getKey() {
        return key;
    }

    public char[][] getMatrix() {
        return Matrix;
    }

    public String encrypt(char [][] matrix, String Plaintext){
        String Ciphertext="";

        int ptLength = Plaintext.length();


//
        for (int len = 0; len < ptLength-1; len=len+2) {

            char    firstChar=Plaintext.charAt(len);
            char    secondChar=Plaintext.charAt(len+1);
            int row1=0,row2=0,column1=0,column2=0;


            for(int column=0;column < matrix.length;column++) {

                for(int row=0;row < matrix[column].length;row++) {

                    if(matrix[column][row]==firstChar && matrix[column][row]==secondChar){
                        column1=column2=column;
                        row1=row2=row;

                    }
                    else if(matrix[column][row]==firstChar){
                        column1=column;
                        row1=row;
                    } else if (matrix[column][row]==secondChar){
                        column2=column;
                        row2=row;
                    }

                }
            }



            if (row1==row2 && column1==column2){
                ++row1;
                ++row2;
                if(row1>mateixSize-1) row1=0;
                if(row2>mateixSize-1) row2=0;

                Ciphertext+=String.valueOf(matrix[column1][row1]);
                Ciphertext+=String.valueOf(matrix[column2][row2]);

            } else if(row1==row2) {
                ++column1;
                ++column2;
                if(column1>mateixSize-1) column1=0;
                if(column2>mateixSize-1) column2=0;

                Ciphertext+=String.valueOf(matrix[column1][row2]);
                Ciphertext+=String.valueOf(matrix[column2][row1]);

            } else if(column1==column2) {

                ++row1;
                ++row2;
                if(row1>mateixSize-1) row1=0;
                if(row2>mateixSize-1) row2=0;

                Ciphertext+=String.valueOf(matrix[column1][row1]);
                Ciphertext+=String.valueOf(matrix[column2][row2]);


            }
            else {
                Ciphertext+=String.valueOf(matrix[column1][row2]);
                Ciphertext+=String.valueOf(matrix[column2][row1]);

            }




        }


        return Ciphertext;


    }

    public String decrypt(char [][] matrix, String cipherText){
        String plainText="";

        int ptLength = cipherText.length();


//
        for (int len = 0; len < ptLength-1; len=len+2) {

            char    firstChar=cipherText.charAt(len);
            char    secondChar=cipherText.charAt(len+1);
            int row1=0,row2=0,column1=0,column2=0;


            for(int column=0;column < matrix.length;column++) {

                for(int row=0;row < matrix[column].length;row++) {

                    if(matrix[column][row]==firstChar && matrix[column][row]==secondChar){
                        column1=column2=column;
                        row1=row2=row;

                    }
                    else if(matrix[column][row]==firstChar){
                        column1=column;
                        row1=row;
                    } else if (matrix[column][row]==secondChar){
                        column2=column;
                        row2=row;
                    }

                }
            }



            if (row1==row2 && column1==column2){
                --row1;
                --row2;
                if(row1<0) row1=5;
                if(row1<0) row2=5;

                plainText+=String.valueOf(matrix[column1][row1]);
                plainText+=String.valueOf(matrix[column2][row2]);

            } else if(row1==row2) {
                --column1;
                --column2;
                if(column1<0) column1=5;
                if(column2<0) column2=5;

                plainText+=String.valueOf(matrix[column1][row2]);
                plainText+=String.valueOf(matrix[column2][row1]);

            } else if(column1==column2) {

                --row1;
                --row2;
                if(row1<0) row1=5;
                if(row2<0) row2=5;

                plainText+=String.valueOf(matrix[column1][row1]);
                plainText+=String.valueOf(matrix[column2][row2]);


            }
            else {
                plainText+=String.valueOf(matrix[column1][row2]);
                plainText+=String.valueOf(matrix[column2][row1]);

            }




        }


        return plainText;


    }

}
