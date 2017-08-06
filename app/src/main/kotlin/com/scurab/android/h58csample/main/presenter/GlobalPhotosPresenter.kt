package com.scurab.android.h58csample.main.presenter

import com.scurab.android.h58csample.api.ServerApi
import com.scurab.android.h58csample.base.BasePresenter
import com.scurab.android.h58csample.base.HasProgressBar
import com.scurab.android.h58csample.base.IViewContract
import com.scurab.android.h58csample.component.*
import com.scurab.android.h58csample.main.INavigator
import com.scurab.android.h58csample.main.MainComponent
import com.scurab.android.h58csample.main.PhotoItemClickListener
import com.scurab.android.h58csample.model.Comparators
import com.scurab.android.h58csample.model.Photo
import com.scurab.android.h58csample.model.PublicPhotos
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import javax.inject.Inject

/**
 * Created by JBruchanov on 06/08/2017.
 */
interface IGlobalPhotosViewContract : IViewContract, HasProgressBar {
    fun tags(): String
    fun setOnRefreshListener(callback: (() -> Unit)?)
    fun setPhotos(items: List<Photo>, localeHelper: ILocaleHelper)
    fun setItemClickListener(clickListener: PhotoItemClickListener?)
    fun setItemLongClickListener(longClickListener: PhotoItemClickListener?)
}

class GlobalPhotosPresenter(component: MainComponent? = null) : BasePresenter<IGlobalPhotosViewContract>() {

    private val TAG = "GlobalPhotosPresenter"

    @Inject lateinit var serverApi: ServerApi
    @Inject lateinit var schedulers: IRxSchedulers
    @Inject lateinit var logger: ILogger
    @Inject lateinit var navigator: INavigator
    @Inject lateinit var cache: ICache
    @Inject lateinit var localeHelper: ILocaleHelper

    private var loadingSubscription: Disposable? = null
    private var lastPhotos: Cacheable<PublicPhotos>? = null

    init {
        component?.inject(this)
    }

    override fun onAttachViewContract(viewContract: IGlobalPhotosViewContract) {
        super.onAttachViewContract(viewContract)
        viewContract.setOnRefreshListener { onLoadData(true) }
        viewContract.setItemClickListener { onItemClick(it) }
        viewContract.setItemLongClickListener { onItemLongClick(it) }
    }

    override fun onResume() {
        super.onResume()
        onLoadData(false)
    }

    fun onItemClick(photo: Photo) {
        navigator.openPhoto(photo)
    }

    fun onItemLongClick(photo: Photo) {
        navigator.sharePhoto(photo)
    }

    internal fun onLoadData(manual: Boolean) {
        loadingSubscription?.dispose()
        loadingSubscription = serverApi
                .getPublicPhotos(viewContract.tags())
                .subscribeOn(schedulers.io())
                .compose(localeHelper.transformer())
                .map { it ->
                    //TODO:argument to select taken vs published
                    Collections.sort(it.items, Comparators.PhotoSortByTaken)
                    it
                }
                .compose(cache.reuse(lastPhotos, manual))
                .compose(bindToLifecycle())
                .compose(bindToProgressBarVisibility(viewContract))
                .observeOn(schedulers.ui())
                .subscribeBy(
                        onNext = { result ->
                            this.lastPhotos = result
                            viewContract.setPhotos(result.item.items, localeHelper)
                        },
                        onError = { err -> logger.d(TAG, "getPublicPhotos", err) }
                )
    }
}