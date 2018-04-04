package com.example.yuxinhua.momeyapp.data

/**
 * Created by yuxh3
 * On 2018/3/21.
 * Email by 791285437@163.com
 */
data class LampBean(
        var name:String
)

data class InvestData(
        var title:String,
        var increase:String,
        var money:String,
        var mouth:String,
        var progress:String
):BaseData()

open class BaseData()