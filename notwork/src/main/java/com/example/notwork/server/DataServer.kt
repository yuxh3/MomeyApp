package com.example.notwork.server

import com.example.notwork.`object`.HomeInfo
import com.example.notwork.`object`.HomeInfoItem
import retrofit2.http.GET
import rx.Observable

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
interface DataServer{

    @GET("/app/native/getIndexImages")
    fun getHomeInfo():Observable<HomeInfo<HomeInfoItem>>
}