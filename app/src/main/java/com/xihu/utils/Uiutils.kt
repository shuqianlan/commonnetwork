package com.xihu.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.google.gson.Gson
import com.xihu.App
import com.xihu.models.ConfigBean
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder

fun Application.initialize() {
    val context = App.context
    val reader = InputStreamReader(context.assets.open("config.json"), "UTF-8")
    reader.read()
    val bufferedReader = BufferedReader(reader)
    val builder = StringBuilder()
    var line:String?
    try {
        do {
            line = bufferedReader.readLine()
            if (line != null) {
                builder.append(line).append("\n")
            }
        } while (line != null)
        ConfigBean.instance = Gson().fromJson(builder.toString(), ConfigBean::class.java)
    } catch (exp: Exception) {
        exp.printStackTrace()
    }
}

fun Activity.toast(msg:String, duration:Int= Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

inline fun <reified T> Activity.startActivity() {
    Intent(this, T::class.java).also { startActivity(it) }
}

fun isLastestVersion(context:Context) {

}

fun View.dp2px(dpVal: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dpVal, resources.displayMetrics).toInt()
}

fun View.sp2px(spVal: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        spVal, resources.displayMetrics).toInt()
}

fun View.px2dp(pxVal: Float): Float {
    val scale = resources.displayMetrics.density
    return pxVal / scale
}

fun View.px2sp(pxVal: Float): Float {
    return pxVal / resources.displayMetrics.scaledDensity
}

fun Context.getWindowSize(result: IntArray) {
    val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    wm.defaultDisplay.getRealSize(point)
    result[0] = point.x
    result[1] = point.y
}

@BindingAdapter("isGone")
fun bindIsGone(view:View, isGone:Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}
