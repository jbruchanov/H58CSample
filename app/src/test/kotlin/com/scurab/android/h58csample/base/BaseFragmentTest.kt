package com.scurab.android.h58csample.base

import android.annotation.SuppressLint
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

/**
 * Created by JBruchanov on 06/08/2017.
 */
@RunWith(RobolectricTestRunner::class)
class BaseFragmentTest {

    @Test
    fun testPassesEvents() {
        var presenter = mock(BasePresenter::class.java) as BasePresenter<IViewContract>
        val fragment =
                @SuppressLint("ValidFragment") object : BaseFragment<BasePresenter<IViewContract>>() {
                    override val layoutId: Int = 0
                    init {
                        this.presenter = presenter
                    }
                }

        fragment.onResume()
        verify(presenter).onResume()

        fragment.onPause()
        verify(presenter).onPause()

        fragment.onDestroyView()
        verify(presenter).onDetachViewContract()
    }
}