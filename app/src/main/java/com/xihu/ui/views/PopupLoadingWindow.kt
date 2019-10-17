package com.xihu.ui.views

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.PopupWindow

import com.xihu.commonnetwork.R
import com.xihu.utils.getWindowSize

class PopupLoadingWindow(private val mContext: Context) : PopupWindow(mContext) {

	private val mWindowWidth: Int
	private val mWindowHeight: Int

	init {
		val ret = IntArray(2)
		mContext.getWindowSize(ret)

		mWindowWidth = ret[0]
		mWindowHeight = ret[1]

		width = (mWindowWidth*0.4).toInt()
		height = (mWindowWidth*0.4).toInt()
		isOutsideTouchable = true
		isFocusable = true

		setAnimationStyle(R.style.PopUpWindowAnim);
		setBackgroundDrawable(ColorDrawable(Color.BLACK))
		LayoutInflater.from(mContext).inflate(R.layout.window_executing_loading, null, false).also {
			contentView = it
		}

		initialize()
	}

	private fun initialize() {
		setOnDismissListener { darkenBackground(1f) }
	}

	private fun darkenBackground(alpha: Float) {
		(mContext as Activity).window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

		val lp = mContext.window.attributes
		lp.alpha = alpha
		mContext.window.attributes = lp
	}
	
	fun show() {
		darkenBackground(0.5f)
		if (mContext is Activity) {
			val group = mContext.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
			showAsDropDown(group, mWindowWidth/2-width/2, (mWindowHeight*0.4).toInt()+height/2, Gravity.START)
		}
	}

}
