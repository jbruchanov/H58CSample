package com.scurab.android.h58csample.api

import com.scurab.android.h58csample.model.PublicPhotos
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by JBruchanov on 06/08/2017.
 */
interface ServerApi {


    @GET("feeds/photos_public.gne")
    fun getPublicPhotos(@Query("tags") tags: String = ""): Observable<PublicPhotos>
}
