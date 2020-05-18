package com.example.mapgame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.MainThread;

public class Game extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    public Game(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread();
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(true){
            try{
                gameThread.setRunning(false);
                gameThread.join();
            } catch(Exception e){
                Log.i("Inside SurfaceDestroyed", "Exception caught");
            }
            retry = false;
        }
    }
    public void updateScreen(){

    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas)
    }
}
