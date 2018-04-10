package com.example.yuxinhua.momeyapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.adapter.MeAdapter
import com.example.yuxinhua.momeyapp.adapter.MeAdapterDecoration
import com.example.yuxinhua.momeyapp.data.MeData
import kotlinx.android.synthetic.main.me_fragment_layout.*

/**
 * Created by yuxh3
 * On 2018/3/16.
 * Email by 791285437@163.com
 */
class MeFragment : BaseFragment(){

    var viewFragment: View? = null
    val imgData:ArrayList<Int> = arrayListOf<Int>(R.drawable.asset,R.drawable.investment_projects,R.drawable.transaction,R.drawable.nav_icon_sign_home_default,
            R.drawable.certification,R.drawable.oen,R.drawable.account,R.drawable.cunguanicon,R.drawable.more_settings)
    var mData:ArrayList<MeData> = arrayListOf()
//    var mEData:MeData? = null
    companion object {
        fun getInstance(): Fragment {
            val meFragment = MeFragment()
            return meFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewFragment === null){
            viewFragment = inflater?.inflate(R.layout.me_fragment_layout,container,false)!!
        }
        return viewFragment
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val array = context.resources.getStringArray(R.array.me_adapter)


        for (i in 0..imgData.size -1){
            mData?.add(MeData(imgData.get(i),array[i]))
        }
        val mAdapter = MeAdapter(context)
        mAdapter.setData(mData!!)

        val layManager = GridLayoutManager(context,3)
        me_recycle_view.layoutManager = layManager!!
        me_recycle_view.addItemDecoration(MeAdapterDecoration(context,3))
        me_recycle_view.adapter = mAdapter


    }
}