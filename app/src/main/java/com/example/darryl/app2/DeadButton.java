package com.example.darryl.app2;

import android.graphics.Color;

public class DeadButton extends Button {
    public DeadButton(int x, int y){
        super(x,y,100,100, Color.rgb(0,0,250));
        this.x = x;
        this.y = y;
    }
}
