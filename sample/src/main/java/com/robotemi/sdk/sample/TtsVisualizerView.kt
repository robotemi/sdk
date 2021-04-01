package com.robotemi.sdk.sample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View


class TtsVisualizerView constructor(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private var mBytes: ByteArray? = null

    private var mPoints: FloatArray? = null

    private val mRect: Rect = Rect()

    private val mForePaint: Paint = Paint()

    init {
        init()
    }

    private fun init() {
        mBytes = null
        mForePaint.strokeWidth = 4F
        mForePaint.color = context.getColor(R.color.temi_primary_green)
    }

    fun updateVisualizer(bytes: ByteArray?) {
        mBytes = bytes
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mBytes == null) {
            return
        }
        if (mPoints == null || mPoints!!.size < mBytes!!.size * 4) {
            mPoints = FloatArray(mBytes!!.size * 4)
        }
        mRect.set(0, 0, width, height)
        for (i in 0 until mBytes!!.size - 1) {
            mPoints!![i * 4] = mRect.width() * i / (mBytes!!.size - 1) * 1F
            mPoints!![i * 4 + 1] = (mRect.height() / 2F
                    + (mBytes!![i] + 128).toByte() * (mRect.height() / 2) / 128)
            mPoints!![i * 4 + 2] = mRect.width() * (i + 1) / (mBytes!!.size - 1) * 1F
            mPoints!![i * 4 + 3] = (mRect.height() / 2F
                    + (mBytes!![i + 1] + 128).toByte() * (mRect.height() / 2)
                    / 128)
        }
        canvas.drawLines(mPoints!!, mForePaint)
    }
}