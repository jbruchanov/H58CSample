package com.scurab.android.h58csample.main

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.scurab.android.h58csample.R
import com.scurab.android.h58csample.component.ILocaleHelper
import com.scurab.android.h58csample.model.Photo

/**
 * Created by JBruchanov on 06/08/2017.
 */
typealias PhotoItemClickListener = ((Photo) -> Unit)

class PhotosAdapter : RecyclerView.Adapter<PhotoViewHolder>() {

    var items: List<Photo> = listOf()
        get() = field
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private lateinit var layoutInflater: LayoutInflater
    private var itemClickListener: PhotoItemClickListener? = null
    lateinit var localeHelper: ILocaleHelper

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        layoutInflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = layoutInflater.inflate(R.layout.view_item_photo, parent, false)
        val photoViewHolder = PhotoViewHolder(view)
        view.setOnClickListener { dispatchItemClick(photoViewHolder.adapterPosition) }
        return photoViewHolder
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            title.text = item.title
            published.text = localeHelper.mediumDateMediumTimeFormat.format(item.publishedObj)
            loadImage(item.media.link)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun dispatchItemClick(position: Int) {
        itemClickListener?.let {
            it(items[position])
        }
    }

    fun setItemClickListener(clickListener: PhotoItemClickListener?) {
        this.itemClickListener = clickListener
    }
}

class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    @BindView(R.id.title) lateinit var title: TextView
    @BindView(R.id.published) lateinit var published: TextView
    @BindView(R.id.image) lateinit var image: ImageView
    private var target: SimpleTarget<Bitmap>? = null

    init {
        ButterKnife.bind(this, view)
    }

    fun loadImage(url: String?) {
        image.setImageBitmap(null)
        target = Glide
                .with(itemView.context)
                .load(url).asBitmap()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                        image.setImageBitmap(resource)
                    }
                })

    }
}