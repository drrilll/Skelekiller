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
    SkeleSprite[] skeleSprites;
    AdventSprite advSprite;
    Background background;
    World world;
    private ArrayList<Drawable> drawables;
    private ArrayList<Sprite> updateables;
    private Button attack1, attack2, runleft, runright, block, slide;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(getHolder(), this);
        setFocusable(true);
        drawables = new ArrayList<>();
        updateables = new ArrayList<>();
        block = new Button(100,1000, 100, Color.rgb(0, 0,250),"Block");
        attack1 = new Button(block.getRightMostPoint()+50 ,1000, 100, Color.rgb(250, 0,0),"Attack1");
        attack2 = new Button(attack1.getRightMostPoint()+50,1000, 100, Color.rgb(250, 0,0),"Attack2");
        runleft = new Button(attack2.getRightMostPoint()+50,1000, 100, Color.rgb(0, 250,0),"<-Jump");
        runright = new Button(runleft.getRightMostPoint()+50,1000, 100, Color.rgb(0, 250,0),"Run->");
        slide = new Button(runright.getRightMostPoint()+50,1000, 100, Color.rgb(0, 250,0),"Slide->");
        world = new World();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (attack1.checkTouched(event)){
           advSprite.setAction(AdventSprite.AdvAction.att1);
        }else if (runleft.checkTouched(event)){
           advSprite.setAction(AdventSprite.AdvAction.jumproll);
        }else if (runright.checkTouched(event)){
           advSprite.setAction(AdventSprite.AdvAction.runright);
        }else if (attack2.checkTouched(event)){
           advSprite.setAction(AdventSprite.AdvAction.att2);
        }else if (block.checkTouched(event)){
           advSprite.setAction(AdventSprite.AdvAction.block);
        }else if (slide.checkTouched(event)){
            advSprite.setAction(AdventSprite.AdvAction.slide);
        }else{
           advSprite.setAction(AdventSprite.AdvAction.idlesw);
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder){
        background = new Background(this);
        drawables.add(background);
        updateables.add(background);
        advSprite = new AdventSprite(this);
        advSprite.setAction(AdventSprite.AdvAction.idle);
        drawables.add(advSprite);
        updateables.add(advSprite);
        skeleSprites = new SkeleSprite[4];
        for (int i = 0; i< 4; i++){
            skeleSprites[i] = new SkeleSprite(this,new Location(i*2000 +1000));
            skeleSprites[i].setAction(SkeleSprite.Action.walkleft);
            drawables.add(skeleSprites[i]);
            updateables.add(skeleSprites[i]);
        }

        drawables.add(attack1);
        drawables.add(attack2);
        drawables.add(runleft);
        drawables.add(runright);
        drawables.add(block);
        drawables.add(slide);
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
        for (Sprite sprite: updateables){
            sprite.update();
        }
    }

    public Location getHeroLocation(){
        return advSprite.getLocation();
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

    /* In this section we resolve attacks. These are callback functions used by our sprites.
     * A lot of hardcoding, but we will abstract later */

    public void advAttack(){
        for (SkeleSprite skeleSprite: skeleSprites) {
            if (skeleSprite.action != SkeleSprite.Action.dead) {
                if ((skeleSprite.x - advSprite.x) < 150) {
                    skeleSprite.setAction(SkeleSprite.Action.dead);
                }
            }
        }
    }

    public void skelAttack(SkeleSprite skeleSprite){
        if (((skeleSprite.x - advSprite.x)< 150)&&(advSprite.action != AdventSprite.AdvAction.block)
                &&(advSprite.action != AdventSprite.AdvAction.slide)){
            advSprite.setAction(AdventSprite.AdvAction.die);
        }
    }
}
