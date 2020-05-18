package com.example.mapgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GameFragment extends Fragment {
    private GameActivity.ButtonEvent buttonEvent;
    GameView gameView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        gameView = new GameView(getActivity());
        return gameView;
    }
    @Override
    public void onResume(){
        super.onResume();
        gameView.resume();
    }
    @Override
    public void onPause(){
        super.onPause();
        gameView.pause();
    }
    public void setButtonEvent(GameActivity.ButtonEvent buttonEvent){
        this.buttonEvent = buttonEvent;
    }
}
//Game Loop credit:
//Zechner, Mario. Beginning Android Games . Apress. Kindle Edition.
class GameView extends SurfaceView implements Runnable {
    Thread gameThread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;

    public GameView(Context context){
        super(context);
        surfaceHolder = getHolder();
    }
    protected void resume(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(0, 0, 0));
        int x = 100;
        int y = 100;
        while(running == true){
            if(!surfaceHolder.getSurface().isValid()){
                continue;
            }
            x = x + 1;
            y = y + 1;
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawRGB(255,0,0);
            canvas.drawRect(x,y,x+100,y+100, myPaint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    public void pause(){
        running = false;
        while(true){
            try{
                gameThread.join();
                return;
            } catch (InterruptedException e){
            }
        }
    }
}