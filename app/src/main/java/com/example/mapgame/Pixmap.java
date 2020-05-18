package com.example.mapgame;

public interface Pixmap {
    public int getWidth();
    public int getHeight();
    public Graphics.PixmapFormat getFormat();
    public void dispose();
}
