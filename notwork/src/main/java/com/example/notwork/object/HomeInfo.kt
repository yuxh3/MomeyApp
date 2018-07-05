package com.example.notwork.`object`

/**
 * Created by yuxh3
 * On 2018/4/11.
 * Email by 791285437@163.com
 */
data class HomeInfo<T>(
        var error:String,
        var message:String,
//        var data:ArrayList<T>,
        var data:T
)
data class HomeInfoItem(
        var id:String?,
        var name:String?,
        var imageFileId:String,
        var imageRelativePath:String?,
        var linkUrl:String,
        var orderIndex:String,
        var valid:String,
        var cnzzCode:String,
        var iosKey:String,
        var androidKey:String,
        var remark:String,
        var createDate:String,
        var lastUpdateDate:String,
        var createOperatorId:String,
        var lastUpdateOperatorId:String,
        var imgUrl:String,
        var type:Type

)
data class Type(
        var key:String,
        var name:String
)
data class BenLaiData(
        var error: String,
        var message: String,
        var data:BenLaiItemData
)
data class BenLaiItemData(
        var menu:ArrayList<BenLaiItemMenu>,
        var allCategory:ArrayList<BenLaiItemAllCategory>
)
data class BenLaiItemMenu(
        var isSelected:Boolean,
        var sysNo:Int,
        var name:String,
        var imgUrl:String
)
data class BenLaiItemAllCategory(
        var parentSysNo:Int,
        var adList:ArrayList<BenLaiItemList>,
        var category:ArrayList<BenLaiItemCate>
)
data class BenLaiItemList(
        var uri:String=""
)
data class BenLaiItemCate(
        var parentSysNo:Int,
        var children:ArrayList<BenLaiItemCate>,
        var isSelected:Boolean,
        var sysNo:Int,
        var name:String,
        var imgUrl:String
)