package com.xihu.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DataAdapter<T, R:ViewDataBinding> private constructor(): RecyclerView.Adapter<DataHolder>() {

    private var layoutId: ((Int)->Int)?=null
    private var datas = listOf<T>()
    private var itemClick: ((View,T)->Unit)?=null
    private var viewType: ((T)->Int)?=null
    private var bindView: ((T, Int, View)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val binder = DataBindingUtil.inflate<R>(LayoutInflater.from(parent.context), layoutId!!(viewType), parent, false)
        return DataHolder(binder)
    }

    override fun getItemCount() = datas.size

    override fun getItemViewType(position: Int): Int {
        return viewType?.invoke(datas[position]) ?: 0
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        bindView?.invoke(datas[position], position, holder.itemView) ?: holder.binder.setVariable(1, datas[position])

        holder.itemView.setOnClickListener {
            itemClick?.invoke(it, datas[position])
        }
    }

    class DataAdapterBuilder<B,S:ViewDataBinding> {
        private val adapter = DataAdapter<B,S>()

        fun datas(datas:List<B>): DataAdapterBuilder<B,S> {
            adapter.datas = datas
            return this
        }

        fun layoutId(layoutId: ((Int)->Int)?=null):DataAdapterBuilder<B,S> {
            adapter.layoutId = layoutId
            return this
        }

        fun viewType(viewType:((B)->Int)?=null):DataAdapterBuilder<B,S> {
            adapter.viewType = viewType
            return this
        }

        fun itemClick(click:((View,B)->Unit)?=null):DataAdapterBuilder<B,S> {
            adapter.itemClick = click
            return this
        }

        fun bindView(bindView:((B, Int, View)->Unit)?=null):DataAdapterBuilder<B,S> {
            adapter.bindView = bindView
            return this
        }

        fun build(): DataAdapter<B,S>{
            return adapter
        }
    }
}