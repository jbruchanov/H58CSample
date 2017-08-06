package com.scurab.android.h58csample.base

import android.support.annotation.CallSuper
import com.scurab.android.h58csample.component.Constants
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * Created by JBruchanov on 06/08/2017.
 */
interface IViewContract

open class BasePresenter<T : IViewContract> {

    private var _viewContract: T? = null
    protected val viewContract: T get() = _viewContract!!

    private val runningSubject = PublishSubject.create<Boolean>()

    @CallSuper
    open fun onAttachViewContract(viewContract: T) {
        _viewContract = viewContract
    }

    @CallSuper
    open fun onDetachViewContract() {
        _viewContract = null
    }

    @CallSuper
    open fun onResume() {
        runningSubject.onNext(true)
    }

    @CallSuper
    open fun onPause() {
        runningSubject.onNext(false)
    }

    fun <R> bindToLifecycle(): ObservableTransformer<R, R> {
        return ObservableTransformer { upstream ->
            upstream.takeUntil(runningSubject.filter { v -> !v })
        }
    }

    fun <R> bindToProgressBarVisibility(pbarHolder: HasProgressBar): ObservableTransformer<R, R> {
        return ObservableTransformer { upstream ->
            var subscribe: Disposable? = null
            upstream
                    .doOnSubscribe {
                        subscribe = Observable
                                .timer(Constants.loadingProgressBarDelay, TimeUnit.MILLISECONDS)
                                .subscribe { pbarHolder.progressBarVisible = true }
                    }
                    .doFinally {
                        subscribe?.dispose()
                        pbarHolder.progressBarVisible = false
                    }
        }
    }
}