package com.scurab.android.h58csample.component

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

/**
 * Created by JBruchanov on 06/08/2017.
 */
class LocaleHelperTest {

    private val localeHelper = LocaleHelper()

    @Test
    fun testParseDateTaken() {
        val date = localeHelper.photoTakenFormat.parse("2017-04-12T12:36:03-01:00")
        val c = Calendar.getInstance()
        c.time = date

        assertEquals(2017, c.get(Calendar.YEAR))
        assertEquals(4 - 1, c.get(Calendar.MONTH))
        assertEquals(12, c.get(Calendar.DAY_OF_MONTH))
    }

    @Test
    fun testParseDatePublished() {
        val date = localeHelper.photoTakenFormat.parse("2017-08-06T09:55:48Z")
        val c = Calendar.getInstance()
        c.time = date

        assertEquals(2017, c.get(Calendar.YEAR))
        assertEquals(8 - 1, c.get(Calendar.MONTH))
        assertEquals(6, c.get(Calendar.DAY_OF_MONTH))
    }
}