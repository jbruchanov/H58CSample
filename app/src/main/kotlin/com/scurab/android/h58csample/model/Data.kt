package com.scurab.android.h58csample.model

import com.google.gson.annotations.SerializedName

/**
 * Created by JBruchanov on 06/08/2017.
 */

data class PublicPhotos(
        val title: String,
        val link: String,
        val description: String,
        val modified: String,
        val items: List<PublicPhoto>
)

data class PublicPhoto(
        val title: String,
        val link: String,
        val media: Media,
        @SerializedName("date_taken") val dateTaken: String,
        @SerializedName("description") val descriptionHtml: String,
        val published: String,
        val author: String,
        @SerializedName("author_id") val authorId: String,
        val tags: String
)

data class Media(
        @SerializedName("m") val link: String
)