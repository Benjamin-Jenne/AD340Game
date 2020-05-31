package com.example.mapgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private static final String TAG = GameFragment.class.getSimpleName();

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
        Log.i(TAG, "inside game frag "+ buttonEvent);
    }
}
//Game Loop credit:
//Zechner, Mario. Beginning Android Games . Apress. Kindle Edition.
class GameView extends SurfaceView implements Runnable {
    Thread gameThread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;
    int x = 100;
    int y = 100;
    Paint myPaint = new Paint();

    //Bitmaps
    Bitmap burns_right = BitmapFactory.decodeResource(getResources(), R.drawable.burnsm_right);
    Bitmap burns_left = BitmapFactory.decodeResource(getResources(), R.drawable.burnsm_left);
    Bitmap burns_forward = BitmapFactory.decodeResource(getResources(), R.drawable.burnsm_forward);

    //Last direction facing
    String last_direction_facing = "right";

    //Buttons
    boolean button_up_pressed = false;
    boolean button_down_pressed = false;
    boolean button_left_pressed = false;
    boolean button_right_pressed = false;

    public GameView(Context context){
        super(context);
        surfaceHolder = getHolder();

        myPaint.setColor(Color.rgb(0, 0, 0));

    }
    protected void resume(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        while(running == true){
            if(!surfaceHolder.getSurface().isValid()){
                continue;
            }
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawRGB(136,192,208);
            if(button_up_pressed == true){
                canvas.drawBitmap(burns_forward, x, y,null);
                last_direction_facing = "up";
                y = y-1;
            }
            if(button_down_pressed == true){
                canvas.drawBitmap(burns_forward, x, y,null);
                last_direction_facing = "down";
                y = y+1;
            }
            if(button_left_pressed == true){
                canvas.drawBitmap(burns_left, x, y,null);
                last_direction_facing = "left";
                x = x-1;
            }
            if(button_right_pressed == true){
                canvas.drawBitmap(burns_right, x, y,null);
                last_direction_facing = "right";
                x = x+1;
            }
            else{
                if(last_direction_facing == "up"){
                    canvas.drawBitmap(burns_forward, x, y,null);
                }
                if(last_direction_facing == "down"){
                    canvas.drawBitmap(burns_forward, x, y,null);
                }
                if(last_direction_facing == "left"){
                    canvas.drawBitmap(burns_left, x, y,null);
                }
                if(last_direction_facing == "right"){
                    canvas.drawBitmap(burns_right, x, y,null);
                }
            }
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