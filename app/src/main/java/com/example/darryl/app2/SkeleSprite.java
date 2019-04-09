package com.example.darryl.app2;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class SkeleSprite {
    private Bitmap[] image;
    int x,y, currentImage, imageTimer;
    public static int TIMERINTERVAL = 15;

    public SkeleSprite(Bitmap[] bmp){
        image = bmp;
        x = 100; y = 100; currentImage = 0;
        imageTimer = 0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image[currentImage],x,y, null);
    }

    public void update(){
        y++;
        imageTimer ++;
        if (imageTimer>TIMERINTERVAL){
            currentImage++;
            if (currentImage >3){currentImage = 0;}
            imageTimer = 0;
        }

    }
}
