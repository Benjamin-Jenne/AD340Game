package com.example.mapgame.entities;

public class Entity {
    private int X;
    private int Y;
    private int width = 49;
    private int height = 49;

    public int getX() {
        return X;
    }
    public int getY(){
        return Y;
    }
    public void setX(int newX){
        X = newX;
    }
    public void setY(int newY){
        Y = newY;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public void setWidth(int newWidth){
        width = newWidth;
    }
    public void setHeight(int newHeight) {
        height = newHeight;
    }
}
