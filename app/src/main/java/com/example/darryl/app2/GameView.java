package com.example.darryl.app2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private SkeleSprite skeleSprite;
    private AdventSprite advSprite;
    private ArrayList<Drawable> drawables;
    private AttackButton abutton;
    private WalkButton wbutton, wlbutton;
    private DeadButton dbutton;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
        drawables = new ArrayList<>();
        abutton = new AttackButton(500,1000);
        wbutton = new WalkButton(700,1000);
        dbutton = new DeadButton(900,1000);
        wlbutton = new WalkButton(300,1000);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (abutton.checkTouched(event)){
            skeleSprite.setAction(SkeleSprite.Action.dead);
        }else if (wbutton.checkTouched(event)){
            skeleSprite.setAction(SkeleSprite.Action.walkright);
        }else if (dbutton.checkTouched(event)){
            skeleSprite.setAction(SkeleSprite.Action.attack);
        }else if (wlbutton.checkTouched(event)){
            skeleSprite.setAction(SkeleSprite.Action.walkleft);
        }else{
            skeleSprite.setAction(SkeleSprite.Action.idle);
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        skeleSprite = new SkeleSprite(getResources());
        advSprite = new AdventSprite(getResources());
        skeleSprite.setAction(SkeleSprite.Action.dead);
        drawables.add(skeleSprite);
        drawables.add(advSprite);
        drawables.add(abutton);
        drawables.add(wbutton);
        drawables.add(dbutton);
        drawables.add(wlbutton);
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
            for (Drawable drawable:drawables) {
                drawable.draw(canvas);
            }
        }
    }
}
