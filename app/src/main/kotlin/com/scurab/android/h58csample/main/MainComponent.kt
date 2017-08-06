package com.scurab.android.h58csample.main

import com.scurab.android.h58csample.AppComponent
import com.scurab.android.h58csample.main.presenter.GlobalPhotosPresenter
import dagger.Component

/**
 * Created by JBruchanov on 06/08/2017.
 */
@Component(dependencies = arrayOf(AppComponent::class))
interface MainComponent {

    fun inject(globalPhotosPresenter: GlobalPhotosPresenter)
}