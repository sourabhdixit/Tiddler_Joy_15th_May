package com.whizkidzmedia.tiddlerjoy.DataModels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.whizkidzmedia.tiddlerjoy.R;

/**
 * Created by Sourabh Dixit on 09-03-2016.
 */
public class GameView extends SurfaceView{

    Bitmap bmp ;
    SurfaceHolder holder;
    GameThreadLoop gameThreadLoop;
    int x=0;
    private int xSpeed = 1;
    private Sprite sprite;
    public GameView(Context context) {
        super(context);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setZOrderOnTop(true); //necessary
        gameThreadLoop = new GameThreadLoop(this);
        holder = getHolder();
        holder.setFormat(PixelFormat.TRANSPARENT);
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameThreadLoop.setRunning(true);
                gameThreadLoop.start();

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameThreadLoop.setRunning(false);
                while (retry) {
                    try {
                        gameThreadLoop.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.engin_sprit_new),480,800,false);
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.engin_sprite);
        sprite = new Sprite(this,bmp);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        sprite.onDraw(canvas);
    }
}
