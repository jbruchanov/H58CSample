package com.scurab.android.h58csample.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.scurab.android.h58csample.R
import com.scurab.android.h58csample.extension.app
import com.scurab.android.h58csample.extension.transaction
import com.scurab.android.h58csample.main.fragment.GlobalPhotosFragment

/**
 * Created by JBruchanov on 06/08/2017.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.apply {
            if (findFragmentById(R.id.fragment_container) == null) {
                transaction {
                    add(R.id.fragment_container, GlobalPhotosFragment(), GlobalPhotosFragment::class.java.name)
                }
            }
        }
    }

    val component by lazy {
        DaggerMainComponent
                .builder()
                .appComponent(app().appComponent)
                .build()
    }
}