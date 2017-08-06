package com.scurab.android.h58csample.main.presenter

import com.scurab.android.h58csample.api.ServerApi
import com.scurab.android.h58csample.base.BasePresenter
import com.scurab.android.h58csample.base.BaseViewContract
import com.scurab.android.h58csample.component.ILogger
import com.scurab.android.h58csample.component.IRxSchedulers
import com.scurab.android.h58csample.main.MainComponent
import javax.inject.Inject

/**
 * Created by JBruchanov on 06/08/2017.
 */
interface IGlobalPhotosViewContract : BaseViewContract {

}

class GlobalPhotosPresenter(component: MainComponent? = null) : BasePresenter<IGlobalPhotosViewContract>() {

    private val TAG = "GlobalPhotosPresenter"

    @Inject lateinit var serverApi: ServerApi
    @Inject lateinit var schedulers: IRxSchedulers
    @Inject lateinit var logger: ILogger

    init {
        component?.inject(this)
    }

    override fun onResume() {
        super.onResume()
    }
}