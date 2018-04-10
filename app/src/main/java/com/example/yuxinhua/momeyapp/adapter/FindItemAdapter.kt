package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.data.FindItemData

/**
 * Created by yuxh3
 * On 2018/4/10.
 * Email by 791285437@163.com
 */
class FindItemAdapter(val context: Context): Adapter<FindItemAdapter.FindItemHolder>() {


    private var mData:ArrayList<FindItemData>? = null

    fun setData(mData:ArrayList<FindItemData>?){
        this.mData = mData
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FindItemAdapter.FindItemHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.find_item_layout,parent,false)

        return FindItemHolder(view)
    }

    override fun onBindViewHolder(holder: FindItemAdapter.FindItemHolder?, position: Int) {
        holder?.mTvMoney?.text = mData?.get(position)?.money
        holder?.mTvContent?.text = mData?.get(position)?.content
    }

    override fun getItemCount(): Int  = mData?.size?:0

    class FindItemHolder: RecyclerView.ViewHolder {

        var mTvMoney:TextView? = null
        var mTvContent:TextView? = null
        constructor(itemView: View?) : super(itemView){
            mTvMoney = itemView?.findViewById<TextView>(R.id.tv_find_item)
            mTvContent = itemView?.findViewById<TextView>(R.id.txt_find_item)
        }
    }
}