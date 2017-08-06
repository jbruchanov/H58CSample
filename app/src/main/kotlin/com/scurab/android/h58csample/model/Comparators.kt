package com.scurab.android.h58csample.model

/**
 * Created by JBruchanov on 06/08/2017.
 */
object Comparators {
    val PhotoSortByPublished = Comparator<Photo> { p0, p1 -> p0.publishedObj.compareTo(p1.publishedObj) }
    val PhotoSortByTaken = Comparator<Photo> { p0, p1 -> p0.dateTakenObj.compareTo(p1.dateTakenObj) }
}