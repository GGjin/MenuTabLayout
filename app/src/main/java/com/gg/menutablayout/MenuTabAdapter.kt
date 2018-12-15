package com.gg.menutablayout

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

/**
 * Creator : GG
 * Time    : 2018/12/9
 * Mail    : gg.jin.yu@gmail.com
 * Explain :
 */
class MenuTabAdapter(context: Context) : BaseMenuAdapter() {


    private var mContext: Context = context

    private val items = arrayListOf("类型", "品牌", "价格", "更多")

    override fun getCount(): Int {
        return items.size
    }

    override fun getMenuTabView(position: Int, viewGroup: ViewGroup): View {
        val textView = LayoutInflater.from(mContext).inflate(R.layout.menu_tab, viewGroup, false) as TextView

        return textView.apply {
            text = items[position]
            setTextColor(Color.BLACK)
        }

    }

    override fun getMenuDataView(position: Int, viewGroup: ViewGroup): View {
        val textView = LayoutInflater.from(mContext).inflate(R.layout.menu_data, viewGroup, false) as TextView

        return textView.apply {
            text = items[position]
            setTextColor(Color.BLACK)
            setOnClickListener {
                if (menuCloseObserver != null)
                    menuCloseObserver!!.closeMenu()

                Toast.makeText(mContext, "hah", Toast.LENGTH_LONG).show()
            }
        }
    }
}