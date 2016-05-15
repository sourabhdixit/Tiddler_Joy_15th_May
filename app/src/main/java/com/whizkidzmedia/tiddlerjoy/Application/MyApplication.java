package com.whizkidzmedia.tiddlerjoy.Application;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.activeandroid.app.Application;

/**
 * Created by Sourabh Dixit on 21-03-2016.
 */
public class MyApplication extends com.activeandroid.app.Application {



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
