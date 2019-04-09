package com.example.darryl.app2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

    private Canvas mCanvas;
    private Bitmap mBitmap;
    private ImageView mView;
    private Paint mPaint = new Paint();
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG);
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
        /*
        mView = (ImageView) findViewById(R.id.imageView);
        TextView tView = (TextView)findViewById(R.id.textView);
        int vWidth = 300;
        int vHeight = 300;
        tView.setText(Integer.toString(vWidth)+Integer.toString(vHeight));
        int halfWidth = vWidth / 2;
        int halfHeight = vHeight / 2;

        mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
        //Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        //Bitmap bmp = Bitmap.createBitmap(vWidth, vHeight, conf);

        mView.setImageBitmap(mBitmap);
        mCanvas = new Canvas(mBitmap);
        mPaintText.setColor(
                ResourcesCompat.getColor(getResources(),
                        R.color.colorPrimaryDark, null)
        );
        mPaintText.setTextSize(70);
        mCanvas.drawText("blablabla", 100, 100, mPaintText);*/


    }

    /*
    public void onTouch(View view){
        mCanvas.drawRect(new Rect(0,0,300,300), new Paint(Color.WHITE));

        mCanvas.drawText("eat it"+count++, 0, 100, mPaintText);

    }*/
}
