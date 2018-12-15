package com.gg.menutablayout

import android.view.View
import android.view.ViewGroup

/**
 * Creator : GG
 * Time    : 2018/12/9
 * Mail    : gg.jin.yu@gmail.com
 * Explain :
 */
abstract class BaseMenuAdapter {

     var menuCloseObserver: MenuCloseObserver? = null

    fun registerCloseMenuObserver(observer: MenuCloseObserver) {
        menuCloseObserver = observer
    }

    fun unregisterCloseObserver(observer: MenuCloseObserver) {
        menuCloseObserver = null
    }

    //获取总共有多少条
   abstract fun getCount(): Int

    //
    abstract  fun getMenuTabView(position: Int, viewGroup: ViewGroup): View

    abstract fun getMenuDataView(position: Int, viewGroup: ViewGroup): View


    fun openMenu(tabView: View) {

    }

    fun closeMenu(tabView: View) {

    }
}