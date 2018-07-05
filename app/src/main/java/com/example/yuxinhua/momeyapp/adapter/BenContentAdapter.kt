package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.notwork.`object`.BenLaiItemData
import com.example.yuxinhua.momeyapp.R

/**
 * Created by yuxh3
 * On 2018/7/3.
 * Email by 791285437@163.com
 */
class BenContentAdapter(val content:Context,val data: BenLaiItemData): RecyclerView.Adapter<BenContentAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {


        holder?.title?.text = "全部-->"+data?.menu.get(position).name
         var mData = data.allCategory.get(position)

        var adapter = BenContentItemAdapter(content,mData)

        val layoutManager = LinearLayoutManager(content)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        holder?.recyclerView?.adapter = adapter
        holder?.recyclerView?.layoutManager = layoutManager

    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BenContentAdapter.ViewHolder {

        var view = LayoutInflater.from(content).inflate(R.layout.ben_cont, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = data?.allCategory.size?:0


    open class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val recyclerView:RecyclerView = itemView.findViewById(R.id.ben_con_recycle)
        val title:TextView = itemView.findViewById(R.id.title)
    }

}