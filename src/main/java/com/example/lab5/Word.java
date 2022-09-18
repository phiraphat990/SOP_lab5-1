package com.example.lab5;

import java.util.ArrayList;

public class Word {
    public ArrayList<String> badWords;
    public ArrayList<String> goodWords;
    public Word(){
        badWords = new ArrayList<>();
        goodWords = new ArrayList<>();
        goodWords.add("happy");
        goodWords.add("enjoy");
        goodWords.add("life");
        badWords.add("fuck");
        badWords.add("olo");

    }

}
