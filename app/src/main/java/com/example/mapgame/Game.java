package com.example.mapgame;

public class Game
{
    private Thread thread;
    private GameLoop gameLoop;
    public void start()
    {
        gameLoop = new GameLoop();
        thread = new thread(gameLoop);
        thread.start();
    }
    public void stop()
    {
        gameLoop.pleaseStop();
        try{
            thread.join();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
