package com.example.yuxinhua.momeyapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.adapter.FindAdapter
import com.example.yuxinhua.momeyapp.data.FindData
import com.example.yuxinhua.momeyapp.data.FindItemData
import com.example.yuxinhua.momeyapp.inteface.FindCallback
import kotlinx.android.synthetic.main.find_fragment_layout.*

/**
 * Created by yuxh3
 * On 2018/3/16.
 * Email by 791285437@163.com
 */
class FindFragment : BaseFragment(){

    var viewFragment: View?= null
    var adapter:FindAdapter? = null
    private var mData:ArrayList<FindData> = arrayListOf()
    private var mItemData:ArrayList<FindItemData> = arrayListOf()
    private var mNewData:ArrayList<FindData> = arrayListOf()
    companion object {
        fun getInstance(): Fragment {
            val findFragment = FindFragment()
            return findFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewFragment === null){
            viewFragment = inflater?.inflate(R.layout.find_fragment_layout,container,false)!!
        }
        return viewFragment
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        find_ll_top.setPadding(0,getStatusHeight(),0,0)

        adapter = FindAdapter(context)
        adapter?.setFindData(mData)

        adapter?.setState(adapter?.PULLUP_LOAD_MORE!!)
        val layoutManager = LinearLayoutManager(context)
        find_recycle_view.layoutManager = layoutManager
        find_recycle_view.adapter =adapter

        find_recycle_view.smoothScrollToPosition(0)
        flashData()

        loadMoreData()
    }

    var lastVisibleItemPosition = 0
    var isLast= false
    fun loadMoreData() {
        find_recycle_view.setOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!find_refresh_view.isRefreshing) {
                    isLast = lastVisibleItemPosition + 1 == adapter?.getItemCount()
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition + 1 == adapter?.getItemCount())) {
                        //上啦加载，添加数据
//                        loarmoreData()
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                lastVisibleItemPosition = (recyclerView?.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()


            }

        })

    }

    fun loarmoreData(){

        adapter?.setState(adapter?.ISLOADING!!)

        setItemData()

        adapter?.setState(adapter?.PULLUP_LOAD_MORE!!)
    }


    fun setData(){

        for(i in 0..3){
            for (x in 1..5) {
                if (i != 3){
                    mItemData.add(FindItemData("10元激活红包", "200"))
                }

            }
            if (i != 3){
                mData.add(FindData(i,null))
            }else{
                mData.add(FindData(i, mItemData))
            }
        }
    }

    fun setItemData(){

        for (x in 1..6) {
                mItemData.add(FindItemData("10元激活红包", "200"))
            }

        mData.add(FindData(3, mItemData))

    }

    private fun DiffUtils(){
        val diffResult = DiffUtil.calculateDiff(FindCallback(mData,mNewData),true)
        diffResult.dispatchUpdatesTo(adapter)
    }
    private fun flashData(){
        find_refresh_view.setColorSchemeResources(R.color.inverst_head_yellow)
        find_refresh_view.setOnRefreshListener(object :SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {

//                setData()
                refalshItemData()
//                find_recycle_view.smoothScrollToPosition(0)
                DiffUtils()
                mNewData = mData
                adapter?.setFindData(mNewData)
                find_refresh_view.isRefreshing = false

            }
        })
    }

    /**
     * 刷新数据
     */
    fun refalshItemData(){

        for(i in 0..3){
            for (x in 1..5) {
                if (i != 3){
                    mItemData.add(FindItemData("10元激活红包", "200"))
                }

            }
            if (i == 3){
                mData.add(FindData(i, mItemData))
            }
        }
    }
}