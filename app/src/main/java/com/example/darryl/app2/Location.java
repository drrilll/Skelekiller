package com.example.darryl.app2;

/**
 * The location of an object in the world. For now this is simply an x-coordinate between
 * 0 and 10 000.
 */
public class Location {
    int loc;
    static int DISTANCEFROMLEFT = 500;

    public Location(int loc) {
        this.loc = loc;
    }

    public Location(){
        this(0);
    }

    public int getLoc() {
        return loc;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getDrawLocation(Location hero){
        if ((loc > hero.getLoc() - 700)&&(loc < hero.loc +1000)){
            return loc - DISTANCEFROMLEFT;
        }else{
            return -1;
        }
    }
}
