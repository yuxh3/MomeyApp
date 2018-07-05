package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.notwork.`object`.BenLaiItemAllCategory
import com.example.yuxinhua.momeyapp.R

/**
 * Created by yuxh3
 * On 2018/7/3.
 * Email by 791285437@163.com
 */
class BenContentItemAdapter(val content:Context, val data: BenLaiItemAllCategory): RecyclerView.Adapter<BenContentItemAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.name?.text = data.category.get(position).name
        var adapter = BenContentImgAdapter(content,data.category.get(position))
        val manager = GridLayoutManager(content,3)
        manager.orientation = GridLayoutManager.VERTICAL

        if (holder?.recycler?.parent !== null){
            holder?.recycler?.removeAllViews()
        }
        holder?.recycler?.adapter = adapter
        holder?.recycler?.layoutManager = manager
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BenContentItemAdapter.ViewHolder {

        val view = LayoutInflater.from(content).inflate(R.layout.ben_cont_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  = data?.category.size?:0


    open class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val name:TextView = itemView.findViewById(R.id.tv_con)
        val recycler:RecyclerView = itemView.findViewById(R.id.con_recycle)
    }

}