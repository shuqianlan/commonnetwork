package com.xihu.models

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.xihu.huidefeng.net.base.BaseRepository
import com.xihu.huidefeng.net.base.BaseViewModel
import com.xihu.utils.toast
import kotlinx.coroutines.TimeoutCancellationException
import java.lang.Exception

interface ViewModelDelegate<VM:BaseViewModel> {
	fun providerVMClass():Class<VM>
	
	fun initViewModel(context: AppCompatActivity):VM {
		// TOOD: 这个写法有点奇怪，使用委托类？？
		return ViewModelProvider.NewInstanceFactory().create(providerVMClass()).also {
			context.lifecycle.addObserver(it)
			it.getError().observe(context, Observer {
				requestError(it, context)
			})
			
			it.loading().observe(context, Observer {
				showLoading(it)
			})
		}
	}

	fun showLoading(it:Boolean)
	
	private fun requestError(it: Exception?, activity: AppCompatActivity) {
		it?.let {
			when(it) {
				is TimeoutCancellationException -> {
					activity.toast("请求超时")
				}
				is BaseRepository.TokenInvalidException -> {
					it.message?.let {
						activity.toast(it)
					}
				}
				else -> {
					println("Exception:${it.message}")
				}
			}
		}
	}
	
}