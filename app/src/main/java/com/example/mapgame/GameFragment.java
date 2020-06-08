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

    //Time & frames per second
    long current_time;
    long previous_time = 0;
    long delta_time;
    long frames_per_second;
    int screen_update_count = 0;

    //Bitmaps
    Bitmap HomerRight = BitmapFactory.decodeResource(getResources(), R.drawable.homerright);
    Bitmap HomerRightWalk1 = BitmapFactory.decodeResource(getResources(), R.drawable.homerrightwalk1);
    Bitmap HomerRightWalk2 = BitmapFactory.decodeResource(getResources(), R.drawable.homerrightwalk2);
    Bitmap HomerRightWalk3 = BitmapFactory.decodeResource(getResources(), R.drawable.homerrightwalk3);
    Bitmap HomerRightWalk4 = BitmapFactory.decodeResource(getResources(), R.drawable.homerrightwalk4);
    Bitmap HomerRightWalk5 = BitmapFactory.decodeResource(getResources(), R.drawable.homerrightwalk5);
    Bitmap HomerRightWalk6 = BitmapFactory.decodeResource(getResources(), R.drawable.homerrightwalk6);
    Bitmap HomerRightWalk7 = BitmapFactory.decodeResource(getResources(), R.drawable.homerrightwalk7);
    Bitmap HomerRightWalk8 = BitmapFactory.decodeResource(getResources(), R.drawable.homerrightwalk8);
    Bitmap HomerLeft = BitmapFactory.decodeResource(getResources(), R.drawable.homerleft);
    Bitmap HomerLeftWalk1 = BitmapFactory.decodeResource(getResources(), R.drawable.homerleftwalk1);
    Bitmap HomerLeftWalk2 = BitmapFactory.decodeResource(getResources(), R.drawable.homerleftwalk2);
    Bitmap HomerLeftWalk3 = BitmapFactory.decodeResource(getResources(), R.drawable.homerleftwalk3);
    Bitmap HomerLeftWalk4 = BitmapFactory.decodeResource(getResources(), R.drawable.homerleftwalk4);
    Bitmap HomerLeftWalk5 = BitmapFactory.decodeResource(getResources(), R.drawable.homerleftwalk5);
    Bitmap HomerLeftWalk6 = BitmapFactory.decodeResource(getResources(), R.drawable.homerleftwalk6);
    Bitmap HomerLeftWalk7 = BitmapFactory.decodeResource(getResources(), R.drawable.homerleftwalk7);
    Bitmap HomerLeftWalk8 = BitmapFactory.decodeResource(getResources(), R.drawable.homerleftwalk8);

    //Last direction facing
    String last_direction_facing = "right";

    //Buttons
    boolean button_up_pressed = false;
    boolean button_down_pressed = false;
    boolean button_left_pressed = false;
    boolean button_right_pressed = false;
    int button_pressed_count = 0;

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
                canvas.drawBitmap(HomerLeft, x, y,null);
                last_direction_facing = "up";
                y = y-1;
            }
            else if(button_down_pressed == true){
                canvas.drawBitmap(HomerLeft, x, y,null);
                last_direction_facing = "down";
                y = y+1;
            }
            else if(button_left_pressed == true){
                if(button_pressed_count < 10){
                    canvas.drawBitmap(HomerLeftWalk4, x, y, null);
                }
                else if(button_pressed_count < 20){
                    canvas.drawBitmap(HomerLeftWalk5, x, y, null);
                }
                else if(button_pressed_count < 30){
                    canvas.drawBitmap(HomerLeftWalk6, x, y, null);
                }
                else if(button_pressed_count < 40){
                    canvas.drawBitmap(HomerLeftWalk7, x, y, null);
                }
                else if(button_pressed_count < 50){
                    canvas.drawBitmap(HomerLeftWalk8, x, y, null);
                }
                else if(button_pressed_count < 60){
                    canvas.drawBitmap(HomerLeftWalk1, x, y, null);
                }
                else if(button_pressed_count < 70){
                    canvas.drawBitmap(HomerLeftWalk2, x, y, null);
                }
                else if(button_pressed_count < 80){
                    canvas.drawBitmap(HomerLeftWalk3, x, y, null);
                }
                else if(button_pressed_count == 80){
                    canvas.drawBitmap(HomerLeftWalk3, x, y, null);
                    button_pressed_count = 0;
                }
                last_direction_facing = "left";
                x = x-2;
                button_pressed_count += 1; }
            else if(button_right_pressed == true){
                if(button_pressed_count < 10){
                    canvas.drawBitmap(HomerRightWalk4, x, y, null);
                }
                else if(button_pressed_count < 20){
                    canvas.drawBitmap(HomerRightWalk5, x, y, null);
                }
                else if(button_pressed_count < 30){
                    canvas.drawBitmap(HomerRightWalk6, x, y, null);
                }
                else if(button_pressed_count < 40){
                    canvas.drawBitmap(HomerRightWalk7, x, y, null);
                }
                else if(button_pressed_count < 50){
                    canvas.drawBitmap(HomerRightWalk8, x, y, null);
                }
                else if(button_pressed_count < 60){
                    canvas.drawBitmap(HomerRightWalk1, x, y, null);
                }
                else if(button_pressed_count < 70){
                    canvas.drawBitmap(HomerRightWalk2, x, y, null);
                }
                else if(button_pressed_count < 80){
                    canvas.drawBitmap(HomerRightWalk3, x, y, null);
                }
                else if(button_pressed_count == 80){
                    canvas.drawBitmap(HomerRightWalk3, x, y, null);
                    button_pressed_count = 0;
                }
                last_direction_facing = "right";
                x = x+2;
                button_pressed_count += 1;
            }
            else{
                button_pressed_count = 0;
                if(last_direction_facing == "up"){
                    canvas.drawBitmap(HomerLeft, x, y,null);
                }
                if(last_direction_facing == "down"){
                    canvas.drawBitmap(HomerLeft, x, y,null);
                }
                if(last_direction_facing == "left"){
                    canvas.drawBitmap(HomerLeft, x, y,null);
                }
                if(last_direction_facing == "right"){
                    canvas.drawBitmap(HomerRight, x, y,null);
                }
            }
            current_time = System.currentTimeMillis();
            delta_time = previous_time - current_time;
            previous_time = current_time;
            if(screen_update_count == 100){
                double dTime = (double) delta_time;
                Log.i("frames per second", Double.toString(-1.0*(1.0 / (dTime / 1000.0))));
                screen_update_count = 0;
            }
            screen_update_count += 1;
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