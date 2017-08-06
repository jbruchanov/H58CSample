package com.scurab.android.h58csample.main.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.scurab.android.h58csample.R
import com.scurab.android.h58csample.base.BaseFragment
import com.scurab.android.h58csample.component.ILocaleHelper
import com.scurab.android.h58csample.extension.setIsRefreshingSafe
import com.scurab.android.h58csample.extension.setVisibilitySafe
import com.scurab.android.h58csample.main.MainActivity
import com.scurab.android.h58csample.main.PhotoItemClickListener
import com.scurab.android.h58csample.main.PhotosAdapter
import com.scurab.android.h58csample.main.presenter.GlobalPhotosPresenter
import com.scurab.android.h58csample.main.presenter.IGlobalPhotosViewContract
import com.scurab.android.h58csample.model.Photo

/**
 * Created by JBruchanov on 06/08/2017.
 */
class GlobalPhotosFragment : BaseFragment<GlobalPhotosPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = GlobalPhotosPresenter((activity as MainActivity).component)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttachViewContract(GlobalPhotosViewContract(view))
    }

    override val layoutId: Int = R.layout.fragment_global_photos
}

class GlobalPhotosViewContract(private val view: View) : IGlobalPhotosViewContract {
    @BindView(R.id.tags) internal lateinit var tags: EditText
    @BindView(R.id.progress_bar) internal lateinit var progressBar: ProgressBar
    @BindView(R.id.swipe_refresh_layout) internal lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.recycler_view) internal lateinit var recyclerView: RecyclerView
    private val photosAdapter: PhotosAdapter

    init {
        ButterKnife.bind(this, view)
        val linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(view.context, linearLayoutManager.orientation))
        photosAdapter = PhotosAdapter()
        recyclerView.adapter = photosAdapter
    }

    override var progressBarVisible: Boolean
        get() = progressBar.visibility == View.VISIBLE
        set(value) {
            swipeRefreshLayout.setIsRefreshingSafe(false)
            progressBar.setVisibilitySafe(value)
        }

    override fun tags(): String = tags.text.toString()

    override fun setOnRefreshListener(callback: (() -> Unit)?) {
        swipeRefreshLayout.setOnRefreshListener(
                if (callback == null) {
                    null
                } else {
                    SwipeRefreshLayout.OnRefreshListener { callback() }
                }
        )
        tags.setOnEditorActionListener(
                if (callback == null) {
                    null
                } else {
                    TextView.OnEditorActionListener { textView, i, keyEvent ->
                        if (i == EditorInfo.IME_ACTION_DONE) {
                            callback()
                            true
                        } else {
                            false
                        }
                    }
                }
        )
    }

    override fun setPhotos(items: List<Photo>, localeHelper: ILocaleHelper) {
        photosAdapter.localeHelper = localeHelper
        photosAdapter.items = items
    }

    override fun setItemClickListener(clickListener: PhotoItemClickListener?) {
        photosAdapter.setItemClickListener(clickListener)
    }
}