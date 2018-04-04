package com.example.yuxinhua.momeyapp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yuxinhua.momeyapp.R

/**
 * Created by yuxh3
 * On 2018/3/16.
 * Email by 791285437@163.com
 */
class FindFragment : Fragment(){

    var viewFragment: View?= null
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
}