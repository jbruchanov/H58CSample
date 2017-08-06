package com.scurab.android.h58csample.component

import com.scurab.android.h58csample.component.Cacheable
import com.scurab.android.h58csample.component.Source
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Created by JBruchanov on 06/08/2017.
 */
@RunWith(Parameterized::class)
class CacheableTest {

    companion object {
        @JvmStatic
        @Parameterized.Parameters()
        fun data(): Array<Array<Any>> {
            return arrayOf(
                    /*  expected, obj,           expiry, now*/
                    arrayOf(true, cacheable(1000), 1000, 1999),
                    arrayOf(true, cacheable(1000), 1000, 2000),
                    arrayOf(false, cacheable(1000), 1000, 2001),
                    //rev time in case of some messed up time
                    arrayOf(true, cacheable(2000), 1000, 1001),
                    arrayOf(true, cacheable(2000), 1000, 1000),
                    arrayOf(false, cacheable(2000), 1000, 999)
            )
        }

        fun cacheable(expiry: Long) = Cacheable("", Source.Origin, expiry)
    }

    private val expectedValid : Boolean
    private val obj : Cacheable<*>
    private val expiry : Long
    private val now : Long

    constructor(expectedValid: Boolean, obj: Cacheable<*>, expiry: Long, now: Long) {
        this.expectedValid = expectedValid
        this.obj = obj
        this.expiry = expiry
        this.now = now
    }

    @Test
    fun testIsValid() {
        assertEquals(expectedValid, obj.isValid(expiry, now))
    }
}
