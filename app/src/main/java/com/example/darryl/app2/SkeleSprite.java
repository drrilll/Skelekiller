package com.example.darryl.app2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.app.NotificationCompat;


public class SkeleSprite implements Drawable, Sprite{
    private Bitmap[] skelAttack, skelWalk, skelReact, skelIdle,skelHit,skelDead, image;
    int x,y, modx, mody, numImages, currentImage, imageTimer;
    public static int TIMERINTERVAL = 2;
    private static int WALKFRAMES = 13;
    private static int ATTACKFRAMES = 18;
    private static int REACTFRAMES = 4;
    private static int IDLEFRAMES = 11;
    private static int HITFRAMES = 8;
    private static int DEADFRAMES = 15;
    private static int SPEED = 2;
    private AdventSprite adventurer;
    GameView gameView;
    static private int range = 200;
    public enum Action {idle, react, attack, hit, dead, walkleft, walkright};
    int hitPoint = 7;
    Action action;
    Location location;




    /** Initialize our sprite
     *
     */
    public SkeleSprite(GameView view, Location location){
        this.adventurer = view.advSprite;
        this.location = location;
        gameView = view;
        x = 500; y = 500; currentImage = 0;
        imageTimer = 0;
        Resources resources = gameView.getResources();
        Bitmap map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_attack);
        Bitmap temp;
        Matrix matrix = new Matrix();
        float cx= 0,cy=0;
        skelAttack = new Bitmap[ATTACKFRAMES];
        skelReact  = new Bitmap[REACTFRAMES];
        skelWalk   = new Bitmap[WALKFRAMES];
        skelDead = new Bitmap[DEADFRAMES];
        skelIdle  = new Bitmap[IDLEFRAMES];
        skelHit   = new Bitmap[HITFRAMES];
        int interval = map.getWidth()/ATTACKFRAMES;
        for (int i = 0; i<ATTACKFRAMES ; i++){
            temp = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
            cx = temp.getWidth()/2;
            cy = temp.getHeight()/2;
            matrix.reset();
            matrix.postScale(-1,1,cx,cy);
            skelAttack[i] = Bitmap.createBitmap(temp,0 ,0,temp.getWidth(), temp.getHeight(), matrix, true);
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeletonreact);
        interval = map.getWidth()/REACTFRAMES;
        for (int i = 0; i<REACTFRAMES ; i++){
            temp = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
            cx = temp.getWidth()/2;
            cy = temp.getHeight()/2;
            matrix.reset();
            matrix.postScale(-1,1,cx,cy);
            skelReact[i] = Bitmap.createBitmap(temp,0 ,0,temp.getWidth(), temp.getHeight(), matrix, true);
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_walk);
        interval = map.getWidth()/WALKFRAMES;
        for (int i = 0; i<WALKFRAMES ; i++){
            temp = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
            cx = temp.getWidth()/2;
            cy = temp.getHeight()/2;
            matrix.reset();
            matrix.postScale(-1,1,cx,cy);
            skelWalk[i] = Bitmap.createBitmap(temp,0 ,0,temp.getWidth(), temp.getHeight(), matrix, true);
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_dead);
        interval = map.getWidth()/DEADFRAMES;
        for (int i = 0; i<DEADFRAMES ; i++){
            temp = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
            cx = temp.getWidth()/2;
            cy = temp.getHeight()/2;
            matrix.reset();
            matrix.postScale(-1,1,cx,cy);
            skelDead[i] = Bitmap.createBitmap(temp,0 ,0,temp.getWidth(), temp.getHeight(), matrix, true);
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_idle);
        interval = map.getWidth()/IDLEFRAMES;
        for (int i = 0; i<IDLEFRAMES ; i++){
            temp = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
            cx = temp.getWidth()/2;
            cy = temp.getHeight()/2;
            matrix.reset();
            matrix.postScale(-1,1,cx,cy);
            skelIdle[i] = Bitmap.createBitmap(temp,0 ,0,temp.getWidth(), temp.getHeight(), matrix, true);
        }
        map = BitmapFactory.decodeResource(resources, R.drawable.skeleton_hit);
        interval = map.getWidth()/HITFRAMES;
        for (int i = 0; i<HITFRAMES ; i++) {
            temp = Bitmap.createBitmap(map,i*interval ,0,interval, map.getHeight());
            cx = temp.getWidth()/2;
            cy = temp.getHeight()/2;
            matrix.reset();
            matrix.postScale(-1,1,cx,cy);
            skelHit[i] = Bitmap.createBitmap(temp,0 ,0,temp.getWidth(), temp.getHeight(), matrix, true);
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
                modx=0;
                mody=0;
                break;
            case dead:
                image = skelDead;
                numImages = DEADFRAMES;
                modx=15;
                mody=0;
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
                modx=0;
                mody=0;
                break;
            case react:
                image = skelReact;
                numImages = REACTFRAMES;
                modx=0;
                mody=0;
                break;
            case attack:
                image = skelAttack;
                numImages = ATTACKFRAMES;
                modx=-55;mody=-17;
        }
        imageTimer = 0;
        currentImage = 0;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(image[currentImage],x+modx,y+mody, null);
    }

    /**
     * We will implement AI here. Skeleton walks left until in range, then attacks, and if hit, dies
     */
    public void update(){
        if ((action !=Action.dead)&&(action != Action.attack)){
            if (Math.abs(adventurer.getLocation().loc - location.loc)<range){
                setAction(Action.attack);
            }
        }
        if (action == Action.walkright) {
            location.loc+=SPEED;
        }
        if (action == Action.walkleft) {
            location.loc-=SPEED;
        }
        imageTimer ++;
        if (imageTimer>TIMERINTERVAL){
            currentImage++;
            if (action == Action.attack) {
                if (currentImage == hitPoint) {
                    gameView.skelAttack(this);
                }
            }
            if (currentImage >= numImages){
                currentImage = 0;
                if (action == Action.attack) {
                    location.loc-=10;
                    setAction(Action.walkleft);
                }else if (action == Action.dead){
                    // a hacky way to stay dead
                    currentImage = numImages -1;
                    /*if(deathTimer++ > 30){
                        deathTimer = 0;
                        x = 1500;
                        setAction(Action.walkleft);
                    }*/
                }
            }
            imageTimer = 0;
        }
        x = location.loc - gameView.getHeroLocation().loc + 500;
    }
}
