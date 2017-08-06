package com.scurab.android.h58csample.main.presenter

import com.google.gson.Gson
import com.scurab.android.h58csample.api.ServerApi
import com.scurab.android.h58csample.extension.app
import com.scurab.android.h58csample.model.PublicPhotos
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import java.io.File
import java.io.FileReader

/**
 * Created by JBruchanov on 06/08/2017.
 */
@RunWith(RobolectricTestRunner::class)
class GlobalPhotosPresenterTest {


    val samplePhotos: PublicPhotos by lazy {
        val classLoader = javaClass.classLoader
        val file = File(classLoader.getResource("photos.json").file)
        val fileReader = FileReader(file)
        val photos = Gson().fromJson(fileReader, PublicPhotos::class.java)
        fileReader.close()
        photos
    }

    @Test
    fun testLoading() {
        val presenter = GlobalPhotosPresenter()
        presenter.schedulers = app().appComponent.schedulers()
        presenter.cache = app().appComponent.cache()
        presenter.localeHelper = app().appComponent.localeHelper()
        presenter.serverApi = mock(ServerApi::class.java)
        doReturn(Observable.just(samplePhotos)).`when`(presenter.serverApi).getPublicPhotos(ArgumentMatchers.anyString())
        val viewContract = mock(IGlobalPhotosViewContract::class.java)
        doReturn("").`when`(viewContract).tags()
        presenter.onAttachViewContract(viewContract)

        presenter.onResume()

        (presenter.schedulers.ui() as TestScheduler).triggerActions()
        verify(viewContract).setPhotos(samplePhotos.items, presenter.localeHelper)
    }

    @Test
    fun testReuseData() {
        val presenter = GlobalPhotosPresenter()
        presenter.schedulers = app().appComponent.schedulers()
        presenter.cache = app().appComponent.cache()
        presenter.localeHelper = app().appComponent.localeHelper()
        presenter.serverApi = mock(ServerApi::class.java)
        doReturn(Observable.just(samplePhotos)).`when`(presenter.serverApi).getPublicPhotos(ArgumentMatchers.anyString())
        val viewContract = mock(IGlobalPhotosViewContract::class.java)
        doReturn("").`when`(viewContract).tags()
        presenter.onAttachViewContract(viewContract)

        presenter.onResume()
        (presenter.schedulers.ui() as TestScheduler).triggerActions()
        presenter.onResume()
        (presenter.schedulers.ui() as TestScheduler).triggerActions()
        verify(viewContract, times(2)).setPhotos(samplePhotos.items, presenter.localeHelper)
    }
}