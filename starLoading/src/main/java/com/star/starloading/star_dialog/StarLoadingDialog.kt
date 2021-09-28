package com.star.starloading.star_dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.star.starloading.R
import com.star.starloading.star_view.StarLoadingView

/**
 * @des
 * @date 2021/9/26
 * @author sam
 */
class StarLoadingDialog(private var fragmentActivity: FragmentActivity) : DialogFragment(),
    ILoadingDialogMethod {
    private var loadingView: StarLoadingView? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.widget_layout_loading_view)
        val layoutParams: WindowManager.LayoutParams =
            dialog.window?.attributes ?: throw IllegalAccessException("错误dialog不能继续")
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        dialog.window?.attributes = layoutParams
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setDimAmount(0f)
        dialog.setCanceledOnTouchOutside(false)
        loadingView = dialog.findViewById(R.id.widget_loading_view)
        return dialog
    }

    override fun start() {
        val manager = fragmentActivity.supportFragmentManager
        show(manager, "")
    }

    override fun stop() {
        dismiss()
    }

    override fun setSize(pixSize: Int) {
        loadingView?.setSize(pixSize)
    }

    override fun setColor(color: Int) {
        loadingView?.setColor(color)
    }

    override fun dismiss() {
        loadingView?.visibility = View.GONE
        dialog?.dismiss()
        super.dismiss()
    }


}