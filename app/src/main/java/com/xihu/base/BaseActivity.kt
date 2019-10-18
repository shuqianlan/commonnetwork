package com.xihu.base

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.xihu.ui.views.PopupLoadingWindow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity: AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResID())

        initView()
        initData()
    }

    abstract fun layoutResID(): Int
    abstract fun initView()
    abstract fun initData()

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if ((view != null) && isShouldHideInput(view, ev)) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                }
                view.clearFocus()
            }
        }

        println("popupWindow.visible: ${popWindow?.isShowing ?: false}")
        if ((popWindow?.isShowing ?: false)) {
            return false
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideInput(v:View?, ev:MotionEvent):Boolean {
        return if (v != null && (v is EditText)) {
            val leftTop = IntArray(2).apply { fill(0) }
            v.getLocationInWindow(leftTop)
            val left = leftTop[0]
            val top = leftTop[1]
            val bottom = top+v.height
            val right = left + v.width

            !(ev.x > left && ev.x < right && ev.y > top && ev.y < bottom);
        } else {
            false
        }
    }

    private var popWindow: PopupLoadingWindow?=null
    fun showTopLoading(loading: Boolean) {
        popWindow = popWindow ?: PopupLoadingWindow(this)
        popWindow?.apply {
            if (loading) {
                show()
            } else {
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel("cancel", Exception("activity is destroyed"))
    }
}