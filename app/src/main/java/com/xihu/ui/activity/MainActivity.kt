package com.xihu.ui.activity

import com.xihu.base.EmptyActivity
import com.xihu.commonnetwork.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

class MainActivity : EmptyActivity() {

    override fun layoutResID()= R.layout.activity_main

    override fun initView() {
        main_cont.setOnClickListener {
            launch {
                showTopLoading(true)
                delay(6_000)
                showTopLoading(false)
            }
        }
    }
}
