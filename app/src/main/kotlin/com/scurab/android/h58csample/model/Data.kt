package com.scurab.android.h58csample.model

import com.google.gson.annotations.SerializedName
import com.scurab.android.h58csample.component.HasParseableData
import com.scurab.android.h58csample.component.ILocaleHelper
import java.util.*

/**
 * Created by JBruchanov on 06/08/2017.
 */

data class PublicPhotos(
        val title: String,
        val link: String,
        val description: String,
        val modified: String,
        val items: List<Photo>
) : HasParseableData {
    override fun parse(localeHelper: ILocaleHelper) {
        items.forEach { it -> it.parse(localeHelper) }
    }
}

data class Photo(
        val title: String,
        val link: String,
        val media: Media,
        @SerializedName("date_taken") val dateTaken: String,
        @SerializedName("description") val descriptionHtml: String,
        val published: String,
        val author: String,
        @SerializedName("author_id") val authorId: String,
        val tags: String
) : HasParseableData {

    @Transient
    lateinit var dateTakenObj : Date
    lateinit var publishedObj : Date

    override fun parse(localeHelper: ILocaleHelper) {
        dateTakenObj = localeHelper.photoTakenFormat.parse(dateTaken)
        publishedObj = localeHelper.photoPublishedFormat.parse(published)
    }
}

data class Media(
        @SerializedName("m") val link: String
)