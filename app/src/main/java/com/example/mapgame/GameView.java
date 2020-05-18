package com.example.mapgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

//Game Loop credit:
//Zechner, Mario. Beginning Android Games . Apress. Kindle Edition.
class GameView extends SurfaceView implements Runnable {
    Game game;
    Bitmap framebuffer;
    Thread gameThread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;

    public GameView(Game game, Bitmap framebuffer){
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.surfaceHolder = getHolder();
    }
    protected void resume(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while(running == true){
            if(!surfaceHolder.getSurface().isValid()){
                continue;
            }
            float deltaTime = (System.nanoTime()-startTime)/ 1000000000.0f;
            startTime = System.nanoTime();

            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().present(deltaTime);

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.getClipBounds();
            canvas.drawBitmap(framebuffer, null, dstRect, null);

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
