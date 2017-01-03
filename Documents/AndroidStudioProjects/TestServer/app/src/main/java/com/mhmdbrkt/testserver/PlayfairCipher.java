package com.mhmdbrkt.testserver;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by mhmd on 12/30/16.
 */

public class PlayfairCipher {

    StringBuilder sb;
    String alphabet = "";
    String plainText = "";
    char fillerLetter;
    String key = "";
    String finalMatrixString;
    int columnNo = 0;
    int rowNo = 0;
    char[][] Matrix;
    Set<Character> matrixString = new LinkedHashSet<>();
    StringBuilder finalString = new StringBuilder();

    int [] fllArray ;
    int [] spaceArray ;
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

    public PlayfairCipher(String alphabet, int columnNo, int rowNo, char fillerLetter, String plainText, String key) {

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

    public String formatText(String Message) {Message = Message.toUpperCase();
        oddFlag=false;

        // Count No of Spaces
        int spaceCounter=0;
        for(int i=0; i<Message.length(); i++) {
            if(Message.charAt(i)==' ') {
                spaceCounter++;
            }
        }
        spaceArray = new int[spaceCounter];
        int counter=0;

        for (int i = 0; i < Message.length(); i++) {
            if (Message.charAt(i)==' '){
                spaceArray[counter]=i;
                counter++;
            }
        }

        Message = Message.replaceAll(" ", "");


        fllArray = new int[Message.length()];
        int fillerCounter=0;
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
                fllArray[fillerCounter]=i;
                fillerCounter++;

            } else {
                fllArray[fillerCounter]=0;

            }

        }

        // Check if length is odd
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



    public char[][] getMatrix() {
        return Matrix;
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


//

        if (oddFlag==true){
            plainText=plainText.substring(0,plainText.length()-1);
        }

        sb = new StringBuilder(plainText);

        if (fllArray!=null){
            for (int i = 0; i <fllArray.length ; i++) {
                if (fllArray[i]>0){
                    sb.deleteCharAt(fllArray[i]);
                    sb.insert(0,"%");
                }

            }



            plainText=sb.toString();
            sb = new StringBuilder(plainText.replaceAll("%",""));
            plainText=sb.toString();
        }


        if ( spaceArray != null  ) {

            for (int i = 0; i <spaceArray.length ; i++) {
                if (spaceArray[i]>0){
                    sb.insert(spaceArray[i],' ');
                }
            }
            plainText=sb.toString();

        }



        return plainText;

    }

    public int[] getSpaceArray() {
        return spaceArray;
    }

    public void setSpaceArray(int[] spaceArray) {
        this.spaceArray = spaceArray;
    }

    public StringBuilder getSb() {
        return sb;
    }

}
