package com.scurab.android.h58csample.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ShareCompat
import android.support.v7.app.AppCompatActivity
import com.scurab.android.h58csample.R
import com.scurab.android.h58csample.component.ILogger
import com.scurab.android.h58csample.extension.app
import com.scurab.android.h58csample.extension.transaction
import com.scurab.android.h58csample.main.fragment.GlobalPhotosFragment
import com.scurab.android.h58csample.model.Photo
import javax.inject.Inject

/**
 * Created by JBruchanov on 06/08/2017.
 */
class MainActivity : AppCompatActivity(), INavigator {

    @Inject lateinit var logger: ILogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)

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
                .mainModule(MainModule(this))
                .appComponent(app().appComponent)
                .build()
    }

    override fun openPhoto(photo: Photo) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(photo.link)))
        } catch (e: Throwable) {
            logger.d("MainActivity", "openPhoto", e)
        }
    }

    override fun sharePhoto(photo: Photo) {
        try {
            //TODO: sharing directly would need download full resolution and also own ContentProvider
            ShareCompat.IntentBuilder.from(this)
                    .setType("message/rfc822")
                    .setSubject(resources.getString(R.string.share))
                    .setText(photo.link)
                    .setChooserTitle(R.string.share)
                    .startChooser()
        } catch (e: Throwable) {
            logger.d("MainActivity", "sharePhoto", e)
        }
    }
}