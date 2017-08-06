package com.scurab.android.h58csample.component

import android.util.Log
import com.scurab.android.h58csample.extension.stackTrace
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JBruchanov on 06/08/2017.
 */
interface ILogger {
    fun d(tag: String, msg: String)
    fun d(tag: String, msg: String, ex: Throwable) {
        d(tag, msg)
        d(tag, ex.message ?: "NullMessage")
        d(tag, "\n${ex.stackTrace()}")
    }
}

class AndroidLogger : ILogger {

    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }
}

class JavaLogger : ILogger {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

    override fun d(tag: String, msg: String) {
        val time = dateFormat.format(Date())
        System.out.println("[$time]:$tag $msg")
    }
}