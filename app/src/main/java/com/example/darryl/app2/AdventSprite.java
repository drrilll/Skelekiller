package com.example.darryl.app2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class AdventSprite implements Drawable, Sprite{
        private Bitmap[][] adventurer;
        private static int ADVSTATES = 13;
        private static int MAXFRAMES = 9;
        private int[] frames = {4,4,6,8,7,9,4,6,5,6,5,4,4};


        int x,y, modx, mody, numImages, currentImage, imageTimer;
        private static int TIMERINTERVAL = 6;

        public enum AdvAction {idle, crouch, run, jumproll, slide, climb, idlesw, att1, att2, att3, hit, die, jump};

        AdvAction action;




        /** Initialize our sprite
         *
         * @param resources
         */
    public AdventSprite(Resources resources){
            x = 200; y = 100; currentImage = 0;
            modx = 0; mody=0;
            imageTimer = 0;
            Bitmap map = BitmapFactory.decodeResource(resources, R.drawable.adventurer);
            adventurer = new Bitmap[ADVSTATES][MAXFRAMES];
            /*int columns = 7, width = map.getWidth()/columns, height = map.getHeight()/11;
            for (int i = 0; i < 6; i++){
                adventurer[AdvAction.att1.ordinal()][i] = Bitmap.createBitmap(map, i*width, 6*height, width, height);
            }*/

            int frameCount = 0, columns = 7, width = map.getWidth()/columns, height = map.getHeight()/11;
            for (int i=0; i< ADVSTATES; i++){
                for (int j = 0; j < MAXFRAMES; j++){
                    if (j<frames[i]){
                        int currentRow = frameCount/columns;
                        int currentColumn = frameCount - (currentRow*columns);
                        int xindex = currentColumn*width;
                        int yindex = currentRow*height;
                        adventurer[i][j] = Bitmap.createBitmap(map, xindex, yindex,
                                width, height);
                        frameCount ++;
                    }
                }
            }
        }

        public void setAction(AdvAction action){
            this.action = action;
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
            imageTimer ++;
            if (imageTimer>TIMERINTERVAL){
                currentImage++;
                if (currentImage >= frames[action.ordinal()]){
                    currentImage = 0;
                    //if (action == AdvAction.att1) {
                    //    setAction(AdvAction.idle);
                    //}
                }
                imageTimer = 0;
            }
            canvas.drawBitmap(adventurer[action.ordinal()][currentImage],x+modx,y+mody, null);
        }

        public void update() {
        }
    }


