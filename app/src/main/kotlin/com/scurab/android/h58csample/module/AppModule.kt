package com.scurab.android.h58csample.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.scurab.android.h58csample.H58CSampleApp
import com.scurab.android.h58csample.component.*
import com.scurab.android.h58csample.dagger.PerApp
import dagger.Module
import dagger.Provides

/**
 * Created by JBruchanov on 06/08/2017.
 */
@PerApp
@Module
class AppModule(val app: H58CSampleApp,
                val logger: ILogger,
                var rxSchedulers: IRxSchedulers,
                var localeHelper: ILocaleHelper) {
    @PerApp
    @Provides
    fun provideApp(): H58CSampleApp {
        return app
    }

    @PerApp
    @Provides
    fun provideLogger(): ILogger {
        return logger
    }

    @PerApp
    @Provides
    fun provideSchedulers(): IRxSchedulers {
        return rxSchedulers
    }

    @PerApp
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
                .create()
    }

    @PerApp
    @Provides
    fun provideLocaleHelper(): ILocaleHelper {
        return localeHelper
    }

    @PerApp
    @Provides
    fun provideCache(): ICache {
        return AndroidCache()
    }
}