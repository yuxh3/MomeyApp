package com.example.yuxinhua.momeyapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.adapter.HomeAdapter
import com.example.yuxinhua.momeyapp.widgt.AutoVerticalScrollTextView
import kotlinx.android.synthetic.main.home_bottom_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*

/**
 * Created by yuxh3
 * On 2018/3/16.
 * Email by 791285437@163.com
 */
class HomeFragment:BaseFragment(),AutoVerticalScrollTextView.OnClickListener{

    var viewFragment:View? = null

    val srtLeft:String = "金额"
    val srtRight = "期限"
    val homeData:ArrayList<String> = arrayListOf("50.0万","4月")
    val urlList:HashMap<String,String> = HashMap()
    val lampDataList:ArrayList<String> = arrayListOf()
    val imgDataList:ArrayList<Int> = arrayListOf(R.drawable.green_img_advantage_default,R.drawable.green_img_data_default
    ,R.drawable.green_img_financial_default,R.drawable.green_img_slide_default,R.drawable.green_img_introduce_default)
    companion object {
        fun getInstance():Fragment{
            val homeFragment = HomeFragment()
            return homeFragment
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewFragment === null){
            viewFragment = inflater?.inflate(R.layout.home_fragment_layout,container,false)!!
        }
        return viewFragment
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUrlToMap()
        lamp_view.initData(lampDataList)
        lamp_view.setOnClickListener(this)

        val adapter = HomeAdapter(context)
        adapter.setData(imgDataList)
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        recycle_view.layoutManager = manager
        recycle_view.adapter = adapter
        initData()
    }

    fun initData(){
        val coloSpan = ForegroundColorSpan(Color.parseColor("#000000"))
        val spanSrtLeft = SpannableString(srtLeft.plus(homeData[0]))
        val spanSrtRight = SpannableString(srtLeft.plus(homeData[1]))
        spanSrtLeft.setSpan(coloSpan,srtLeft.length,srtLeft.length+homeData[0].length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spanSrtRight.setSpan(coloSpan,srtRight.length,srtRight.length+homeData[1].length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        home_tv_money.setText(spanSrtLeft)
        home_tv_limit.setText(spanSrtRight)

    }

    fun setUrlToMap(){
        urlList.put("集渝粉拿奖励","http://www.yufex.com/fileContext/resource/index_config/imageResource?fileId=2c90d582622a63cd01622c5174bb6f64")
        urlList.put("优享计划","http://www.yufex.com/fileContext/resource/index_config/imageResource?fileId=2c90d582622a63cd01622db4cd323ccd")
        urlList.put("运营报告","http://www.yufex.com/fileContext/resource/index_config/imageResource?fileId=2c90d58261d7fed10161f570b62a2273")
        urlList.put("邀请好友一起来赚钱","http://www.yufex.com/fileContext/resource/index_config/imageResource?fileId=2c90d5825ab16277015ab24fb4a1346c")
        urlList.put("新手注册礼","http://www.yufex.com/fileContext/resource/index_config/imageResource?fileId=2c90d5825a234828015a2614f43f28ca")
        urlList.put("qq链接一群","http://www.yufex.com/fileContext/resource/index_config/imageResource?fileId=2c90d582610f2845016121e77f40309a")

        for (name in urlList.keys){
            val textSliderView = TextSliderView(context)
            textSliderView
                    .image(urlList.get(name)!!)
                    .description(name)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)

            textSliderView.bundle(Bundle())
            textSliderView.bundle.putString("extra",name)
            slider.addSlider(textSliderView)
            lampDataList.add(name)
        }
    }

    override fun cliclk(name: String) {
        Toast.makeText(context,name,Toast.LENGTH_SHORT).show()
    }
}