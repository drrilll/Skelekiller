package com.example.darryl.app2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceView;

import static com.example.darryl.app2.GameThread.canvas;

public class SkeleSprite implements Drawable, Sprite{
    private Bitmap[] skelAttack, skelWalk, skelReact, skelIdle,skelHit,skelDead, image;
    int x,y, modx, mody, numImages, currentImage, imageTimer;
    public static int TIMERINTERVAL = 4;
    private static int WALKFRAMES = 13;
    private static int ATTACKFRAMES = 18;
    private static int REACTFRAMES = 4;
    private static int IDLEFRAMES = 11;
    private static int HITFRAMES = 8;
    private static int DEADFRAMES = 15;

    public enum Action {idle, react, attack, hit, dead, walkleft, walkright};

    Action action;




    /** Initialize our sprite
     *
     * @param resources
     */
    public SkeleSprite(Resources resources){
        x = 100; y = 100; currentImage = 0;
        imageTimer = 0;
        Bitmap map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_attack);
        skelAttack = new Bitmap[ATTACKFRAMES];
        skelReact  = new Bitmap[REACTFRAMES];
        skelWalk   = new Bitmap[WALKFRAMES];
        skelDead = new Bitmap[DEADFRAMES];
        skelIdle  = new Bitmap[IDLEFRAMES];
        skelHit   = new Bitmap[HITFRAMES];
        int interval = map.getWidth()/ATTACKFRAMES;
        for (int i = 0; i<ATTACKFRAMES ; i++){
            skelAttack[i] = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeletonreact);
        interval = map.getWidth()/REACTFRAMES;
        for (int i = 0; i<REACTFRAMES ; i++){
            skelReact[i] = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_walk);
        interval = map.getWidth()/WALKFRAMES;
        for (int i = 0; i<WALKFRAMES ; i++){
            skelWalk[i] = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_dead);
        interval = map.getWidth()/DEADFRAMES;
        for (int i = 0; i<DEADFRAMES ; i++){
            skelDead[i] = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_idle);
        interval = map.getWidth()/IDLEFRAMES;
        for (int i = 0; i<IDLEFRAMES ; i++){
            skelIdle[i] = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_hit);
        interval = map.getWidth()/HITFRAMES;
        for (int i = 0; i<HITFRAMES ; i++) {
            skelHit[i] = Bitmap.createBitmap(map, i * interval, 0, interval, map.getHeight());
        }
        numImages = DEADFRAMES;
        image = skelDead;
        modx=0;mody=0;
    }

    public void setAction(Action newAction) {
        this.action = newAction;
        switch (action) {
            case hit:
                image = skelHit;
                numImages = HITFRAMES;
                break;
            case dead:
                image = skelDead;
                numImages = DEADFRAMES;
                break;
            case idle:
                image = skelIdle;
                numImages = IDLEFRAMES;
                modx=0;
                mody=0;
                break;
            case walkleft:
            case walkright:
                image = skelWalk;
                numImages = WALKFRAMES;
                break;
            case react:
                image = skelReact;
                numImages = REACTFRAMES;
                break;
            case attack:
                image = skelAttack;
                numImages = ATTACKFRAMES;
                modx=-15;mody=-17;
        }
        imageTimer = 0;
        currentImage = 0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image[currentImage],x+modx,y+mody, null);
    }

    public void update(){
        if (action == Action.walkright) {
            x++;
        }
        if (action == Action.walkleft) {
            x--;
        }
        imageTimer ++;
        if (imageTimer>TIMERINTERVAL){
            currentImage++;
            if (currentImage >= numImages){
                currentImage = 0;
                if (action == Action.attack) {
                    setAction(Action.idle);
                }
            }
            imageTimer = 0;
        }

    }
}
