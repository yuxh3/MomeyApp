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

data class MeData(
        var id:Int,
        var message:String

)

data class FindData(
        var type:Int,
        var data:ArrayList<FindItemData>?
)

data class FindItemData(
        var money: String,
        var content:String
)
data class DayFinish(
        var day:Int,
        var all:Int,
        var finish:Int
)
open class BaseData()