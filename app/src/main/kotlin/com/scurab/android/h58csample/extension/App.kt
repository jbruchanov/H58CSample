package com.scurab.android.h58csample.extension

import android.content.Context
import android.os.Looper
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import com.scurab.android.h58csample.H58CSampleApp
import java.io.PrintWriter
import java.io.StringWriter

/**
 * Created by JBruchanov on 06/08/2017.
 */
fun Context.app(): H58CSampleApp = this.applicationContext as H58CSampleApp

inline fun FragmentManager.transaction(crossinline op: FragmentTransaction.() -> Unit) {
    val transaction = this.beginTransaction()
    op(transaction)
    transaction.commit()
}

fun Throwable.stackTrace(): String {
    val writer = StringWriter()
    this.printStackTrace(PrintWriter(writer))
    return writer.toString()
}

fun View.setVisibilitySafe(visible: Boolean) {
    postIfNecessary { visibility = if (visible) View.VISIBLE else View.GONE }
}

fun SwipeRefreshLayout.setIsRefreshingSafe(refreshing: Boolean) {
    postIfNecessary { isRefreshing = refreshing }
}

fun View.postIfNecessary(func: () -> Unit) {
    if (Looper.getMainLooper() == Looper.myLooper()) {
        func()
    } else {
        post { func() }
    }
}
