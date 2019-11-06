package com.abdulmanov.customviews.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Scroller
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.abdulmanov.customviews.R

class NoSwipingViewPager:ViewPager {

    constructor(context: Context) : super(context){
        setScroller()
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr){
        setScroller()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    private fun setScroller(){
        try {
            val viewPager = ViewPager::class.java
            val scroller = viewPager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller.set(this,CustomScroller(context))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    inner class CustomScroller(context: Context):Scroller(context){
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, 0)
        }
    }
}