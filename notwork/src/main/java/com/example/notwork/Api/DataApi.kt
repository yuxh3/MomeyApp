package com.example.notwork.Api

import com.example.notwork.Application.MApplication
import com.example.notwork.`object`.Common
import com.example.notwork.server.DataServer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
class DataApi private constructor(){

    val file: File = File(MApplication().applicition?.cacheDir, "cache")
    val cache: Cache = Cache(file, 1024 * 1024 * 100)
    val logInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
    val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create()


    companion object {
        val instance by lazy { init.DATA_API }
    }

    private object init{
        val DATA_API = DataApi()
    }

    fun setInit():DataServer{
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .readTimeout(Common.READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Common.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .cache(cache)
                .addInterceptor(logInterceptor)
                .build()
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Common.BASEURL)
                .build()
        val dataService = retrofit.create(DataServer::class.java)

        return dataService
    }
}