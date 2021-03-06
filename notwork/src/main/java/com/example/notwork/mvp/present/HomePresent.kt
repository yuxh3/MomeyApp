package com.example.notwork.mvp.present

import android.content.Context
import android.util.Log
import com.example.notwork.`object`.BenLaiItemData
import com.example.notwork.mvp.Contact.HomeCallBack
import com.example.notwork.mvp.Contact.HomeContact
import com.example.notwork.mvp.Contact.HttpResultSubscriber

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
class HomePresent:HomeContact.Presenter() {

//    fun loadHomeInfo(context: Context,isShow:Boolean,callBack: HomeCallBack<ArrayList<HomeInfoItem>>){
////        mModel?.getHomeInfo()?.subscribe(object :HttpResultSubscriber<HomeInfoItem>(context,isShow){
////            override fun onSuccess(data: ArrayList<HomeInfoItem>?) {
////
////                if (callBack !== null){
////                    callBack.success(data!!)
////                }
////            }
////
////            override fun onFailue(code: Int, errorMsg: String) {
////                if (callBack  !== null){
////                    callBack.error(code,errorMsg)
////                }
////            }
////
////        })
//    }

    fun loadBenLaiInfo(context: Context, isShow:Boolean, callBack: HomeCallBack){


        mModel?.getBenLaiData()?.subscribe(object :HttpResultSubscriber<BenLaiItemData>(context,isShow){
            override fun onSuccess(data: BenLaiItemData) {
                Log.i("yuxh3","------------->onSuccess")

                callBack.success(data)
            }

            override fun onFailue(errorMsg: String) {

                Log.i("yuxh3","------------->onFailue")
            }


        })

    }

}