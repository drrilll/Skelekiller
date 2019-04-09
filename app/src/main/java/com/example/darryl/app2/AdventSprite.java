package com.example.darryl.app2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class AdventSprite implements Drawable, Sprite{
        private Bitmap[] advAttack;
        int x,y, modx, mody, numImages, currentImage, imageTimer;
        public static int TIMERINTERVAL = 4;
        private static int WALKFRAMES = 13;
        private static int ATTACKFRAMES = 18;
        private static int REACTFRAMES = 4;
        private static int IDLEFRAMES = 11;
        private static int HITFRAMES = 8;
        private static int DEADFRAMES = 15;

        public enum Action {idle, react, attack, hit, dead, walkleft, walkright};

        AdventSprite.Action action;




        /** Initialize our sprite
         *
         * @param resources
         */
    public AdventSprite(Resources resources){
            x = 200; y = 100; currentImage = 0;
            imageTimer = 0;
            Bitmap map = BitmapFactory.decodeResource(resources, R.drawable.adventurerattack100);
            advAttack = new Bitmap[ATTACKFRAMES];

            advAttack[0] = Bitmap.createBitmap(map,0 ,0,map.getWidth(), map.getHeight());

        }
/*
        public void setAction(SkeleSprite.Action newAction) {
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
        }*/

        public void draw(Canvas canvas){
            canvas.drawBitmap(advAttack[currentImage],x+modx,y+mody, null);
        }

        public void update() {
        }
    }


