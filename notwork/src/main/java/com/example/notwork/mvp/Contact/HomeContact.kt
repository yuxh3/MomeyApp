package com.example.notwork.mvp.Contact

import com.example.notwork.`object`.BenLaiItemData
import com.example.notwork.`object`.HomeInfo
import com.example.notwork.`object`.HomeInfoItem
import com.example.notwork.mvp.model.BaseModel
import com.example.notwork.mvp.present.BasePresenter
import com.example.notwork.mvp.ui.BaseView
import io.reactivex.Observable

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
interface HomeContact{

    interface Model :BaseModel{
        fun getHomeInfo():Observable<HomeInfo<HomeInfoItem>>

        fun getBenLaiData():Observable<HomeInfo<BenLaiItemData>>
    }

    interface View:BaseView{

    }

    open class Presenter:BasePresenter<View,Model>()
}