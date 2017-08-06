package com.scurab.android.h58csample.main

import com.scurab.android.h58csample.model.Photo

/**
 * Created by JBruchanov on 06/08/2017.
 */
interface INavigator {
    fun openPhoto(photo: Photo)
    fun sharePhoto(photo : Photo)
}