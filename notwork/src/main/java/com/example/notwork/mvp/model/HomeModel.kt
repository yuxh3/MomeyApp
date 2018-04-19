package com.example.notwork.mvp.model

import com.example.notwork.Api.DataApi
import com.example.notwork.`object`.HomeInfo
import com.example.notwork.`object`.HomeInfoItem
import com.example.notwork.mvp.Contact.HomeContact
import com.example.notwork.mvp.Contact.RxSchedulersUtils
import com.example.notwork.server.DataServer
import rx.Observable

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
class HomeModel:HomeContact.Model{

    override fun init(): DataServer {

       return DataApi.instance.setInit()
    }


    override fun getHomeInfo(): Observable<HomeInfo<HomeInfoItem>> {

        return init().getHomeInfo().compose(RxSchedulersUtils.io_main())
    }

}