package com.scurab.android.h58csample.component

import io.reactivex.Observable
import io.reactivex.ObservableTransformer

/**
 * Created by JBruchanov on 06/08/2017.
 */
enum class Source {
    Memory, Cache, Origin
}

data class Cacheable<out T>(val item: T, val source: Source, val created: Long) {

    fun isValid(expiry: Long = Constants.cacheExpiry): Boolean {
        return isValid(expiry, System.currentTimeMillis())
    }

    internal fun isValid(expiry: Long = Constants.cacheExpiry, now: Long): Boolean {
        return Math.abs(created - now) <= expiry
    }
}


interface ICache {

    fun <T> reuse(obj: Cacheable<T>?, force: Boolean): ObservableTransformer<in T, out Cacheable<T>> {
        return reuse(obj, if (force) 0L else Constants.cacheExpiry)
    }

    /**
     * Use this transformer for taking value from cache.
     * If cache doesn't have the value, then and only then is made the request
     */
    fun <T> reuse(obj: Cacheable<T>?, expiry: Long = Constants.cacheExpiry): ObservableTransformer<in T, out Cacheable<T>> {
        return ObservableTransformer { upstream ->
            if (obj != null && obj.isValid(expiry)) {
                val cached = if (obj.source != Source.Memory) {
                    obj.copy(source = Source.Memory)
                } else {
                    obj
                }
                Observable.just(cached)
            } else {
                upstream.map {
                    Cacheable(it, Source.Origin, System.currentTimeMillis())
                }
            }
        }
    }
}

class AndroidCache : ICache