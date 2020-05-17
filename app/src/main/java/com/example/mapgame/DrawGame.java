package com.example.mapgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class DrawGame extends View {
    private int left;
    private int top;
    private int right;
    private int bottom;

    Paint paint = new Paint();

    public DrawGame(Context context) {
        super(context);
    }
    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.GREEN);
        Rect rect = new Rect(left, top, right, bottom);
        canvas.drawRect(rect, paint );
    }
}
