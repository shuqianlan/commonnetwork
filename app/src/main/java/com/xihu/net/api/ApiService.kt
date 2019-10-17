package com.xihu.huidefeng.net.api

import com.xihu.huidefeng.net.beans.*
import retrofit2.http.*

interface ApiService {

	// DEMO_API_SERVICE
	@POST("register")
	suspend fun register(@Field("phone") phone:String, @Field("pwd") pwd:String, @Field("team_code") team_code:String="", client_id:String):ResponseData<LoginResponse>
	
	@POST("login")
	suspend fun login(@Field("phone") phone:String, @Field("pwd") pwd: String, @Field("client_id") client_id:String):ResponseData<LoginResponse>
	
	@POST("login")
	suspend fun login(@Field("phone") phone:String, @Field("token") token: String):ResponseData<LoginResponse>


}