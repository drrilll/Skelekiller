package com.example.darryl.app2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;


public class AdventSprite implements Drawable, Sprite{
        private Bitmap[][][] adventurer; //[right/left][state][frame]
        private static int ADVSTATES = 13;
        private static int MAXFRAMES = 9;
        private static int SPEED = 6;
        private Location location;


        private int[] frames = {4,4,6,8,7,9,4,6,5,6,3,4,4};

        int loc, x, y, modx, mody, numImages, currentImage, imageTimer, facing;
        private static int TIMERINTERVAL = 3, RIGHT = 0, LEFT =1;

        public enum AdvAction {idle, crouch, runright, jumproll, slide, climb, idlesw, att1, att2, att3, block, die, jump};
        GameView gameView;

        AdvAction action;




        /** Initialize our sprite
         *
         */
    public AdventSprite(GameView view){
            gameView = view;
            loc = 0;
            x = 500; y = 500; currentImage = 0;
            modx = 0; mody=-15;
            imageTimer = 0;
            location = new Location(500);
            Bitmap map = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.adventurer);
            adventurer = new Bitmap[2][ADVSTATES][MAXFRAMES];
            Bitmap temp;
            Matrix matrix = new Matrix();
            float cx= 0,cy=0;
            int frameCount = 0, columns = 7, width = map.getWidth()/columns, height = map.getHeight()/11;
            for (int i=0; i< ADVSTATES; i++){
                for (int j = 0; j < MAXFRAMES; j++){
                    if (j<frames[i]){
                        int currentRow = frameCount/columns;
                        int currentColumn = frameCount - (currentRow*columns);
                        int xindex = currentColumn*width;
                        int yindex = currentRow*height;
                        adventurer[RIGHT][i][j] = Bitmap.createBitmap(map, xindex, yindex,
                                width, height);
                        temp = adventurer[RIGHT][i][j];
                        cx = temp.getWidth()/2;
                        cy = temp.getHeight()/2;
                        matrix.reset();
                        matrix.postScale(-1,1,cx,cy);
                        adventurer[LEFT][i][j] = Bitmap.createBitmap(temp,0 ,0,temp.getWidth(), temp.getHeight(), matrix, true);
                        frameCount ++;
                    }
                }
            }
            // we want to repeat some frames for block
            adventurer[RIGHT][AdvAction.block.ordinal()][1]= adventurer[RIGHT][AdvAction.block.ordinal()][0];
            adventurer[RIGHT][AdvAction.block.ordinal()][2]= adventurer[RIGHT][AdvAction.block.ordinal()][0];
            facing = RIGHT;

            /*
             * Here we make all the left facing versions.
             */
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

        }

        public Location getLocation(){
            return location;
        }


        public void draw(Canvas canvas){
            canvas.drawBitmap(adventurer[facing][action.ordinal()][currentImage],x+modx,y+mody, null);
        }

        public void update() {
            if ((action == AdvAction.runright)||(action == AdvAction.slide)){
                location.loc+=SPEED;
            }else if (action == AdvAction.jumproll) {
                if (currentImage > 1) {
                    location.loc -= SPEED;
                }
            }
            imageTimer ++;
            if (imageTimer>TIMERINTERVAL){
                currentImage++;
                //check if we are in the hitting phase of some attack, which is halfway through
                //the swing
                if ((action == AdvAction.att1)||(action == AdvAction.att2)||(action == AdvAction.att3)){
                    if (currentImage == frames[action.ordinal()]/2) {
                        gameView.advAttack();
                    }
                }
                if (currentImage >= frames[action.ordinal()]){
                    currentImage = 0;
                    if ((action != AdvAction.jump)&&(action != AdvAction.runright)) {
                        setAction(AdvAction.idlesw);
                    }

                }
                imageTimer = 0;
            }
        }
    }


