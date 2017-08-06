package com.scurab.android.h58csample

import android.app.Application
import com.facebook.stetho.Stetho
import com.scurab.android.h58csample.component.AndroidLogger
import com.scurab.android.h58csample.component.AndroidRxSchedulers
import com.scurab.android.h58csample.h58csample.BuildConfig
import com.scurab.android.h58csample.module.AppModule

/**
 * Created by JBruchanov on 06/08/2017.
 */
open class H58CSampleApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(onCreateAppModule())
                .build()
    }

    internal open fun onCreateAppModule(): AppModule {
        return AppModule(this, AndroidLogger(), AndroidRxSchedulers())
    }

    override fun onCreate() {
        super.onCreate()
        initStetho()
    }

    protected open fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                            .build())
        }
    }

}