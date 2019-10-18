package com.xihu.ui.activity

import androidx.databinding.ViewDataBinding
import com.xihu.base.EmptyActivity
import com.xihu.commonnetwork.R
import com.xihu.ui.adapters.DataAdapter
import com.xihu.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

class MainActivity : EmptyActivity() {

    override fun layoutResID()= R.layout.activity_main
    private lateinit var adapter: DataAdapter<ItemBean,ViewDataBinding>

    private val datas:List<ItemBean> by lazy {
        ArrayList<ItemBean>().apply {
            for(i in 1..20) {
                add(ItemBean("Item-$i", "Item-Address:$i", 20+i))
            }
        }
    }

    override fun initView() {
        main_cont.setOnClickListener {
            launch {
                showTopLoading(true)
                delay(6_000)
                adapter.notifyDataSetChanged()
                showTopLoading(false)
            }
        }

        adapter = DataAdapter.DataAdapterBuilder<ItemBean, ViewDataBinding>().apply {
            datas(datas)
            itemClick {view,bean->
                toast(bean.toString())
            }
            layoutId {viewType->
                R.layout.person_item_layout
            }
        }.build()
        person_items.adapter = adapter
    }

    data class ItemBean(val name:String, val address:String, val age:Int)
}
