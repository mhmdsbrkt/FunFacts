package com.mhmdbrkt.clientside;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by mhmd on 12/30/16.
 */

public class PlyfairCipher {

    final String EnglishAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    final String ArabicAlphabets = "اأإءآىبپتةثجچحخدذرزسشصضطظعغفڤقكلمنهوؤيئ٠١٢٣٤٥٦٧٨٩";
    final String mixAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZابتثجحخدذرزسشصضطظعغفقكلمنهوي0123456789";
    String alphabet = "";
    String plainText = "";
    char fillerLetter;
    String key = "";
    String finalMatrixString;
    int columnNo = 0;
    int rowNo = 0;
    char[][] Matrix;
    int mateixSize = 0;
    Set<Character> matrixString = new LinkedHashSet<>();
    StringBuilder finalString = new StringBuilder();
    int [] fllArray ;
    boolean oddFlag;

    public int[] getFllArray() {
        return fllArray;
    }

    public void setFllArray(int[] fllArray) {
        this.fllArray = fllArray;
    }

    public boolean isOddFlag() {
        return oddFlag;
    }

    public void setOddFlag(boolean oddFlag) {
        this.oddFlag = oddFlag;
    }

    public PlyfairCipher(String alphabet, int columnNo, int rowNo, char fillerLetter, String plainText, String key) {

        key = formatKey(key);
        this.alphabet = alphabet.toUpperCase();
        this.fillerLetter = fillerLetter;
        this.plainText = formatText(plainText);
        this.key = key;
        this.columnNo = columnNo;
        this.rowNo = rowNo;
        finalMatrixString = finalString(key, alphabet).toString();
        Matrix = constructMatrix();


    }

    public String formatAlphabets(String letters) {
        letters = letters.toUpperCase();
        letters = letters.replaceAll(" ", "");

        LinkedHashSet<Character> formattedKeyList = new LinkedHashSet<>();
        for (int i = 0; i < letters.length(); i++) formattedKeyList.add(letters.charAt(i));
        StringBuilder formattedKey = new StringBuilder();
        for (Character character : formattedKeyList) {
            formattedKey.append(character);
        }
        letters = formattedKey.toString().toUpperCase();

        return letters;
    }

    public String formatKey(String Key) {
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

    public String formatText(String Message) {
        Message = Message.toUpperCase();
        Message = Message.replaceAll(" ", "");
        fllArray = new int[Message.length()*2];
        oddFlag=false;


        for (int i = 1; i < Message.length(); i = i + 2) {
            if (Message.charAt(i - 1) == Message.charAt(i)) {
                String temp1 = "";
                String temp2 = "";
                for (int j = 0; j < i; j++) {
                    temp1 += Message.charAt(j);
                }

                for (int j = i; j < Message.length(); j++) {
                    temp2 += Message.charAt(j);

                }
                Message = "";
                Message = temp1 + fillerLetter + temp2;
                fllArray[i]=1;
            }

        }

        if (Message.length() % 2 == 1) {
            Message = Message + fillerLetter;
            oddFlag=true;

        }


        return Message;
    }


    public StringBuilder finalString(String key, String alphabets) {

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

    public char[][] constructMatrix() {

        char[][] finalMatrix = new char[columnNo][rowNo];
        int counter = 0;
        int StringLength = finalMatrixString.length();

        for (int row = 0; row < rowNo; row++) {

            for (int column = 0; column < columnNo; column++) {
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

    public String encrypt(char[][] matrix, String Plaintext) {
        String Ciphertext = "";

        int ptLength = Plaintext.length();


//
        for (int len = 0; len < ptLength - 1; len = len + 2) {

            char firstChar = Plaintext.charAt(len);
            char secondChar = Plaintext.charAt(len + 1);
            int row1 = 0, row2 = 0, column1 = 0, column2 = 0;

            for (int row = 0; row < rowNo; row++) {

                for (int column = 0; column < columnNo; column++) {
                    if (matrix[row][column] == firstChar) {
                        column1 = column;
                        row1 = row;
                    } else if (matrix[row][column] == secondChar) {
                        column2 = column;
                        row2 = row;
                    }
                }
            }

            if (row1 == row2) {
                column1 = (column1 + 1) % columnNo;
                column2 = (column2 + 1) % columnNo;
                Ciphertext += String.valueOf(matrix[row1][column1]);
                Ciphertext += String.valueOf(matrix[row2][column2]);

            } else if (column1 == column2) {
                row1 = (row1 + 1) % rowNo;
                row2 = (row2 + 1) % rowNo;
                Ciphertext += String.valueOf(matrix[row1][column1]);
                Ciphertext += String.valueOf(matrix[row2][column2]);

            } else {
                Ciphertext += String.valueOf(matrix[row1][column2]);
                Ciphertext += String.valueOf(matrix[row2][column1]);

            }


        }


        return Ciphertext;


    }

    public String decrypt(char[][] matrix, String cipherText) {

        String plainText = "";
        int ptLength = cipherText.length();

        for (int len = 0; len < ptLength - 1; len = len + 2) {

            char firstChar = cipherText.charAt(len);
            char secondChar = cipherText.charAt(len + 1);
            int row1 = 0, row2 = 0, column1 = 0, column2 = 0;


            for (int row = 0; row < rowNo; row++) {

                for (int column = 0; column < columnNo; column++) {

                    if (matrix[row][column] == firstChar) {
                        column1 = column;
                        row1 = row;
                    } else if (matrix[row][column] == secondChar) {
                        column2 = column;
                        row2 = row;
                    }

                }
            }


            if (row1 == row2) {

                column1 = Math.abs((column1 + (columnNo - 1)) % columnNo);
                column2 = Math.abs((column2 + (columnNo - 1)) % columnNo);

                plainText += String.valueOf(matrix[row1][column1]);
                plainText += String.valueOf(matrix[row2][column2]);
            } else if (column1 == column2) {

                row1 = Math.abs((row1 + (rowNo - 1)) % rowNo);
                row2 = Math.abs((row2 + ((rowNo - 1))) % rowNo);

                plainText += String.valueOf(matrix[row1][column1]);
                plainText += String.valueOf(matrix[row2][column2]);
            } else {
                plainText += String.valueOf(matrix[row1][column2]);
                plainText += String.valueOf(matrix[row2][column1]);

            }


        }


        for (int i = 0; i <fllArray.length ; i++) {

            if (fllArray[i]>0){
                StringBuilder sb = new StringBuilder(plainText);
                sb.deleteCharAt(i);
                plainText=sb.toString();
            }


        }

        if (oddFlag==true){
            plainText=plainText.substring(0,plainText.length()-1);
        }
        return plainText;

    }

}
