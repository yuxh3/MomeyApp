package com.example.yuxinhua.momeyapp.fragment

import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.ui.BaseActivity
import com.example.yuxinhua.momeyapp.ui.MainActivity
import kotlinx.android.synthetic.main.guide_avivity.*
import kotlinx.android.synthetic.main.invest_fragment_layout.*

/**
 * Created by yuxh3
 * On 2018/3/16.
 * Email by 791285437@163.com
 */
class InvestFragment : BaseFragment(){

    var viewFragment: View? = null
    var dataTitles = arrayListOf<String>("非存管项目","存管项目")
    var dataFragment:LinkedHashMap<Int,Fragment> = LinkedHashMap()
    companion object {
        fun getInstance(): Fragment {
            val investFragment = InvestFragment()
            return investFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addSaveFragment()
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewFragment === null) {
            viewFragment = inflater?.inflate(R.layout.invest_fragment_layout, container, false)!!
        }
        return viewFragment
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        head_text.setPadding(0,getStatusHeight(),0,0)
        head_text.setBackgroundResource(R.color.inverst_head_yellow)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addAnimation(true)
        val mAdapter = object : FragmentPagerAdapter(childFragmentManager){
            override fun getItem(position: Int): Fragment = dataFragment[position]!!

            override fun getCount(): Int = dataTitles.size

            override fun getPageTitle(position: Int): CharSequence {
                return dataTitles[position]
            }
        }
        rv_indicator.setTabItemTitles(dataTitles)

        mHandler?.postDelayed(Runnable {
            addAnimation(false)

            ivest_view_pager?.adapter = mAdapter
            rv_indicator.setViewPager(ivest_view_pager!!,0)

        },2000)
    }

    fun addSaveFragment():LinkedHashMap<Int,Fragment>{
        for (pos in 0..dataTitles.size -1){
            if (dataFragment.get(pos) == null) {
                dataFragment.put(pos,ViewPagerSaveFragment.getInstance())
            }
        }
        return dataFragment
    }

    override fun handlerMessage(msg: Message?) {
        if (msg?.what == 0){

        }
    }
}