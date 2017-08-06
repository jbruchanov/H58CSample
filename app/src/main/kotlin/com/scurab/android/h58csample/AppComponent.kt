package com.scurab.android.h58csample

import com.scurab.android.h58csample.api.ServerApi
import com.scurab.android.h58csample.component.ICache
import com.scurab.android.h58csample.component.ILocaleHelper
import com.scurab.android.h58csample.component.ILogger
import com.scurab.android.h58csample.component.IRxSchedulers
import com.scurab.android.h58csample.dagger.PerApp
import com.scurab.android.h58csample.module.AppModule
import com.scurab.android.h58csample.module.NetModule
import dagger.Component

/**
 * Created by JBruchanov on 06/08/2017.
 */

@PerApp
@Component(modules = arrayOf(AppModule::class, NetModule::class))
interface AppComponent {

    fun serverApi(): ServerApi
    fun schedulers(): IRxSchedulers
    fun logger(): ILogger
    fun cache(): ICache
    fun localeHelper(): ILocaleHelper
}