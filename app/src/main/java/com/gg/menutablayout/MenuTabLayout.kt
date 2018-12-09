package com.gg.menutablayout

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

/**
 * Creator : GG
 * Time    : 2018/12/4
 * Mail    : gg.jin.yu@gmail.com
 * Explain :
 */
class MenuTabLayout : LinearLayout, View.OnClickListener {


    private var mTopMenuLayout: LinearLayout

    private var mDataLayout: LinearLayout

    private var mShadowView: View

    private val mShadowColor = -0x77777778

    private var mMenuContainerView: FrameLayout

    // 内容菜单的高度
    private var mMenuContainerHeight = 0f

    private lateinit var mAdapter: BaseMenuAdapter

    private var currentPosition = -1

    private var mAnimationExecute = false

    private var DURATION_TIME = 300L


    constructor(context: Context) : this(context, null) {

    }


    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0) {

    }

    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(context, attr) {

    }

    init {
        orientation = LinearLayout.VERTICAL
        //创建头部布局 用来存放tab
        mTopMenuLayout = LinearLayout(context).apply {

            LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }


        addView(mTopMenuLayout)

        //创建数据布局
        mDataLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0).apply {
                weight = 1f
            }

        }
        addView(mDataLayout)

        mShadowView = View(context)

        mShadowView.setBackgroundColor(mShadowColor)
        mShadowView.alpha = 0f
        mShadowView.setOnClickListener(this)
        mShadowView.visibility = View.GONE


        mMenuContainerView = FrameLayout(context).apply {
            setBackgroundColor(Color.WHITE)
        }
        mDataLayout.addView(mMenuContainerView)

        mDataLayout.addView(mShadowView)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        if (mMenuContainerHeight == 0f && height > 0) {
            mMenuContainerHeight = (height * 75f / 100f)

            mMenuContainerView.layoutParams.apply {
                this.height = mMenuContainerHeight.toInt()
            }
            mMenuContainerView.translationY = -mMenuContainerHeight
        }
    }


    fun setAdapter(adapter: BaseMenuAdapter) {
        this.mAdapter = adapter
        val count = mAdapter.getCount()
        for (i in 0 until count) {
            // 获取菜单的Tab
            val tab = mAdapter.getMenuTabView(i, mTopMenuLayout).apply {
                layoutParams = (layoutParams as LinearLayout.LayoutParams).apply {
                    weight = 1f
                }
            }
            mTopMenuLayout.addView(tab)
            // 设置tab点击事件
            setTabClick(tab, i)
            // 获取菜单的内容

            val dataView = mAdapter.getMenuDataView(i, mMenuContainerView)
            dataView.visibility = View.GONE
            mMenuContainerView.addView(dataView)
        }
    }

    private fun setTabClick(tab: View, position: Int) {
        tab.setOnClickListener {
            if (currentPosition == -1) {
                openMenu(position, tab)
            } else {
                if (currentPosition == position) {
                    closeMenu()
                } else {
                    var currentView = mMenuContainerView.getChildAt(currentPosition)
                    currentView.visibility = View.GONE
                    mAdapter.closeMenu(currentView)
                    currentPosition = position
                    currentView = mMenuContainerView.getChildAt(currentPosition)
                    currentView.visibility = View.VISIBLE
                    mAdapter.openMenu(currentView)

                }
            }
        }
    }

    private fun closeMenu() {
        if (mAnimationExecute)
            return

        mShadowView.visibility = View.VISIBLE


        ObjectAnimator.ofFloat(mMenuContainerView, "translationY", 0f, -mMenuContainerHeight).run {
            duration = DURATION_TIME
            start()
        }

        ObjectAnimator.ofFloat(mShadowView, "alpha", 1f, 0f).apply {
            duration = DURATION_TIME
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)

                    mAnimationExecute = true
                    mAdapter.closeMenu(mMenuContainerView.getChildAt(currentPosition))
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mMenuContainerView.getChildAt(currentPosition).visibility = View.GONE
                    mShadowView.visibility = View.GONE

                    currentPosition = -1
                    mAnimationExecute = false
                }
            })
            start()
        }
    }

    private fun openMenu(position: Int, tab: View) {
        if (mAnimationExecute)
            return

        mShadowView.visibility = View.VISIBLE

        val menuView = mMenuContainerView.getChildAt(position)
        menuView.visibility = View.VISIBLE

        ObjectAnimator.ofFloat(mMenuContainerView, "translationY", -mMenuContainerHeight, 0f).run {
            duration = DURATION_TIME
            start()
        }
        ObjectAnimator.ofFloat(mShadowView, "alpha", 0f, 1f).apply {
            duration = DURATION_TIME
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)

                    mAnimationExecute = true
                    mAdapter.openMenu(tab)
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    mAnimationExecute = false
                    currentPosition = position
                }
            })
            start()
        }

    }


    override fun onClick(p0: View?) {
        closeMenu()
    }
}