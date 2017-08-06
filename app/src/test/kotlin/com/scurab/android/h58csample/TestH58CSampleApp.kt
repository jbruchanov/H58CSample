package com.scurab.android.h58csample

import com.scurab.android.h58csample.component.JavaLocaleHelper
import com.scurab.android.h58csample.component.JavaLogger
import com.scurab.android.h58csample.component.TextRxSchedulers
import com.scurab.android.h58csample.module.AppModule

/**
 * Created by JBruchanov on 06/08/2017.
 */
class TestH58CSampleApp : H58CSampleApp() {


    override fun onCreateAppModule(): AppModule {
        return AppModule(this, JavaLogger(), TextRxSchedulers(), JavaLocaleHelper())
    }

    override fun onCreate() {
        super.onCreate()
    }

    override protected fun initStetho() {
        //donothing for tests
    }
}