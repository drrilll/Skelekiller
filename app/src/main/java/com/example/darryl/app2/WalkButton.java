package com.example.darryl.app2;

import android.graphics.Color;

public class WalkButton extends Button {
    public WalkButton(int x, int y){
        super(x,y,100,100, Color.rgb(0,250,0));
        this.x = x;
        this.y = y;
    }
}
