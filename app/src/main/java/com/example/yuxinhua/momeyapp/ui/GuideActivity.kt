package com.example.yuxinhua.momeyapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.utils.DevicesInfo
import kotlinx.android.synthetic.main.guide_avivity.*

/**
 * Created by yuxh3
 * On 2018/3/18.
 * Email by 791285437@163.com
 */
class GuideActivity:BaseActivity(){
    override fun getLayoutId(): Int = R.layout.guide_avivity

    var imageList:ArrayList<View> = arrayListOf()
    var doc_X  = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        isNeedShowTranslucentStatus(true,R.color.transparent)
        initView()

        view_pager.adapter = ImageAdapter(imageList)
        view_pager.setCurrentItem(0)

        view_pager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                dot_foucs.translationX = doc_X*(position + positionOffset)
            }

            override fun onPageSelected(position: Int) {
            }

        })
    }

    fun initView(){

        val view_1 = View.inflate(this,R.layout.image_view_1,null)
        val view_2 = View.inflate(this,R.layout.image_view_2,null)
        val view_3 = View.inflate(this,R.layout.image_view_3,null)

        imageList.add(view_1)
        imageList.add(view_2)
        imageList.add(view_3)

        for (i in 0..imageList.size -1){
            val indicator = LinearLayout.LayoutParams(DevicesInfo.dpToPx(this@GuideActivity,10F),DevicesInfo.dpToPx(this@GuideActivity,10F))
            val indicatiorView = View(this)
            indicatiorView.setBackgroundResource(R.drawable.guide_indicatior_view_bg)
            indicatiorView.layoutParams = indicator
            if(i !== 0){
                indicator.leftMargin = DevicesInfo.dpToPx(this@GuideActivity,10F)
            }
            rl_top.addView(indicatiorView)
        }

        view_3.findViewById<View>(R.id.tv_now).setOnClickListener {
            startActivity(Intent(this@GuideActivity,LoginActivity::class.java))
            finish()
        }


        dot_foucs.postDelayed({
            doc_X = rl_top.getChildAt(1).left - rl_top.getChildAt(0).left
        },20)
    }

    class ImageAdapter:PagerAdapter {

        var dataList:ArrayList<View>? = null

        constructor(dataList:ArrayList<View>){

            this.dataList = dataList
        }

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean  = view == `object`

        override fun getCount(): Int = dataList?.size?:0

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            container?.addView(dataList?.get(position))
            return dataList?.get(position)?:0
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {

            container?.removeView(dataList?.get(position))
        }
    }
    
}