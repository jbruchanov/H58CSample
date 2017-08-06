package com.scurab.android.h58csample.main.fragment

import android.os.Bundle
import com.scurab.android.h58csample.R
import com.scurab.android.h58csample.base.BaseFragment
import com.scurab.android.h58csample.main.presenter.GlobalPhotosPresenter

/**
 * Created by JBruchanov on 06/08/2017.
 */
class GlobalPhotosFragment : BaseFragment<GlobalPhotosPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = GlobalPhotosPresenter()
    }

    override val layoutId: Int = R.layout.fragment_global_photos
}