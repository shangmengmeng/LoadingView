package com.star.starloading.star_view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.star.starloading.R

/**
 * @des
 * @date 2021/9/23
 * @author sam
 */
private const val LINE_COUNT = 12
private const val DEGREE_PER_LINE = 30

class StarLoadingView : View, ILoadingMethod {

    private var mSize = 0;
    private var mPaintColor: Int = Color.WHITE
    private var mAnimatorValue = 0
    private lateinit var mPaint: Paint
    private var mAnimator: ValueAnimator? = null


    private val mAnimatorValueListener =
        ValueAnimator.AnimatorUpdateListener { animation ->
            mAnimatorValue = animation?.animatedValue as Int
            invalidate()
        }

    constructor(context: Context, size: Int, color: Int) : super(context) {
        mSize = size
        mPaintColor = color
    }

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.StarLoadingView)
        mSize = typeArray.getDimensionPixelSize(
            R.styleable.StarLoadingView_star_loading_size,
            dp2px(context, 32)
        )
        mPaintColor = typeArray.getColor(R.styleable.StarLoadingView_android_color, Color.WHITE)
        typeArray.recycle()
        initPaint()
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint.color = mPaintColor
        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND
    }

    fun setColor(color: Int) {
        mPaintColor = color
        invalidate()
    }

    fun setSize(pixSize: Int) {
        mSize = pixSize
    }

    override fun start() {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, LINE_COUNT - 1)
            mAnimator?.apply {
                addUpdateListener(mAnimatorValueListener)
                duration = 600
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                interpolator = LinearInterpolator()
                start()
            }
        } else if (mAnimator!!.isStarted) {
            mAnimator!!.start()
        }
    }

    override fun stop() {
        mAnimator?.apply {
            removeUpdateListener(mAnimatorValueListener)
            removeAllUpdateListeners()
            cancel()
            mAnimator = null
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(mSize, mSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val saveCount: Int =
            canvas?.saveLayer(0f, 0f, height.toFloat(), width.toFloat(), null, Canvas.ALL_SAVE_FLAG)
                ?: 0
        drawLoading(canvas, mAnimatorValue * DEGREE_PER_LINE)
        canvas?.restoreToCount(saveCount)
    }

    private fun drawLoading(canvas: Canvas?, rotateDegrees: Int) {
        val width = mSize / 12
        val height = mSize / 6
        mPaint.strokeWidth = width.toFloat()

        canvas?.apply {
            rotate(rotateDegrees.toFloat(), (mSize / 2).toFloat(), (mSize / 2).toFloat())
            translate((mSize / 2).toFloat(), (mSize / 2).toFloat())

            for (i in 0 until LINE_COUNT) {
                rotate(DEGREE_PER_LINE.toFloat())
                mPaint.alpha =
                    (255f * (i + 1) / LINE_COUNT).toInt()
                translate(0f, (-mSize / 2 + width / 2).toFloat())
                drawLine(0f, 0f, 0f, height.toFloat(), mPaint)
                translate(0f, (mSize / 2 - width / 2).toFloat())
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == VISIBLE) {
            start()
        } else {
            stop()
        }
    }


    private fun dp2px(context: Context, dp: Int): Int {
        return (context.resources.displayMetrics.scaledDensity * dp + 0.5).toInt()
    }

}