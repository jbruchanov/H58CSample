package com.scurab.android.h58csample.component

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler

/**
 * Created by JBruchanov on 06/08/2017.
 */
interface IRxSchedulers {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun cpu(): Scheduler
}

class AndroidRxSchedulers : IRxSchedulers {
    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun cpu(): Scheduler {
        return Schedulers.computation()
    }
}

class TextRxSchedulers : IRxSchedulers {

    private val scheduler = TestScheduler()
    override fun io(): TestScheduler {
        return scheduler
    }

    override fun ui(): TestScheduler {
        return scheduler
    }

    override fun cpu(): TestScheduler {
        return scheduler
    }
}