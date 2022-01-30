package com.app.grofiesta.data.apiClient

import com.app.grofiesta.BuildConfig
import com.app.grofiesta.data.api.ApiUrls
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(ApiUrls.BASE_URL).addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            ).addConverterFactory(ScalarsConverterFactory.create()).addConverterFactory(
                GsonConverterFactory.create(gson)
            ).client(okHttpClient).build()
        }
        return retrofit
    }

    var gson = GsonBuilder().setLenient().create()

    fun <S> createService(serviceClass: Class<S>): S {
        return getClient()!!.create(serviceClass)
    }

    private var okHttpClient =
        OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS).readTimeout(
            60, TimeUnit.SECONDS
        ).writeTimeout(
            60, TimeUnit.SECONDS
        ).addInterceptor(NetworkLogger()).also { client ->
            if (BuildConfig.BUILD_TYPE.contentEquals("debug")) {
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(this)
                }
            }
        }.build()

}
