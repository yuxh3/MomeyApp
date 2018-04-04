package com.example.yuxinhua.momeyapp.inteface

import android.support.v7.util.DiffUtil
import com.example.yuxinhua.momeyapp.data.BaseData
import com.example.yuxinhua.momeyapp.data.InvestData

/**
 * Created by yuxh3
 * On 2018/4/3.
 * Email by 791285437@163.com
 */
class Callback: DiffUtil.Callback {

    private var mOldDatas:ArrayList<InvestData>? = null
    private var mNewData:ArrayList<InvestData>? = null

    constructor(mOldDatas: ArrayList<InvestData>?, mNewData: ArrayList<InvestData>?) : super() {
        this.mOldDatas = mOldDatas
        this.mNewData = mNewData
    }


    /**
     * 这里我只是模拟数据，所以只把它设置成了false，正式项目中视情况而定
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//       return mOldDatas?.get(oldItemPosition)?.title?.equals(mOldDatas?.get(oldItemPosition)?.title)!!
        return false
    }

    /**
     * 老数据集 size
     */
    override fun getOldListSize(): Int = mOldDatas?.size?:0

    /**
     * 新数据集 size
     */
    override fun getNewListSize(): Int = mNewData?.size?:0

    /**
     * 只有当areItemsTheSame is true 才会执行这个方法
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val mOldData = mOldDatas?.get(oldItemPosition)
        val mNewData = mNewData?.get(oldItemPosition)

        if (!mOldData?.increase.equals(mNewData?.increase)){
            return false
        }

        return true
    }

}