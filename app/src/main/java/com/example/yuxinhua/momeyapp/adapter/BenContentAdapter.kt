package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.opengl.Visibility
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.example.notwork.`object`.BenLaiItemData
import com.example.yuxinhua.momeyapp.R

/**
 * Created by yuxh3
 * On 2018/7/3.
 * Email by 791285437@163.com
 */
class BenContentAdapter(val content:Context,val data: BenLaiItemData): RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    val list = ArrayList<Int>()
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {


        if (holder is ViewHolder) {
            holder?.title?.text = "全部-->" + data?.menu.get(position).name
            var mData = data.allCategory.get(position)

            var adapter = BenContentItemAdapter(content, mData)

            val layoutManager = LinearLayoutManager(content)
            layoutManager.orientation = LinearLayoutManager.VERTICAL

            holder?.recyclerView?.adapter = adapter
            holder?.recyclerView?.layoutManager = layoutManager
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
         var view:View? = null
        when (viewType){
            in 0..data?.menu.size -1 ->{
                view = LayoutInflater.from(content).inflate(R.layout.ben_cont, parent, false)
                return ViewHolder(view!!)
            }
            else ->{
                view = LayoutInflater.from(content).inflate(R.layout.ben_cont_bottom, parent, false)
                return ViewHolderTwo(view)
            }
        }

    }

    override fun getItemCount(): Int  = data?.allCategory.size + list.size?:0


    override fun getItemViewType(position: Int): Int {
        return position
    }
    open class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val recyclerView:RecyclerView = itemView.findViewById(R.id.ben_con_recycle)
        val title:TextView = itemView.findViewById(R.id.title)
    }

    open class ViewHolderTwo(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    fun setFoot(count:Int){
        for (i in 1..count) {
            list.add(count)
        }

        notifyDataSetChanged()
    }

    fun getFootSize():Int{
        return list.size
    }

}