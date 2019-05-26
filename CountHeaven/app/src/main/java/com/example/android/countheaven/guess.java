package com.example.android.countheaven;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class guess extends MainActivity {
    private guessView mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess);
        mViewModel = ViewModelProviders.of(this).get(guessView.class);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        TextView result = (TextView) findViewById(R.id.result);
        result.setVisibility(View.INVISIBLE);
        TextView correct = (TextView) findViewById(R.id.win);
        correct.setVisibility(View.INVISIBLE);
        TextView wrong = (TextView) findViewById(R.id.lost);
        wrong.setVisibility(View.INVISIBLE);
        TextView noofguess = (TextView) findViewById(R.id.noofguess);
        noofguess.setText(mViewModel.q + mViewModel.total + mViewModel.x);
        Button b = (Button) findViewById(R.id.guess);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                LinearLayout colours = (LinearLayout) findViewById(R.id.colours);
                colours.setVisibility(View.INVISIBLE);
                EditText number = (EditText) findViewById(R.id.guessedage);
                mViewModel.gage = number.getText().toString();
                if (mViewModel.cnt != 0 && mViewModel.cnt != 1) {
                    TextView result = (TextView) findViewById(R.id.result);
                    result.setVisibility(View.VISIBLE);
                    result.setText("GAME OVER..You have exhausted the no. of guesses already!");
                    Button button = (Button) findViewById(R.id.end);
                    button.setText(mViewModel.retry);
                }
                if (TextUtils.isEmpty(mViewModel.gage)) {
                    number.setError("Please Enter The Age To Reap The Soul");
                    number.requestFocus();
                    return;
                }
                mViewModel.ageno = Integer.parseInt(mViewModel.gage);
                if (mViewModel.ageno < 1 || mViewModel.ageno > 100) {
                    number.setError("Please Enter a no. between 1 and 100");
                    number.requestFocus();
                    return;
                }

                mViewModel.guesstrack = mViewModel.guesstrack - 1;
                if (mViewModel.guesstrack == 0) {
                    mViewModel.cnt = 1;
                }
                TextView nogh = (TextView) findViewById(R.id.nogh);
                nogh.setText(mViewModel.g + (mViewModel.total - mViewModel.guesstrack));

                if (mViewModel.ageno >= 1 && mViewModel.ageno <= 100) {
                    if (mViewModel.cnt != 0 && mViewModel.cnt != 1) {
                        TextView result = (TextView) findViewById(R.id.result);
                        result.setVisibility(View.VISIBLE);
                        result.setText("GAME OVER..You have exhausted the no. of guesses already!");
                        Button button = (Button) findViewById(R.id.end);
                        button.setText(mViewModel.retry);
                    } else {
                        TextView result = (TextView) findViewById(R.id.result);
                        result.setVisibility(View.VISIBLE);

                        if (mViewModel.ageno == mViewModel.agetrack) {
                            mViewModel.nowon++;
                            checkcolour(mViewModel.ageno);
                            result.setText(mViewModel.correct + "\nCorrect Answer=" + mViewModel.agetrack);
                            TextView correct = (TextView) findViewById(R.id.win);
                            correct.setVisibility(View.VISIBLE);
                            correct.setText(mViewModel.cg + mViewModel.nowon);
                            TextView wrong = (TextView) findViewById(R.id.lost);
                            wrong.setVisibility(View.VISIBLE);
                            wrong.setText(mViewModel.wg + mViewModel.nolost);
                            Button button = (Button) findViewById(R.id.end);
                            button.setText(mViewModel.retry);
                            LinearLayout removing = (LinearLayout) findViewById(R.id.remove);
                            removing.setVisibility(View.GONE);
                            mViewModel.cnt = 2;

                        } else if (mViewModel.ageno < mViewModel.agetrack) {
                            if (mViewModel.cnt == 1) {
                                checkcolour(mViewModel.ageno);
                                finishguess(mViewModel.ageno);
                                mViewModel.cnt = 2;
                            } else {
                                checkcolour(mViewModel.ageno);
                                result.setText(mViewModel.low);
                            }
                        } else if (mViewModel.ageno > mViewModel.agetrack) {
                            if (mViewModel.cnt == 1) {
                                checkcolour(mViewModel.ageno);
                                finishguess(mViewModel.ageno);
                                mViewModel.cnt = 2;
                                } else {
                                checkcolour(mViewModel.ageno);
                                result.setText(mViewModel.high);
                            }
                        }


                    }
                }
            }
        });


    }

    //TO start the game from the beginning
    public void reset(View view) {
        Intent myIntent = new Intent(guess.this, MainActivity.class);
        startActivity(myIntent);

    }

    //To set the backgroung colour based on how close the guessed answer is to the the actual age
    private void checkcolour(int x) {
        ScrollView sv = (ScrollView) findViewById(R.id.sv);
        int a = mViewModel.agetrack - x;
        if (a > 0) {
            a = a + 0;
        }
        if (a < 0) {
            a = a * (-1);
        }
        /*if difference is
        1.=0:Green
        2.(+/-)10:Bluish-green
        3.(+/-)11 to 25:Yellow
        4.(+/-)26 to 40:Orange
        5.(+/-)41-49:Red
         */
        if (a == 0)
            sv.setBackgroundColor(Color.parseColor("#4caf50"));
        if (a > 0 && a <= 10)
            sv.setBackgroundColor(Color.parseColor("#26a69a"));
        if (a > 10 && a <= 25)
            sv.setBackgroundColor(Color.parseColor("#ffd600"));
        if (a > 25 && a <= 50)
            sv.setBackgroundColor(Color.parseColor("#ff9800"));
        if (a > 51 && a <= 99)
            sv.setBackgroundColor(Color.parseColor("#dd2c00"));
    }

    //If the last guess is wrong this function is called
    private void finishguess(int x) {
        mViewModel.nolost++;
        TextView result = (TextView) findViewById(R.id.result);
        if (x < mViewModel.agetrack) {
            result.setText(mViewModel.low + "\n GAME OVER\n Correct Answer=" + mViewModel.agetrack);
        }
        if (x > mViewModel.agetrack) {
            result.setText(mViewModel.high + "\nGAME OVER \nCorrect Answer=" + mViewModel.agetrack);
        }
        Button button = (Button) findViewById(R.id.end);
        button.setText(mViewModel.retry);
        TextView correct = (TextView) findViewById(R.id.win);
        correct.setVisibility(View.VISIBLE);
        correct.setText(mViewModel.cg + mViewModel.nowon);
        TextView wrong = (TextView) findViewById(R.id.lost);
        wrong.setVisibility(View.VISIBLE);
        wrong.setText(mViewModel.wg + mViewModel.nolost);
        LinearLayout removing = (LinearLayout) findViewById(R.id.remove);
        removing.setVisibility(View.GONE);

        return;
    }

}



