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
    int health = 1200;

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

    Bitmap Health1 = BitmapFactory.decodeResource(getResources(), R.drawable.health_1);
    Bitmap Health2 = BitmapFactory.decodeResource(getResources(), R.drawable.health_2);
    Bitmap Health3 = BitmapFactory.decodeResource(getResources(), R.drawable.health_3);
    Bitmap Health4 = BitmapFactory.decodeResource(getResources(), R.drawable.health_4);
    Bitmap Health5 = BitmapFactory.decodeResource(getResources(), R.drawable.health_5);
    Bitmap Health6 = BitmapFactory.decodeResource(getResources(), R.drawable.health_6);
    Bitmap Health7 = BitmapFactory.decodeResource(getResources(), R.drawable.health_7);
    Bitmap Health8 = BitmapFactory.decodeResource(getResources(), R.drawable.health_8);
    Bitmap Health9 = BitmapFactory.decodeResource(getResources(), R.drawable.health_9);
    Bitmap Health10 = BitmapFactory.decodeResource(getResources(), R.drawable.health_10);
    Bitmap Health11 = BitmapFactory.decodeResource(getResources(), R.drawable.health_11);
    Bitmap Health12 = BitmapFactory.decodeResource(getResources(), R.drawable.health_12);

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

            // Draws the player sprite
            if(button_up_pressed){
                if(y > wallHeight*2) {
                    if (button_pressed_count < 10) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                    } else if (button_pressed_count < 20) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count < 30) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 40) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 50) {
                        canvas.drawBitmap(PlayerMove5, x, y, null);
                    } else if (button_pressed_count < 60) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 70) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 80) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count == 80) {
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
                    if (button_pressed_count < 10) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                    } else if (button_pressed_count < 20) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count < 30) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 40) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 50) {
                        canvas.drawBitmap(PlayerMove5, x, y, null);
                    } else if (button_pressed_count < 60) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 70) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 80) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count == 80) {
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
                    if (button_pressed_count < 10) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                    } else if (button_pressed_count < 20) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count < 30) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 40) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 50) {
                        canvas.drawBitmap(PlayerMove5, x, y, null);
                    } else if (button_pressed_count < 60) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 70) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 80) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count == 80) {
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
                    if (button_pressed_count < 10) {
                        canvas.drawBitmap(PlayerMove1, x, y, null);
                    } else if (button_pressed_count < 20) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count < 30) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 40) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 50) {
                        canvas.drawBitmap(PlayerMove5, x, y, null);
                    } else if (button_pressed_count < 60) {
                        canvas.drawBitmap(PlayerMove4, x, y, null);
                    } else if (button_pressed_count < 70) {
                        canvas.drawBitmap(PlayerMove3, x, y, null);
                    } else if (button_pressed_count < 80) {
                        canvas.drawBitmap(PlayerMove2, x, y, null);
                    } else if (button_pressed_count == 80) {
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

                    if (button_pressed_count < 10) {
                        canvas.drawBitmap(Attack1, attx, atty, null);
                    } else if (button_pressed_count < 20) {
                        canvas.drawBitmap(Attack2, attx, atty, null);
                    } else if (button_pressed_count < 30) {
                        canvas.drawBitmap(Attack3, attx, atty, null);
                    } else if (button_pressed_count < 40) {
                        canvas.drawBitmap(Attack4, attx, atty, null);
                    } else if (button_pressed_count < 50) {
                        canvas.drawBitmap(Attack5, attx, atty, null);
                    } else if (button_pressed_count < 60) {
                        canvas.drawBitmap(Attack6, attx, atty, null);
                    } else if (button_pressed_count < 70) {
                        canvas.drawBitmap(Attack7, attx, atty, null);
                    } else if (button_pressed_count < 80) {
                        canvas.drawBitmap(Attack8, attx, atty, null);
                    } else if (button_pressed_count == 80) {
                        canvas.drawBitmap(Attack1, attx, atty, null);
                        button_pressed_count = 0;
                    }
                    button_pressed_count += 1;
                } else if(last_direction_facing == 3) {
                    attx = x - entityWidth;
                    atty = y;
                }

                if (button_pressed_count < 10) {
                    canvas.drawBitmap(Attack1, attx, atty, null);
                } else if (button_pressed_count < 20) {
                    canvas.drawBitmap(Attack2, attx, atty, null);
                } else if (button_pressed_count < 30) {
                    canvas.drawBitmap(Attack3, attx, atty, null);
                } else if (button_pressed_count < 40) {
                    canvas.drawBitmap(Attack4, attx, atty, null);
                } else if (button_pressed_count < 50) {
                    canvas.drawBitmap(Attack5, attx, atty, null);
                } else if (button_pressed_count < 60) {
                    canvas.drawBitmap(Attack6, attx, atty, null);
                } else if (button_pressed_count < 70) {
                    canvas.drawBitmap(Attack7, attx, atty, null);
                } else if (button_pressed_count < 80) {
                    canvas.drawBitmap(Attack8, attx, atty, null);
                } else if (button_pressed_count == 80) {
                    canvas.drawBitmap(Attack1, attx, atty, null);
                    button_pressed_count = 0;
                }
                button_pressed_count += 1;
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
            if(x2 + entityWidth < 1080 - wallWidth && enemy2Direction) {
                x2 += 5;
            } else {
                enemy2Direction = false;
                if(x2 > wallWidth) {
                    x2 -= 5;
                } else {
                    enemy2Direction = true;
                }
            }

            if(x3 + entityWidth < 1080 - wallWidth && enemy3Direction) {
                x3 += 5;
            } else {
                enemy3Direction = false;
                if(x3 > wallWidth) {
                    x3 -= 5;
                } else {
                    enemy3Direction = true;
                }
            }

            if(y4 + entityHeight < 1920 - wallHeight && enemy4Direction) {
                y4 += 5;
            } else {
                enemy4Direction = false;
                if(y4 > wallHeight*2) {
                    y4 -= 5;
                } else {
                    enemy4Direction = true;
                }
            }

            if(y5 + entityHeight < 1920 - wallHeight && enemy5Direction) {
                y5 += 5;
            } else {
                enemy5Direction = false;
                if(y5 > wallHeight*2) {
                    y5 -= 5;
                } else {
                    enemy5Direction = true;
                }
            }

            // The diagonal enemy (enemy6) behavior
            if(x6 + entityWidth < 1080 - wallWidth && enemy6xDirection) {
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

            // The "hunter" (seeking behavior) enemy
            if(x7 < x && y7 < y) {
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

//            if gameView.health

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
                health = health - 2;
            }

            // Display health

            if(health == 1200) {
                canvas.drawBitmap(Health12, 200, 300, null);
            } else if(health >= 1100 && health < 1200) {
                canvas.drawBitmap(Health11, 200, 300, null);
            } else if(health >= 1000 && health < 1100) {
                canvas.drawBitmap(Health10, 200, 300, null);
            } else if(health >= 900 && health < 1000) {
                canvas.drawBitmap(Health9, 200, 300, null);
            } else if(health >= 800 && health < 900) {
                canvas.drawBitmap(Health8, 200, 300, null);
            } else if(health >= 700 && health < 800) {
                canvas.drawBitmap(Health7, 200, 300, null);
            } else if(health >= 600 && health < 700) {
                canvas.drawBitmap(Health6, 200, 300, null);
            } else if(health >= 500 && health < 600) {
                canvas.drawBitmap(Health5, 200, 300, null);
            } else if(health >= 400 && health < 500) {
                canvas.drawBitmap(Health4, 200, 300, null);
            } else if(health >= 300 && health < 400) {
                canvas.drawBitmap(Health3, 200, 300, null);
            } else if(health >= 200 && health < 300) {
                canvas.drawBitmap(Health2, 200, 300, null);
            } else if(health >= 100 && health < 200) {
                canvas.drawBitmap(Health1, 200, 300, null);
            } else if(health >= 1 && health < 100) {
                canvas.drawBitmap(Health1, 200, 300, null);
            }

            takingDamage = false;

            if(health < 1) {
                canvas.drawBitmap(GameOver, 160, 700, null);
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
}