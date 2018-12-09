package com.gg.menutablayout

import android.view.View
import android.view.ViewGroup

/**
 * Creator : GG
 * Time    : 2018/12/9
 * Mail    : gg.jin.yu@gmail.com
 * Explain :
 */
interface BaseMenuAdapter {

    //获取总共有多少条
    fun getCount():Int

    //
    fun getMenuTabView(position:Int , viewGroup: ViewGroup) :View

    fun getMenuDataView(position:Int , viewGroup: ViewGroup):View



    fun openMenu(tabView: View){

    }

    fun closeMenu(tabView: View){

    }
}