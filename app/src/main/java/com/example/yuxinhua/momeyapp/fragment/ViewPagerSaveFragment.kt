package com.example.yuxinhua.momeyapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.adapter.InvestAdapter
import com.example.yuxinhua.momeyapp.data.InvestData
import com.example.yuxinhua.momeyapp.inteface.Callback
import kotlinx.android.synthetic.main.invest_fragment_layout.*
import kotlinx.android.synthetic.main.save_fragment.*

/**
 * Created by yuxh3
 * On 2018/3/25.
 * Email by 791285437@163.com
 */
class ViewPagerSaveFragment : BaseFragment() {

    var vie:View? = null
    var adapter:InvestAdapter? = null
    val data:ArrayList<InvestData> = arrayListOf()
    var mNewData:ArrayList<InvestData> = arrayListOf()
    companion object {
        fun getInstance(): Fragment {
            val saveFragment = ViewPagerSaveFragment()
            return saveFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (vie === null) {
            vie = inflater?.inflate(R.layout.save_fragment, container, false)
        }
        return vie
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh.setColorSchemeResources(R.color.inverst_head_yellow)

        swipe_refresh.setOnRefreshListener {

        }

        setDataToAdapter()
        setRefresh()
    }

    fun setDataToAdapter(){
        for (i in 0..10){
            data.add(InvestData("【金融代码号:B2018329-0"+i+"】","10"+i,"50"+i,""+i,
                    (60+i*2).toString()))
        }

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        save_recycle_view.layoutManager = layoutManager
        adapter =InvestAdapter(context,data)
        save_recycle_view.adapter = adapter

    }

    private fun DiffUtils(){
        val diffResult = DiffUtil.calculateDiff(Callback(data,mNewData),true)
        diffResult.dispatchUpdatesTo(adapter)
    }

    /**
     * 下拉刷新
     */
    fun setRefresh(){
        swipe_refresh.setColorSchemeResources(R.color.inverst_head_yellow)
        swipe_refresh.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {

                    data.add(0,InvestData("【金融代码号:B2018329-0"+"new"+"】","10","50","10",
                            (60+1*2).toString()))
                    DiffUtils()
                    mNewData = data
                    adapter?.setData(mNewData)

                    swipe_refresh.isRefreshing = false
            }

        })
    }
}