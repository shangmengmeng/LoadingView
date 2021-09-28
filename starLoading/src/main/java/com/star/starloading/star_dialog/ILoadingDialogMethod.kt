package com.star.starloading.star_dialog

import androidx.annotation.ColorInt

/**
 * @des
 * @date 2021/9/27
 * @author sam
 */
interface ILoadingDialogMethod {
    /**
     * 开启
     */
    fun start()

    /**
     * 关闭
     */
    fun stop()

    /**
     * 大小
     */
    fun setSize(pixSize: Int)

    /**
     * 颜色
     */
    fun setColor(@ColorInt color: Int)
}