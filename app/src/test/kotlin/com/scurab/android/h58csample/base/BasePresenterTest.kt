package com.scurab.android.h58csample.base

import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test

/**
 * Created by JBruchanov on 06/08/2017.
 */
class BasePresenterTest {

    @Test()
    fun testLifecycleStartDoesntStopObservable() {
        val presenter = object : BasePresenter<BaseViewContract>() {}

        val testScheduler = TestScheduler()
        val test = Observable
                .just(1)
                .subscribeOn(testScheduler)
                .compose(presenter.bindToLifecycle<Int>())
                .test()

        presenter.onResume()
        testScheduler.triggerActions()
        test.assertValue(1)
        test.assertTerminated()
    }

    @Test()
    fun testLifecycleStopDoesStopObservable() {
        val presenter = object : BasePresenter<BaseViewContract>() {}

        val testScheduler = TestScheduler()
        val test = Observable
                .just(1)
                .subscribeOn(testScheduler)
                .compose(presenter.bindToLifecycle<Int>())
                .test()

        presenter.onPause()
        testScheduler.triggerActions()
        test.assertNever(1)
        test.assertTerminated()
    }
}