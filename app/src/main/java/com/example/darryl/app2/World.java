package com.example.darryl.app2;

/**
 * This class is the model for the world. It keeps track of locations and probably
 * where everything should draw itself.
 */
public class World {
    Location hero;
    Location[] skeletons;

    public World(){
        hero = new Location(500);
        skeletons = new Location[4];
        for (int i = 0; i< 4; i++){
            skeletons[i] = new Location(i*2000 +1000);
        }
    }
}
