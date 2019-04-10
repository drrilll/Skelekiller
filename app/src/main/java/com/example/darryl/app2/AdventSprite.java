package com.example.darryl.app2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

public class AdventSprite implements Drawable, Sprite{
        private Bitmap[][] adventurer;
        private static int ADVSTATES = 13;
        private static int MAXFRAMES = 9;

        //keep in mind, this goes into two blank frames, which is ok for now I think, but not
        //a real good practice to have meaningless dangling into empty bitmaps like this. That is
        // the last value should be 4, but we need it as 6
        private int[] frames = {4,4,6,8,7,9,4,6,5,6,3,4,4};

        int x, y, modx, mody, numImages, currentImage, imageTimer;
        private static int TIMERINTERVAL = 6;

        public enum AdvAction {idle, crouch, runright, jumproll, slide, climb, idlesw, att1, att2, att3, block, die, jump};
        GameView gameView;

        AdvAction action;




        /** Initialize our sprite
         *
         */
    public AdventSprite(GameView view){
            gameView = view;
            x = 200; y = 100; currentImage = 0;
            modx = 0; mody=-15;
            imageTimer = 0;
            Bitmap map = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.adventurer);
            adventurer = new Bitmap[ADVSTATES][MAXFRAMES];

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
        // we want to repeat some frames for block
        adventurer[AdvAction.block.ordinal()][1]= adventurer[AdvAction.block.ordinal()][0];
        adventurer[AdvAction.block.ordinal()][2]= adventurer[AdvAction.block.ordinal()][0];
        // we want this to be "runleft" for now
        //adventurer[AdvAction.runleft.ordinal()] = adventurer[AdvAction.runright.ordinal()];
        }



        public void setAction(AdvAction action){

            if ((action == AdvAction.att1)||(action == AdvAction.att2)||(action == AdvAction.att3)){
                if (this.action == AdvAction.die){
                    return;
                }
            }
            this.action = action;
            imageTimer = 0;
            currentImage = 0;
            /*
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
            }*/

        }


        public void draw(Canvas canvas){
            canvas.drawBitmap(adventurer[action.ordinal()][currentImage],x+modx,y+mody, null);
        }

        public void update() {
            if (action == AdvAction.runright){
                x+=2;
            }else if (action == AdvAction.jumproll) {
                if (currentImage > 1) {
                    x -= 2;
                }
            }
            imageTimer ++;
            if (imageTimer>TIMERINTERVAL){
                currentImage++;
                if (currentImage >= frames[action.ordinal()]){
                    currentImage = 0;
                    if ((action == AdvAction.att1)||(action == AdvAction.att2)||(action == AdvAction.att3)){
                        gameView.advAttack();
                    }
                    if ((action != AdvAction.jump)&&(action != AdvAction.runright)) {
                        setAction(AdvAction.idlesw);
                    }

                }
                imageTimer = 0;
            }
        }
    }


