package com.scurab.android.h58csample.extension

import android.app.Application
import com.scurab.android.h58csample.TestH58CSampleApp
import org.robolectric.RuntimeEnvironment

/**
 * Created by JBruchanov on 06/08/2017.
 */

fun Application.app(): TestH58CSampleApp = this as TestH58CSampleApp
fun app() : TestH58CSampleApp = RuntimeEnvironment.application.app()