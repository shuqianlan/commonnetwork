package com.xihu.huidefeng.net.jetpack

import androidx.lifecycle.MutableLiveData
import com.xihu.huidefeng.net.base.BaseViewModel
import com.xihu.huidefeng.net.beans.LoginResponse
import com.xihu.huidefeng.net.repository.RemoteRepository

class LoginViewModel : BaseViewModel() {
	val loginStatus = MutableLiveData<LoginResponse>()
	
	fun login() = launchUI {
		val response = RemoteRepository.instance.login()
		println("login_response $response")
		loginStatus.value = response.data
	}
}