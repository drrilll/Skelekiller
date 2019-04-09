package com.example.darryl.app2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private SkeleSprite skeleSprite;
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Bitmap map = BitmapFactory.decodeResource(getResources(), R.drawable.skeletonreact);
        Bitmap[] allskels = new Bitmap[4];
        int interval = map.getWidth()/4;
        for (int i = 0; i<4 ; i++){
            allskels[i] = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
        }
        skeleSprite = new SkeleSprite(allskels);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                gameThread.setRunning(false);
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        skeleSprite.update();
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null){
            //canvas.drawColor(Color.WHITE);
            //Paint paint = new Paint();
            //paint.setColor(Color.rgb(250,0,0));
            //canvas.drawRect(100,100,200,200,paint);
            skeleSprite.draw(canvas);
        }
    }
}
