package com.example.mapgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mapgame.entities.HealthBarListener;

//import static com.example.mapgame.GameFragment.runOnUiThread;

public class GameFragment extends Fragment implements HealthBarListener{
    private GameActivity.ButtonEvent buttonEvent;
    GameView gameView;
    ProgressBar healthBar;
    Chronometer timer;

    TextView gameOver;
    Button   restart;

    TextView score;

    int healthLevel;
    int scoreLevel;

    private static final String TAG = GameFragment.class.getSimpleName();

//    public static void runOnUiThread(Runnable in_run) {
//        in_run.run();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        healthBar = getActivity().findViewById(R.id.determinateBar);
        timer     = getActivity().findViewById(R.id.chronometerTimer);
        timer.start();

        score     = getActivity().findViewById(R.id.textViewScoreAmount);

        gameOver  = getActivity().findViewById(R.id.textViewGameOver);
        restart   = getActivity().findViewById(R.id.buttonRestart);

        gameView = new GameView(getActivity());
        gameView.setCallback(this);
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
//        gameView.pause();
    }

    public void setButtonEvent(GameActivity.ButtonEvent buttonEvent){
        this.buttonEvent = buttonEvent;
//        Log.i(TAG, "inside game frag "+ buttonEvent);
    }

    // Lower Activity health bar via listener
    @Override
    public void onPlayerAttacked() {
        healthLevel = healthBar.getProgress();
        healthBar.setProgress(healthLevel - 1);
    }

    // Errors: only original thread can touch views activity views. ie Ui thread?
    @Override
    public void onPlayerKilled() {

        getActivity().runOnUiThread(()-> {
            gameOver.setVisibility(View.VISIBLE);
            restart.setVisibility(View.VISIBLE);
            timer.stop();
        });
    }

    @Override
    public void onScore() {
        getActivity().runOnUiThread(()-> {
            score.setText(""+scoreLevel);
            scoreLevel = scoreLevel + 10;
        });

    }



}
//Game Loop credit:
//Zechner, Mario. Beginning Android Games . Apress. Kindle Edition.
class GameView extends SurfaceView implements Runnable{
    private static final String TAG = GameFragment.class.getSimpleName();
    Thread gameThread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;

    private static HealthBarListener mCallback;

    // Player starting position, other stats
    int x = 200;
    int y = 400;

    boolean takingDamage = false;

    final int X_INCREMENT = 10;
    final int Y_INCREMENT = 10;

    final int wallWidth = 60;
    final int wallHeight = 60;

    final int entityWidth = 120;
    final int entityHeight = 120;

    // attack coordinates
    int attx;
    int atty;

    // health
//    int health = 1200;
    int health = 100; // Changed to match progress bar

    // NPC starting position
    int x2 = 600;
    int y2 = 600;

    int x3 = 100;
    int y3 = 1300;

    int x4 = 300;
    int y4 = 1600;

    int x5 = 700;
    int y5 = 500;

    int x6 = 800;
    int y6 = 1800;

    int x7 = 800;
    int y7 = 1700;

    //Enemy respawning timer
    int enemy2Respawn;
    int enemy3Respawn;
    int enemy4Respawn;
    int enemy5Respawn;
    int enemy6Respawn;
    int enemy7Respawn;

    boolean enemy2Direction;
    boolean enemy3Direction;
    boolean enemy4Direction;
    boolean enemy5Direction;
    boolean enemy6xDirection;
    boolean enemy6yDirection;

    Paint myPaint = new Paint();

    //Time & frames per second
    long current_time;
    long previous_time = 0;
    long delta_time;
    long frames_per_second;
    int screen_update_count = 0;



    //Bitmaps

