package com.scurab.android.h58csample.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.scurab.android.h58csample.api.ServerApi
import com.scurab.android.h58csample.component.ILogger
import com.scurab.android.h58csample.dagger.PerApp
import com.scurab.android.h58csample.h58csample.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by JBruchanov on 06/08/2017.
 */
@Module
class NetModule{

    @Provides
    @PerApp
    fun provideServerApi(retrofitBuilder: Retrofit.Builder, gson: Gson): ServerApi {
        return retrofitBuilder
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BuildConfig.SERVER_URL)
                .build()
                .create(ServerApi::class.java)
    }

    @Provides
    @PerApp
    fun provideRetrofitBuilder(httpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
    }

    @Provides
    @PerApp
    fun provideConnectionSpec() : ConnectionSpec =
            ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS).build()

    @Provides
    @PerApp
    fun provideHttpClient(logger: ILogger, connectionSpec: ConnectionSpec): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val httpLogger = HttpLoggingInterceptor.Logger { message -> logger.d("OkHttp", message) }
            val httpLoggingInterceptor = HttpLoggingInterceptor(httpLogger)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(StethoInterceptor())
                    .addInterceptor(httpLoggingInterceptor)
        }

        // Add our headers via an interceptor
        val authInterceptor: Interceptor = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.API_KEY)
                    .addQueryParameter("format", "json")
                    .addQueryParameter("nojsoncallback", "1")
                    .build()
            // Request customization: add request headers
            val request = original
                    .newBuilder()
                    .url(url)
                    .build()
            chain.proceed(request)
        }

        builder.connectionSpecs(listOf(connectionSpec))
        builder.addInterceptor(authInterceptor)

        return builder.build()
    }
}