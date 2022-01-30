package com.app.grofiesta.ui.main.view.splash;

import android.os.Handler;

public class SplashRepository {

    long SplashTime = 4000;
    SplashHandler handler;

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            handler.onSplashCompleted();
        }
    };

    public SplashRepository(SplashHandler handler) {
        this.handler = handler;
    }

    public void onSplashInitiated() {
        new Handler().postDelayed(mRunnable, SplashTime);
    }

    public void removeHandler()
    {
    }

}
