package com.example.yuxinhua.momeyapp.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.yuxinhua.momeyapp.R
import com.example.yuxinhua.momeyapp.data.FindData

/**
 * Created by yuxh3
 * On 2018/4/9.
 * Email by 791285437@163.com
 */
class FindAdapter(val context: Context): RecyclerView.Adapter<FindAdapter.FindViewHolder>() {

    private var mData:ArrayList<FindData>? = null

    var ISLOADING = 1
    var load_more_status = 0
    var PULLUP_LOAD_MORE = 0

    fun setFindData(mData:ArrayList<FindData>){
        this.mData = mData
    }
    override fun onBindViewHolder(holder: FindAdapter.FindViewHolder?, position: Int) {

        if (holder is FindViewFlourHolder){
            holder?.mCradView?.radius = 1f
            holder?.mCradView?.cardElevation = 5f
            val adapter = FindItemAdapter(context)
            adapter.setData(mData?.get(position)?.data?:null)
            val layoutManager = GridLayoutManager(context,2)

            holder?.mRecyView?.layoutManager = layoutManager
//            holder?.mRecyView?.addItemDecoration(FindItemAdapterDecoration(context,2))
            holder?.mRecyView?.adapter = adapter
        }else if (holder is FootViewHolder){
            val animation = RotateAnimation(0f,360f,RotateAnimation.RELATIVE_TO_SELF,
                    0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f)
            if (load_more_status == ISLOADING){
                holder?.mTv?.text = "正在加载中。。。"
                animation.duration = 2000
                animation.start()
                animation?.repeatCount = -1
                holder?.mIv?.animation = animation
            }else if (load_more_status == PULLUP_LOAD_MORE){
                holder?.mTv?.text = "上拉加载更多"
                animation.cancel()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FindAdapter.FindViewHolder {

        var view:View? = null
        when(viewType){
            0->{
                view = LayoutInflater.from(context).inflate(R.layout.find_one_layout,parent,false)
                return FindViewOneHolder(view)
            }
            1->{
                view = LayoutInflater.from(context).inflate(R.layout.find_two_layout,parent,false)
                return FindViewTwoHolder(view)
            }
            2->{
                view = LayoutInflater.from(context).inflate(R.layout.find_three_layout,parent,false)
                return FindViewThreeHolder(view)
            }
            3 ->{
                view = LayoutInflater.from(context).inflate(R.layout.find_flour_layout,parent,false)
                return FindViewFlourHolder(view)
            }
            mData?.size ->{
                view = LayoutInflater.from(context).inflate(R.layout.find_foot_layout,parent,false)
                return FootViewHolder(view)
            }
        }

        return FindViewHolder(view)
    }

    override fun getItemCount(): Int = mData?.size!!+1
    open class FindViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
    class FindViewOneHolder(itemView: View?) : FindViewHolder(itemView) {
        private var mOneTxt: TextView? = itemView?.findViewById<TextView>(R.id.find_adapter_jifen)
    }
    class FindViewTwoHolder(itemView: View?) : FindViewHolder(itemView) {

        private var mLlOne: LinearLayout? = itemView?.findViewById(R.id.find_ll_two_one)
        private var mLlTwo: LinearLayout? = itemView?.findViewById(R.id.find_ll_two_two)
        private var mLlThree: LinearLayout? = itemView?.findViewById(R.id.find_ll_two_three)

    }
    class FindViewThreeHolder(itemView: View?) : FindViewHolder(itemView) {

        private var mBack: TextView? = itemView?.findViewById<TextView>(R.id.find_three_back)
    }
    class FindViewFlourHolder(itemView: View?) : FindViewHolder(itemView) {
        var mRecyView:RecyclerView? = itemView?.findViewById(R.id.find_adapter_recycle)
        val mCradView:CardView? = itemView?.findViewById(R.id.find_item_cardview)
    }
    class FootViewHolder(itemView: View?):FindViewHolder(itemView){
        val mTv:TextView? = itemView?.findViewById(R.id.tv_find_foot)
        val mIv:ImageView? = itemView?.findViewById(R.id.iv_find_foot)
    }

    override fun getItemViewType(position: Int): Int {

        if (position == mData?.size && mData?.size!! >0){

            return mData?.size?:0
        }

        return mData?.get(position)?.type!!
    }

    fun setState(state:Int){
        load_more_status = state
        notifyDataSetChanged()
    }

}