package com.xihu.huidefeng.net.base

import com.xihu.huidefeng.net.beans.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class BaseRepository {
    open suspend fun <T> request(call: suspend () -> ResponseData<T>): ResponseData<T> {
        // withContex的返回值就是lambda表达式的返回值.
        return withContext(Dispatchers.Default) {
            call.invoke()
        }.apply {
            when (code) {
                100 -> throw TokenInvalidException()
            }
        }
    }

    class TokenInvalidException(msg: String="token invalid"):java.lang.Exception(msg)
}