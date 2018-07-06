package com.example.yuxinhua.momeyapp.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.notwork.`object`.BenLaiItemAllCategory
import com.example.notwork.`object`.BenLaiItemData
import com.example.notwork.mvp.Contact.HomeCallBack
import com.example.notwork.mvp.Contact.HomeContact
import com.example.notwork.mvp.model.HomeModel
import com.example.notwork.mvp.present.HomePresent
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.adapter.BenContentAdapter
import com.example.yuxinhua.momeyapp.adapter.BenMenuAdapter
import kotlinx.android.synthetic.main.ben_lai_activity.*

/**
 * Created by yuxh3
 * On 2018/7/2.
 * Email by 791285437@163.com
 */
class BenLaiActivity:BaseActivity(),HomeContact.View{

    var mModel: HomeModel? = null
    var isNoFlag = false
    var prePosition = 0
    override fun getLayoutId(): Int = R.layout.ben_lai_activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())
        mModel = HomeModel()

         var present = HomePresent()

        present.setVM(this,mModel!!)


        present.loadBenLaiInfo(this,false,object :HomeCallBack{

            override fun success(data: BenLaiItemData) {


//                for (i in 0..data.allCategory.size){
//                    if (i === data.allCategory.size){
//                        data.allCategory.add(BenLaiItemAllCategory(0,
//                                ArrayList(),ArrayList(),true))
//                    }
//                }
                var adapterMenu = BenMenuAdapter(this@BenLaiActivity,data)
                val layoutManager = LinearLayoutManager(this@BenLaiActivity)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                menu?.adapter  = adapterMenu
                menu?.layoutManager = layoutManager
//                menu?.addItemDecoration(DividerItemDecoration(this@BenLaiActivity))

                var contentAdapter = BenContentAdapter(this@BenLaiActivity,data)
                contentAdapter.setFoot(1)
                val layoutManager1 = LinearLayoutManager(this@BenLaiActivity)
                layoutManager1.orientation = LinearLayoutManager.VERTICAL
                cantent?.adapter = contentAdapter
                cantent?.layoutManager = layoutManager1

                lister()


            }

            override fun error(code: Int, msg: String) {

            }

        })

    }

    /**
     * 滑动监听
     */
    fun lister(){
        cantent.addOnScrollListener(object :RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when(newState){
                    RecyclerView.SCROLL_STATE_IDLE->{//滑动停止的时候更改状态
                        isNoFlag = false
                    }
                    RecyclerView.SCROLLBAR_POSITION_DEFAULT->{
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING->{//拖拽的时候更改状态
                        isNoFlag = true
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val fristPosition = ((recyclerView?.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()
                val LastPosition = ((recyclerView?.layoutManager) as LinearLayoutManager).findLastVisibleItemPosition()
                val sum = ((recyclerView.adapter)as BenContentAdapter).getFootSize()

                if (LastPosition < (recyclerView.adapter.itemCount)) {
                    if (LastPosition == (recyclerView.adapter.itemCount )-sum){
                        prePosition = LastPosition-sum
                    }else {
                        prePosition = LastPosition
                    }
                    val height = ((recyclerView?.layoutManager) as LinearLayoutManager).height / 2
                    var tv1: TextView? = null
                    var tv2: TextView? = null

                    if (LastPosition > 0) {//当其>0 的时候获取当前一个和现在的一个控制改变其属性
                        if (LastPosition == (recyclerView.adapter.itemCount)-sum){
                            tv1 = (menu.getChildAt(fristPosition) as LinearLayout).getChildAt(0) as TextView
                            tv2 = (menu.getChildAt(LastPosition -sum) as LinearLayout).getChildAt(0) as TextView
                        }else {
                            tv1 = (menu.getChildAt(fristPosition) as LinearLayout).getChildAt(0) as TextView
                            tv2 = (menu.getChildAt(LastPosition) as LinearLayout).getChildAt(0) as TextView
                        }
                    } else {
                        tv2 = (menu.getChildAt(LastPosition) as LinearLayout).getChildAt(0) as TextView
                    }
                    if (LastPosition == (recyclerView.adapter.itemCount)-sum){

                    }else{

                    }
                    if (fristPosition == LastPosition) {
                        tv1?.setTextColor(Color.BLACK)
                        tv2?.setTextColor(Color.GREEN)
                        (menu.getChildAt(fristPosition) as LinearLayout).setBackgroundColor(Color.WHITE)
                        (menu.getChildAt(LastPosition) as LinearLayout).setBackgroundColor(Color.TRANSPARENT)
                    } else {
                        //这个就是获取当前最后一个能看见的顶部的距离
                        var top1 = -1
                        if (LastPosition == (recyclerView.adapter.itemCount)-sum){
                            top1 = ((recyclerView?.layoutManager) as LinearLayoutManager).getChildAt(LastPosition - fristPosition -sum).top
                        }else {
                            top1 = ((recyclerView?.layoutManager) as LinearLayoutManager).getChildAt(LastPosition - fristPosition).top
                        }

                        if (top1 < height) {
                            tv1?.setTextColor(Color.BLACK)
                            tv2?.setTextColor(Color.GREEN)
                            if (LastPosition == (recyclerView.adapter.itemCount)-sum){
                                (menu.getChildAt(fristPosition) as LinearLayout).setBackgroundColor(Color.WHITE)
                                (menu.getChildAt(LastPosition -sum) as LinearLayout).setBackgroundColor(Color.TRANSPARENT)
                            }else {
                                (menu.getChildAt(fristPosition) as LinearLayout).setBackgroundColor(Color.WHITE)
                                (menu.getChildAt(LastPosition) as LinearLayout).setBackgroundColor(Color.TRANSPARENT)
                            }

                        } else {
                            tv2?.setTextColor(Color.BLACK)
                            tv1?.setTextColor(Color.GREEN)
                            if (LastPosition == (recyclerView.adapter.itemCount)-sum){
                                (menu.getChildAt(fristPosition) as LinearLayout).setBackgroundColor(Color.TRANSPARENT)
                                (menu.getChildAt(LastPosition -sum) as LinearLayout).setBackgroundColor(Color.WHITE)
                            }else {
                                (menu.getChildAt(fristPosition) as LinearLayout).setBackgroundColor(Color.TRANSPARENT)
                                (menu.getChildAt(LastPosition) as LinearLayout).setBackgroundColor(Color.WHITE)
                            }
                        }
                    }

                }
            }
        })
    }


    /**
     * 恢复原来的设置,字体颜色,背景颜色
     */
    fun clear(){
        for (i in 0..prePosition) {
            val tv = (menu.getChildAt(i) as LinearLayout).getChildAt(0) as TextView
            tv.setTextColor(Color.BLACK)
            (menu.getChildAt(i) as LinearLayout).setBackgroundColor(Color.WHITE)
        }
    }
    /**
     * 点击回调
     */
    fun callBack(positon:Int ){
        if (isNoFlag){//使其在活动的时候不能点击
            return
        }
        if (prePosition != positon) {//解决重复点击 字体颜色,背景颜色 恢复
            clear()
        }
        prePosition = positon
        ((cantent.layoutManager) as LinearLayoutManager).scrollToPositionWithOffset(positon,0)

    }
}