package com.scurab.android.h58csample.api

import com.scurab.android.h58csample.extension.app
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Created by JBruchanov on 06/08/2017.
 */

@RunWith(RobolectricTestRunner::class)
@Ignore //real data test
class ServerApiTest {

    private val serverApi by lazy { app().appComponent.serverApi() }

    @Test
    fun testGetData() {
        val response = serverApi
                .getPublicPhotos()
                .blockingFirst()


        assertNotNull(response)
        assertTrue(response.items.isNotEmpty())

        val item = response.items.first()
        assertNotNull(item.title)
        assertNotNull(item.dateTaken)
        assertNotNull(item.media)
    }

    @Test
    fun testGetDataByTag() {
        val tag = "car"

        val response = serverApi
                .getPublicPhotos(tag)
                .blockingFirst()


        assertNotNull(response)
        assertTrue(response.items.isNotEmpty())

        val item = response.items.first()
        assertNotNull(item.title)
        assertNotNull(item.dateTaken)
        assertNotNull(item.media)
        assertTrue(item.tags.contains(tag))
    }
}