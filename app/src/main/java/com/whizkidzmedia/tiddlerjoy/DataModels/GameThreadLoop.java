package com.whizkidzmedia.tiddlerjoy.DataModels;

import android.graphics.Canvas;

/**
 * Created by Sourabh Dixit on 09-03-2016.
 */
public class GameThreadLoop extends Thread {

    static final long FPS = 24;
    private GameView view;
    private boolean running = false;
    public GameThreadLoop(GameView view)
    {
        this.view=view;
    }

    public void setRunning(boolean run)
    {
        this.running=run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime = 0;
        long sleepTime;
        while(running)
        {
            Canvas c = null;
            try{
                c = view.getHolder().lockCanvas(null);
                synchronized (view.getHolder()){
                    view.onDraw(c);
                }
            }finally {
                if(c!=null)
                    view.getHolder().unlockCanvasAndPost(c);
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }
}
