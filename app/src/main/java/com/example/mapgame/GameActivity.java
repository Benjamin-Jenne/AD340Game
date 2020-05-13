package com.example.mapgame;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = GameActivity.class.getSimpleName();

    private Button button_attack;
    private Button button_left;
    private Button button_right;
    private Button button_down;
    private Button button_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        button_attack = findViewById(R.id.buttonAttack);
        button_left = findViewById(R.id.buttonLeft);
        button_right = findViewById(R.id.buttonRight);
        button_down = findViewById(R.id.buttonDown);
        button_up = findViewById(R.id.buttonUp);

        button_attack.setOnClickListener(this);

        //Referenced https://stackoverflow.com/questions/4597513/onpress-onrelease-in-android
        button_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.i(TAG, "Button Left Pressed Down");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.i(TAG, "Button Left Released");
                }
                return true;
            }
        });
        button_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.i(TAG, "Button Right Pressed Down");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.i(TAG, "Button Right Released");
                }
                return true;
            }
        });
        button_down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.i(TAG, "Button Down Pressed Down");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.i(TAG, "Button Down Released");
                }
                return true;
        }});
        button_up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Log.i(TAG, "Button Up Pressed Down");
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Log.i(TAG, "Button Up Released");
                }
                return true;
            }});

        Log.i(TAG, "onCreate()");
    }
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.buttonAttack){
            Log.i(TAG, "Attack Button Was Clicked");
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(TAG, "Start()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
