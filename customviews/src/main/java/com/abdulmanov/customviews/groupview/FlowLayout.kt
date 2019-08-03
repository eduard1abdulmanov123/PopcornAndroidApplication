package com.abdulmanov.customviews.groupview


import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.abdulmanov.customviews.R
import java.util.*

class FlowLayout:ViewGroup {

    private var maxLine: Int = 0
    private var indentsBetweenElements: Float = 0f
    private var gravity: Int = 0
    private val widthList = ArrayList<Int>()
    private lateinit var marginLayoutParams:MarginLayoutParams

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.FlowLayout, 0, 0)
        try {
            maxLine = typedArray.getInt(R.styleable.FlowLayout_max_line, 1)
            indentsBetweenElements =
                typedArray.getDimension(R.styleable.FlowLayout_indents_between_elements, 1f)
            gravity = typedArray.getInt(R.styleable.FlowLayout_gravity, Gravity.LEFT or Gravity.TOP)
            marginLayoutParams = MarginLayoutParams(context,attr)
            marginLayoutParams.setMargins(0,0,0,0)
        } finally {
            typedArray.recycle()
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        var width = 0
        var height = 0

        var lineWidth = 0
        var lineCount = 1

        for (i in 0 until childCount) {
            val v = getChildAt(i)
            if (v.visibility == View.GONE) { // Не виден и не занимает места
                continue
            }
            //LayoutParams
            marginLayoutParams.width = v.layoutParams.width
            marginLayoutParams.height = v.layoutParams.height
            v.layoutParams =  marginLayoutParams
            val lp = v.layoutParams as MarginLayoutParams
            lp.leftMargin = (indentsBetweenElements / 2).toInt()
            lp.rightMargin = (indentsBetweenElements / 2).toInt()

            //childWidth and childWidthType
            var childWidthType = View.MeasureSpec.EXACTLY
            var childWidth = lp.width
            if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
                childWidthType = View.MeasureSpec.EXACTLY
                childWidth = widthSize - paddingLeft - paddingRight
            } else if (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                childWidthType = View.MeasureSpec.AT_MOST
                childWidth = widthSize - paddingLeft - paddingRight + lp.leftMargin + lp.rightMargin
            }

            //childHeight and childHeightType
            var childHeightType = View.MeasureSpec.UNSPECIFIED
            var childHeight = 0
            if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                childHeightType = View.MeasureSpec.AT_MOST
                childHeight =
                    heightSize - paddingTop - paddingBottom + lp.topMargin + lp.bottomMargin
            } else if (lp.height >= 0) {
                childHeightType = View.MeasureSpec.EXACTLY
                childHeight = lp.height
            }

            v.measure(
                View.MeasureSpec.makeMeasureSpec(childWidth, childWidthType),
                View.MeasureSpec.makeMeasureSpec(childHeight, childHeightType)
            )

            val childWidthReal = v.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeightReal = v.measuredHeight +lp.topMargin + lp.bottomMargin
            if (lineWidth + childWidthReal > widthSize) {
                if (++lineCount > maxLine) {
                    removeView(v)
                    break
                }
                width = Math.max(lineWidth, childWidthReal)
                lineWidth = childWidthReal
                height += childHeightReal
            } else {
                lineWidth += childWidthReal
                height = Math.max(height, childHeightReal)
            }
        }
        width = Math.max(lineWidth, width)
        width = if (widthMode == View.MeasureSpec.EXACTLY) widthSize else width
        height = if (heightMode == View.MeasureSpec.EXACTLY) heightSize else height
        setMeasuredDimension(width, height)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        var lineWidth = 0
        val widthSize = measuredWidth
        widthList.clear()

        for (i in 0 until childCount) {
            val v = getChildAt(i)
            if (v.visibility == View.GONE) {
                continue
            }
            val lp = v.layoutParams as MarginLayoutParams
            val childWidth = v.measuredWidth + lp.leftMargin + lp.rightMargin

            if (lineWidth + childWidth > widthSize) {  // Новая линия
                widthList.add(lineWidth)
                lineWidth = childWidth
            } else {
                lineWidth += childWidth
            }
        }
        widthList.add(lineWidth)
        lineWidth = 0


        var top = 0
        var left = 0
        var lineCnt = 0
        for (i in 0 until childCount) {
            val v = getChildAt(i)
            if (v.visibility == View.GONE)
                continue
            val lp = v.layoutParams!! as MarginLayoutParams
            val childWidth = v.measuredWidth + lp.leftMargin + lp.rightMargin
            val childHeight = v.measuredHeight + lp.topMargin + lp.bottomMargin

            var l = left

            if (i == 0 || lineWidth + childWidth > widthSize) {  // Новая линия
                lineWidth = childWidth
                left = when (gravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                    Gravity.CENTER_HORIZONTAL -> (widthSize - widthList[lineCnt++]) / 2
                    Gravity.RIGHT -> widthSize - widthList[lineCnt++]
                    Gravity.LEFT -> 0
                    else -> 0
                }
                l = left
                left += childWidth
                top += childHeight
            } else {
                left += childWidth
                lineWidth += childWidth
            }
            v.layout(
                l + lp.leftMargin,
                top - childHeight + lp.topMargin,
                l + childWidth - lp.rightMargin,
                top - lp.bottomMargin
            )
        }
    }

    override fun addView(child: View?) {
        super.addView(child)
        invalidate()
    }

    /*class LayoutParams : ViewGroup.MarginLayoutParams {

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {}

        constructor(source: ViewGroup.LayoutParams) : super(source) {}

        constructor(width: Int, height: Int) : super(width, height) {}
    }*/

}