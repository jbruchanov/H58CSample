package com.scurab.android.h58csample.base

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scurab.android.h58csample.AppComponent
import com.scurab.android.h58csample.extension.app

/**
 * Created by JBruchanov on 06/08/2017.
 */
abstract class BaseFragment : Fragment() {

    lateinit var appComponent: AppComponent
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent = context.app().appComponent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onResume() {
        super.onResume()
        basePresenter().onResume()
    }

    override fun onPause() {
        basePresenter().onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        basePresenter().onDetachViewContract()
        super.onDestroyView()
    }

    protected fun ensureArguments() {
        if (arguments == null) {
            arguments = Bundle()
        }
    }

    protected fun withArgument(key: String, obj: Parcelable) {
        ensureArguments()
        arguments.putParcelable(key, obj)
    }

    protected abstract fun basePresenter(): BasePresenter<*>

    protected abstract val layoutId : Int
}