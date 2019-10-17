package com.xihu.huidefeng.net.base

import androidx.lifecycle.*
import com.xihu.models.ConfigBean
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import java.lang.Exception

open class BaseViewModel : ViewModel(), LifecycleObserver {

    private val error by lazy { MutableLiveData<Exception>() }
    private val loading by lazy { MutableLiveData<Boolean>() }

    fun launchUI(block: suspend CoroutineScope.()->Unit) = viewModelScope.launch {
        loading.value = true
        try {
            withTimeoutOrNull(ConfigBean.instance.Retrofit.requestTimeout) { // 超时则抛出异常TimeoutCancellationException
                block() // 此处切换到线程池的上下文.
            }
        } catch (e: Exception) {
            onError()
            error.value = e
        } finally {
            loading.value = false
        }
    }

    fun getError(): LiveData<Exception> {
        return error
    }

    fun loading(): LiveData<Boolean> {
        return loading
    }

    open fun onError() {}
}