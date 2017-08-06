package com.scurab.android.h58csample.component

import io.reactivex.ObservableTransformer
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JBruchanov on 06/08/2017.
 */

interface HasParseableData {
    fun parse(localeHelper: ILocaleHelper)
}

interface ILocaleHelper {
    val photoTakenFormat: DateFormat
    val photoPublishedFormat: DateFormat
    val mediumDateMediumTimeFormat : DateFormat

    fun <R> transformer(): ObservableTransformer<R, R> {
        return ObservableTransformer { upstream ->
            upstream.map { it ->
                if (it is HasParseableData) {
                    it.parse(this)
                }
                it
            }
        }
    }
}

open class AndroidLocaleHelper : ILocaleHelper {
    override val photoTakenFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())//2017-04-12T15:36:03-08:00
    override val photoPublishedFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())//2017-08-06T09:55:48Z

    //TODO: locale change not handled
    override val mediumDateMediumTimeFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.MEDIUM, SimpleDateFormat.MEDIUM, Locale.getDefault())!!
}

class JavaLocaleHelper : AndroidLocaleHelper() {
    override val photoTakenFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())//java vs android difference
}