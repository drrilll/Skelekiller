package com.example.darryl.app2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Background implements Drawable, Sprite {
    Bitmap[] bground;
    int[] drawLeft;
    GameView gameView;
    int width;

    public Background(GameView gameView){
        this.gameView = gameView;
        drawLeft = new int[5];
        bground = new Bitmap[5];
        bground[0] = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plx1);
        bground[1] = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plx2);
        bground[2] = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plx3);
        bground[3] = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plx4);
        bground[4] = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.plx5);
        width = bground[0].getWidth();
    }

    public void draw(Canvas canvas){
        for (int i =0; i< 5; i++){
            for (int j =0; j<3; j++){
                canvas.drawBitmap(bground[i],drawLeft[i]+(j*width),0,null);
            }
        }
    }

    @Override
    public void update() {
        //need at least 3 frames each time

        //this should give us what location we would draw at 0.
        int loc = gameView.getHeroLocation().loc-500;

        //now we want to divide by background width and find the remainder
        for (int i = 0; i < 5; i++){
            drawLeft[i] = -((((loc)*(i+1))/5)%width);
        }




    }
}
