package com.example.darryl.app2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Button implements Drawable{
    /**
     * I think we will both check the touch and update the model. We'll see.
     * @param event
     * @return
     */
    int x, y;
    float width, height;
    Paint buttonPaint,textPaint;
    String text;
    public Button(int x, int y, float height, int color, String text){
        this.text = text;
        this.height = height;
        this.x = x;
        this.y = y;
        buttonPaint = new Paint();
        buttonPaint.setColor(color);

        textPaint = new Paint();
        textPaint.setTextSize(60);
        this.width = textPaint.measureText(text);
        if(width <150){width = 150;}

        textPaint.setColor(Color.rgb(250,250,250));
    }

    public int getRightMostPoint(){
        return x + (int)width;
    }

    public boolean checkTouched(MotionEvent event){
        float touchx = event.getX();
        float touchy = event.getY();
        return ((touchx >= x)&&(touchx <=x+width)&&(touchy >= y)&&(touchy <= y+height));
    }

    public void draw(Canvas canvas){

        canvas.drawRect(x,y,x+width,y+height,buttonPaint);
        canvas.drawText(text,x,y+(height/2)+30,textPaint);
    }
}
