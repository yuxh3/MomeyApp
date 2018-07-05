package com.example.yuxinhua.momeyapp.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
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
    override fun getLayoutId(): Int = R.layout.ben_lai_activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutId())
        mModel = HomeModel()

         var present = HomePresent()

        present.setVM(this,mModel!!)


        present.loadBenLaiInfo(this,false,object :HomeCallBack{

            override fun success(data: BenLaiItemData) {

                var adapterMenu = BenMenuAdapter(this@BenLaiActivity,data)
                val layoutManager = LinearLayoutManager(this@BenLaiActivity)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                menu?.adapter  = adapterMenu
                menu?.layoutManager = layoutManager
//                menu?.addItemDecoration(DividerItemDecoration(this@BenLaiActivity))


                var contentAdapter = BenContentAdapter(this@BenLaiActivity,data)
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
                    RecyclerView.SCROLL_STATE_IDLE->{
                    }
                    RecyclerView.SCROLLBAR_POSITION_DEFAULT->{

                    }
                    RecyclerView.SCROLL_STATE_DRAGGING->{

                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                (menu.getChildAt(0) as LinearLayout).setBackgroundColor(Color.TRANSPARENT)
                val fristPosition = ((recyclerView?.layoutManager) as LinearLayoutManager).findFirstVisibleItemPosition()
                val LastPosition = ((recyclerView?.layoutManager) as LinearLayoutManager).findLastVisibleItemPosition()

                val height = ((recyclerView?.layoutManager) as LinearLayoutManager).height/2
                var tv1:TextView? = null
                var tv2:TextView? = null

                if (LastPosition > 0){
                    tv1 = (menu.getChildAt(fristPosition) as LinearLayout).getChildAt(0) as TextView
                    tv2 = (menu.getChildAt(LastPosition) as LinearLayout).getChildAt(0) as TextView
                }else {
                    tv2 = (menu.getChildAt(LastPosition) as LinearLayout).getChildAt(0) as TextView
                }
                    if (fristPosition == LastPosition) {
                        tv2?.setTextColor(Color.GREEN)
                    } else {
                        val top1 = ((recyclerView?.layoutManager) as LinearLayoutManager).getChildAt(LastPosition - fristPosition).top

                        if (top1 < height) {
                            tv1?.setTextColor(Color.BLACK)
                            tv2?.setTextColor(Color.GREEN)
                        } else {
                            tv2?.setTextColor(Color.BLACK)
                            tv1?.setTextColor(Color.GREEN)
                        }
                }
            }
        })
    }


    /**
     * 恢复原来的设置,字体颜色,背景颜色
     */
    fun clear(){
         val tv= (menu.getChildAt(prePosition) as LinearLayout).getChildAt(0) as TextView
         tv.setTextColor(Color.BLACK)
        (menu.getChildAt(prePosition) as LinearLayout).setBackgroundColor(Color.WHITE)
    }

    var prePosition = 0

    /**
     * 点击回调
     */
    fun callBack(positon:Int ){
        clear()
        prePosition = positon
        ((cantent.layoutManager) as LinearLayoutManager).scrollToPositionWithOffset(positon,0)
    }
}