    Bitmap PlayerStill = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_still);
    Bitmap PlayerMove1 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_move_1);
    Bitmap PlayerMove2 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_move_2);
    Bitmap PlayerMove3 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_move_3);
    Bitmap PlayerMove4 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_move_4);
    Bitmap PlayerMove5 = BitmapFactory.decodeResource(getResources(), R.drawable.sprite_move_5);

    Bitmap Attack1 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_1);
    Bitmap Attack2 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_2);
    Bitmap Attack3 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_3);
    Bitmap Attack4 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_4);
    Bitmap Attack5 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_5);
    Bitmap Attack6 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_6);
    Bitmap Attack7 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_7);
    Bitmap Attack8 = BitmapFactory.decodeResource(getResources(), R.drawable.attack_8);

//    Bitmap Health1 = BitmapFactory.decodeResource(getResources(), R.drawable.health_1);
//    Bitmap Health2 = BitmapFactory.decodeResource(getResources(), R.drawable.health_2);
//    Bitmap Health3 = BitmapFactory.decodeResource(getResources(), R.drawable.health_3);
//    Bitmap Health4 = BitmapFactory.decodeResource(getResources(), R.drawable.health_4);
//    Bitmap Health5 = BitmapFactory.decodeResource(getResources(), R.drawable.health_5);
//    Bitmap Health6 = BitmapFactory.decodeResource(getResources(), R.drawable.health_6);
//    Bitmap Health7 = BitmapFactory.decodeResource(getResources(), R.drawable.health_7);
//    Bitmap Health8 = BitmapFactory.decodeResource(getResources(), R.drawable.health_8);
//    Bitmap Health9 = BitmapFactory.decodeResource(getResources(), R.drawable.health_9);
//    Bitmap Health10 = BitmapFactory.decodeResource(getResources(), R.drawable.health_10);
//    Bitmap Health11 = BitmapFactory.decodeResource(getResources(), R.drawable.health_11);
//    Bitmap Health12 = BitmapFactory.decodeResource(getResources(), R.drawable.health_12);

    Bitmap WallBrick = BitmapFactory.decodeResource(getResources(), R.drawable.wall_brick_sprite);
    Bitmap WallCorner = BitmapFactory.decodeResource(getResources(), R.drawable.wall_corner_sprite);
    Bitmap WallVertical = BitmapFactory.decodeResource(getResources(), R.drawable.wall_vertical_sprite);
    Bitmap WallHorizontal = BitmapFactory.decodeResource(getResources(), R.drawable.wall_horizontal_sprite);

    Bitmap Enemy = BitmapFactory.decodeResource(getResources(), R.drawable.enemy_sprite);

    Bitmap GameOver = BitmapFactory.decodeResource(getResources(), R.drawable.game_over_1);

    //Last direction facing
    // Up = 0, right = 1, down = 2, left = 3
    int last_direction_facing;

    //Buttons
    boolean button_up_pressed = false;
    boolean button_down_pressed = false;
    boolean button_left_pressed = false;
    boolean button_right_pressed = false;
    boolean button_attack_pressed = false;

    // Restart button
    boolean button_restart_pressed = false;

    int button_pressed_count = 0;
    int attack_button_pressed_count = 0;


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
        while(running){
            if(!surfaceHolder.getSurface().isValid()){
                continue;
            }

            // Draws the background, "dirt" (solid light-brown)
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawRGB(146,101,72);

            // Draws the brick part of the upper wall
            for(int i = 0; i < 50; i++) {
                canvas.drawBitmap(WallBrick, i*wallWidth, wallHeight, null);
                canvas.drawBitmap(WallBrick, i*wallWidth, wallHeight*2, null);
            }

            //------------------------------------------

            // Score
            mCallback.onScore();

            // Draws the player sprite
            if(button_up_pressed){
                if(y > wallHeight*2) {
                    if (button_pressed_count < 1) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                    } else if (button_pressed_count < 2) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count < 3) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 4) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 5) {
                        canvas.drawBitmap(PlayerMove5, x, y, null);
                    } else if (button_pressed_count < 6) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 7) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 8) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count == 8) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                        button_pressed_count = 0;
                    }
                    last_direction_facing = 0;
                    y = y - Y_INCREMENT;
                    button_pressed_count += 1;
                } else {
                    canvas.drawBitmap(PlayerStill, x, y, null);
                }
            }

            else if(button_down_pressed){
                if(y + entityHeight< 1920) {
                    if (button_pressed_count < 1) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                    } else if (button_pressed_count < 2) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count < 3) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 4) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 5) {
                        canvas.drawBitmap(PlayerMove5, x, y, null);
                    } else if (button_pressed_count < 6) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 7) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 8) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count == 8) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                        button_pressed_count = 0;
                    }
                    last_direction_facing = 2;
                    y = y + Y_INCREMENT;
                    button_pressed_count += 1;
                } else {
                    canvas.drawBitmap(PlayerStill, x, y, null);
                }
            }
            else if(button_left_pressed){
                if(x > wallWidth) {
                    if (button_pressed_count < 1) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                    } else if (button_pressed_count < 2) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count < 3) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 4) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 5) {
                        canvas.drawBitmap(PlayerMove5, x, y, null);
                    } else if (button_pressed_count < 6) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 7) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 8) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count == 8) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                        button_pressed_count = 0;
                    }
                    last_direction_facing = 3;
                    x = x - X_INCREMENT;
                    button_pressed_count += 1;
                } else {
                    canvas.drawBitmap(PlayerStill, x, y, null);
                }
            }
            else if(button_right_pressed){
                if(x + entityWidth < 1020) {
                    if (button_pressed_count < 1) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                    } else if (button_pressed_count < 2) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count < 3) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 4) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 5) {
                        canvas.drawBitmap(PlayerMove5, x, y, null);
                    } else if (button_pressed_count < 6) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 7) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 8) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count == 8) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                        button_pressed_count = 0;
                    }
                    last_direction_facing = 1;
                    x = x + X_INCREMENT;
                    button_pressed_count += 1;
                } else {
                    canvas.drawBitmap(PlayerStill, x, y, null);
                }
            }
            else{
                button_pressed_count = 0;
                canvas.drawBitmap(PlayerStill, x, y, null);
            }

            //-----------------------------------------------------

            // Draws the attack animation
            if(button_attack_pressed) {
                if(last_direction_facing == 0) {
                    attx = x;
                    atty = y - entityHeight;
                } else if(last_direction_facing == 1) {
                    attx = x + entityWidth;
                    atty = y;
                } else if(last_direction_facing == 2) {
                    attx = x;
                    atty = y + entityHeight;
                } else if(last_direction_facing == 3) {
                    attx = x - entityWidth;
                    atty = y;
                }

                // Attack button animation

                if (attack_button_pressed_count < 1) {
                    canvas.drawBitmap(Attack1, attx, atty, null);
                } else if (attack_button_pressed_count < 2) {
                    canvas.drawBitmap(Attack2, attx, atty, null);
                } else if (attack_button_pressed_count < 3) {
                    canvas.drawBitmap(Attack3, attx, atty, null);
                } else if (attack_button_pressed_count < 4) {
                    canvas.drawBitmap(Attack4, attx, atty, null);
                } else if (attack_button_pressed_count < 5) {
                    canvas.drawBitmap(Attack5, attx, atty, null);
                } else if (attack_button_pressed_count < 6) {
                    canvas.drawBitmap(Attack6, attx, atty, null);
                } else if (attack_button_pressed_count < 7) {
                    canvas.drawBitmap(Attack7, attx, atty, null);
                } else if (attack_button_pressed_count < 8) {
                    canvas.drawBitmap(Attack8, attx, atty, null);
                } else if (attack_button_pressed_count == 8) {

                    canvas.drawBitmap(Attack1, attx, atty, null);
                    attack_button_pressed_count = 0;
                }
                attack_button_pressed_count += 1;
            }

            //-----------------------------------------------------

            // Draws the walls
            for(int i = 0; i < 50; i++) {
                canvas.drawBitmap(WallHorizontal, i*wallWidth, 0, null);
                canvas.drawBitmap(WallHorizontal, i*wallWidth, 1860, null);
            }
            for(int i = 0; i < 50; i++) {
                canvas.drawBitmap(WallVertical, 0, i*wallHeight, null);
                canvas.drawBitmap(WallVertical, 1020, i*wallHeight, null);
            }
            for(int i = 0; i < 50; i++) {
                canvas.drawBitmap(WallBrick, i*wallWidth, 1920, null);
            }

            canvas.drawBitmap(WallCorner, 0, 0, null);
            canvas.drawBitmap(WallCorner, 1020, 0, null);
            canvas.drawBitmap(WallCorner, 0, 1860, null);
            canvas.drawBitmap(WallCorner, 1020, 1860, null);

            //------------------------------------

            // Draws NPC enemies

            //enemy 2
            if(x2 > 3000) {
                if(x2 <= 3010) {
                    x2 = 600;
                    y2 = 600;
                } else {
                    while(x2 > 3000) {
                        x2 -= 10;
                    }
                }
            } else if(x2 + entityWidth < 1080 - wallWidth && enemy2Direction) {
                x2 += 5;
            } else {
                enemy2Direction = false;
                if(x2 > wallWidth) {
                    x2 -= 5;
                } else {
                    enemy2Direction = true;
                }
            }

            // enemy 3
            if(x3 > 3000) {
                if (x3 <= 3010) {
                    x3 = 100;
                    y3 = 1300;
                } else {
                    while (x3 > 3000) {
                        x3 -= 10;
                    }
                }
            } else if(x3 + entityWidth < 1080 - wallWidth && enemy3Direction) {
                x3 += 5;
            } else {
                enemy3Direction = false;
                if(x3 > wallWidth) {
                    x3 -= 5;
                } else {
                    enemy3Direction = true;
                }
            }

            //enemy 4
            if(x4 > 3000) {
                if (x4 <= 3010) {
                    x4 = 300;
                    y4 = 1600;
                } else {
                    while (x4 > 3000) {
                        x4 -= 10;
                    }
                }
            } else if(y4 + entityHeight < 1920 - wallHeight && enemy4Direction) {
                y4 += 5;
            } else {
                enemy4Direction = false;
                if(y4 > wallHeight*2) {
                    y4 -= 5;
                } else {
                    enemy4Direction = true;
                }
            }

            // enemy 5
            if(x5 > 3000) {
                if (x5 <= 3010) {
                    x5 = 700;
                    y5 = 500;
                } else {
                    while (x5 > 3000) {
                        x5 -= 10;
                    }
                }
            } else if(y5 + entityHeight < 1920 - wallHeight && enemy5Direction) {
                y5 += 5;
            } else {
                enemy5Direction = false;
                if(y5 > wallHeight*2) {
                    y5 -= 5;
                } else {
                    enemy5Direction = true;
                }
            }

            // enemy 6
            if(x6 > 3000) {
                if (x6 <= 3010) {
                    x6 = 800;
                    y6 = 1800;
                } else {
                    while (x6 > 3000) {
                        x6 -= 10;
                    }
                }
            } else if(x6 + entityWidth < 1080 - wallWidth && enemy6xDirection) {
                x6 += 3;
            } else {
                enemy6xDirection = false;
                if(x6 > wallWidth) {
                    x6 -= 3;
                } else {
                    enemy6xDirection = true;
                }
            }
            if(y6 + entityHeight < 1920 - wallHeight && enemy6yDirection) {
                y6 += 3;
            } else {
                enemy6yDirection = false;
                if(y6 > wallHeight*2) {
                    y6 -= 3;
                } else {
                    enemy6yDirection = true;
                }
            }

            // enemy 7 (seeker)
            if(x7 > 3000) {
                if (x7 <= 3010) {
                    x7 = 800;
                    y7 = 1700;
                } else {
                    while (x7 > 3000) {
                        x7 -= 10;
                    }
                }
            } else if(x7 < x && y7 < y) {
                x7 += 2;
                y7 += 2;
            } else if(x7 < x && y7 > y) {
                x7 += 2;
                y7 -= 2;
            } else if(x7 > x && y7 < y) {
                x7 -= 2;
                y7 += 2;
            } else if(x7 > x && y7 > y) {
                x7 -= 2;
                y7 -= 2;
            } else if(x7 < x) {
                x7 += 2;
            } else if(x7 > x) {
                x7 -= 2;
            } else if(y7 < y) {
                y7 += 2;
            } else if(y7 > y) {
                y7 -= 2;
            }

            canvas.drawBitmap(Enemy, x2, y2, null);
            canvas.drawBitmap(Enemy, x3, y3, null);
            canvas.drawBitmap(Enemy, x4, y4, null);
            canvas.drawBitmap(Enemy, x5, y5, null);
            canvas.drawBitmap(Enemy, x6, y6, null);
            canvas.drawBitmap(Enemy, x7, y7, null);

            //-----------------------------------------------------------

            //enemy/player_attack collision detection (enemy death)
            if(button_attack_pressed) {
                if((attx + entityWidth > x2 && attx < x2 + entityWidth) && (atty + entityHeight > y2 && y < y2 + entityHeight)) {
                    x2 = 4000;
                } else if((attx + entityWidth > x3 && attx < x3 + entityWidth) && (atty + entityHeight > y3 && atty < y3 + entityHeight)) {
                    x3 = 4000;
                } else if((attx + entityWidth > x4 && attx < x4 + entityWidth) && (atty + entityHeight > y4 && atty < y4 + entityHeight)) {
                    x4 = 4000;
                } else if((attx + entityWidth > x5 && attx < x5 + entityWidth) && (atty + entityHeight > y5 && atty < y5 + entityHeight)) {
                    x5 = 4000;
                } else if((attx + entityWidth > x6 && attx < x6 + entityWidth) && (atty + entityHeight > y6 && atty < y6 + entityHeight)) {
                    x6 = 4000;
                } else if((attx + entityWidth > x7 && attx < x7 + entityWidth) && (atty + entityHeight > y7 && atty < y7 + entityHeight)) {
                    x7 = 4000;
                }
            }

            //--------------------------------------------------------------

            // Enemy collision detection

            if((x + entityWidth > x2 && x < x2 + entityWidth) && (y + entityHeight > y2 && y < y2 + entityHeight)) {
                takingDamage = true;
            } else if((x + entityWidth > x3 && x < x3 + entityWidth) && (y + entityHeight > y3 && y < y3 + entityHeight)) {
                takingDamage = true;
            } else if((x + entityWidth > x4 && x < x4 + entityWidth) && (y + entityHeight > y4 && y < y4 + entityHeight)) {
                takingDamage = true;
            } else if((x + entityWidth > x5 && x < x5 + entityWidth) && (y + entityHeight > y5 && y < y5 + entityHeight)) {
                takingDamage = true;
            } else if((x + entityWidth > x6 && x < x6 + entityWidth) && (y + entityHeight > y6 && y < y6 + entityHeight)) {
                takingDamage = true;
            } else if((x + entityWidth > x7 && x < x7 + entityWidth) && (y + entityHeight > y7 && y < y7 + entityHeight)) {
                takingDamage = true;
            }

            if(takingDamage) {
                health = health - 1;
                // Call listener
                mCallback.onPlayerAttacked();
            }
            takingDamage = false;


            // If player dies
            if(health < 1) {
//                canvas.drawBitmap(GameOver, 160, 700, null);
                mCallback.onPlayerKilled();
                running = false;
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

    public void setCallback(HealthBarListener callback){
        mCallback = callback;
    }

}