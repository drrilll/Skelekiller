package com.example.darryl.app2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Button implements Drawable{
    /**
     * I think we will both check the touch and update the model. We'll see.
     * @param event
     * @return
     */
    int x, y;
    final int width, height;
    int color;
    public Button(int x, int y, int width, int height, int color){
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public boolean checkTouched(MotionEvent event){
        float touchx = event.getX();
        float touchy = event.getY();
        return ((touchx >= x)&&(touchx <=x+width)&&(touchy >= y)&&(touchy <= y+height));
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(x,y,x+width,y+height,paint);
    }
}
