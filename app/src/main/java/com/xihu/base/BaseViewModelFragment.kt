package com.xihu.huidefeng.net.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xihu.base.BaseActivity
import com.xihu.base.BaseFragment
import com.xihu.models.ViewModelDelegate

abstract class BaseViewModelFragment<VM:BaseViewModel> : BaseFragment(), ViewModelDelegate<VM> {
	
	protected lateinit var viewModel:VM
	override fun onCreate(savedInstanceState: Bundle?) {
		viewModel = initViewModel(activity as AppCompatActivity)
		super.onCreate(savedInstanceState)
	}
	
	override fun showLoading(it: Boolean) {
		(activity as BaseActivity)?.showTopLoading(it)
	}
	
	override fun onDestroy() {
		super.onDestroy()
		activity?.lifecycle?.removeObserver(viewModel)
	}
	
}