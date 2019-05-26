package com.example.android.countheaven;

import android.arch.lifecycle.ViewModel;

public class guessView extends ViewModel {
public  int ageno=0;
public static int nowon=0;
public final int total=MainActivity.guess2;
public  int guesstrack=MainActivity.guess2;
public int agetrack=MainActivity.age2;
public static int nolost=0;
public String gage;
public int cnt=0;
public String low="Oops..You are reaping a young soul!";
public String high="That's too long..The sole must be reaped earlier!";
public String retry="Start again";
public String cg="No. of Correct guess=";
public String wg="No. of Wrong Guess=";
public String g="Guess";
public String correct="yay!!You got the right age \n Start the game again?";
public String q="Guess the age to reap the soul\n You can make ";
public String x=" guesses!";

}
