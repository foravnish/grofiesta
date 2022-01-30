package com.app.grofiesta

import android.app.Application

class App : Application() {

    //Force Crash
    //Crashlytics.getInstance().crash()

    companion object {
        lateinit var instance: Application
        const val LANGUAGE_ENGLISH = "en"
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

//        AppLog.LOG_ENABLED = BuildConfig.BUILD_TYPE.contentEquals("debug")

    }

}
