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
import com.daimajia.slider.library.Tricks.ViewPagerEx
import com.example.notwork.`object`.HomeInfoItem
import com.example.notwork.mvp.Contact.HomeCallBack
import com.example.notwork.mvp.Contact.HomeContact
import com.example.notwork.mvp.model.HomeModel
import com.example.notwork.mvp.present.HomePresent
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.adapter.HomeAdapter
import com.example.yuxinhua.momeyapp.widgt.AutoVerticalScrollTextView
import kotlinx.android.synthetic.main.home_bottom_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.*
import kotlinx.android.synthetic.main.home_fragment_layout.view.*

/**
 * Created by yuxh3
 * On 2018/3/16.
 * Email by 791285437@163.com
 */
class HomeFragment:BaseFragment(),AutoVerticalScrollTextView.OnClickListener,HomeContact.View{

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
        mPresenter?.setVM(this,mModel!!)

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


        mPresenter?.loadHomeInfo(context,false,object:HomeCallBack<ArrayList<HomeInfoItem>>{

            override fun success(data: ArrayList<HomeInfoItem>) {
                for (mData in data.iterator()){
                    urlList.put(mData.name!!,mData.imgUrl)

                    setUrlToMap(data?.size)
                }
            }

            override fun error(code: Int, msg: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })


    }

    fun setUrlToMap(size:Int){

        for (name in urlList.keys){
            val textSliderView = TextSliderView(context)
            lampDataList.add(name)
            textSliderView
                    .image(urlList.get(name)!!)
                    .description(name)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside)

            textSliderView.bundle(Bundle())
            textSliderView.bundle.putString("extra",name)
            slider.addSlider(textSliderView)
        }

        lamp_view.initData(lampDataList)
        lamp_view.setOnClickListener(this)

        tv_home_number.text = (slider.currentPosition % size+1).toString()+"/"+size
        slider.addOnPageChangeListener(object :ViewPagerEx.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                Toast.makeText(context,"",Toast.LENGTH_SHORT).show()
            }

            override fun onPageSelected(position: Int) {

                tv_home_number.text = (position % size+1).toString()+"/"+size
            }

        })
    }

    override fun cliclk(name: String) {
        Toast.makeText(context,name,Toast.LENGTH_SHORT).show()
    }
}