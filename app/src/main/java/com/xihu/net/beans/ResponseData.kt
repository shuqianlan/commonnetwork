package com.xihu.huidefeng.net.beans

data class ResponseData<out T>(val code:Int, val msg:String, val data:T)