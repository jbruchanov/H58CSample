package com.scurab.android.h58csample.main

import com.scurab.android.h58csample.dagger.PerActivity
import dagger.Module
import dagger.Provides

/**
 * Created by JBruchanov on 06/08/2017.
 */
@PerActivity
@Module
class MainModule(val navigator: INavigator) {

    @Provides
    fun provideNavigator(): INavigator {
        return navigator
    }
}