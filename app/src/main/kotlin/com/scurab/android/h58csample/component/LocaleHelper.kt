package com.scurab.android.h58csample.component

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JBruchanov on 06/08/2017.
 */
interface ILocaleHelper {
    val photoTakenFormat: DateFormat
    val photoPublishedFormat: DateFormat
}

class LocaleHelper : ILocaleHelper {
    override val photoTakenFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())//2017-04-12T15:36:03-08:00
    override val photoPublishedFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())//2017-08-06T09:55:48Z
}