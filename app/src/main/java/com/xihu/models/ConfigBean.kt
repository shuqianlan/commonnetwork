package com.xihu.models

data class ConfigBean(
	val Retrofit: RetrofitBean
) {
	companion object {
		@JvmStatic
		lateinit var instance: ConfigBean
	}
	
	data class RetrofitBean(
			val baseUrl: String,
			val readTimeout: Long,
			val connectTimeout: Long,
			val requestTimeout: Long
	)
}