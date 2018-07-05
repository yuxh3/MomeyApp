package com.example.notwork.server

import com.example.notwork.`object`.BenLaiItemData
import com.example.notwork.`object`.HomeInfo
import com.example.notwork.`object`.HomeInfoItem
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
interface DataServer{

    @GET("/app/native/getIndexImages")
    fun getHomeInfo(): Observable<HomeInfo<HomeInfoItem>>

    @GET("AllCategory?channel=samsung&sign=E9632F333AFAF1D6D67B2F745E1E27FB&source=3&pageid=com.android.benlai.activity.main.MainActivity&systemVersion=7.1.1&deviceId=358520081199080&phoneModel=SM-N9500&localcity=120&isAll=true&version=3.9.0&nativeVersion=3.9.0&t=1530605389125&imei=358520081199080&android_id=c78b701bce0b1220")
    fun getHomeDiag():Observable<HomeInfo<BenLaiItemData>>
}