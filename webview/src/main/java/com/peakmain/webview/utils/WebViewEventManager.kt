package com.peakmain.webview.utils

import com.peakmain.webview.annotation.Handler
import com.peakmain.webview.annotation.HandlerMethod
import com.peakmain.webview.bean.WebViewEvent
import com.peakmain.webview.sealed.HandleResult
import java.lang.String.*
import java.lang.reflect.Method

/**
 * author ：Peakmain
 * createTime：2023/04/26
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewEventManager private constructor() {
    private var mHandleMap: MutableMap<String, Method?> = HashMap()

    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewEventManager()
        }
    }

    fun registerEntities(vararg entities: Class<*>) {
        if (mHandleMap.isEmpty()) {
            entities.forEach {
                addEntity(it)
            }
        }
    }

    private fun addEntity(entity: Class<*>) {
        val handler = entity.getAnnotation(Handler::class.java) ?: return
        val authorities = handler.authority
        val scheme = handler.scheme
        for (authority in authorities) {
            val methods = entity.methods ?: break
            for (method in methods) {
                val handlerMethod = method.getAnnotation(HandlerMethod::class.java) ?: continue
                mHandleMap[format(
                    "%s://%s%s", scheme, authority, handlerMethod.path
                )] = method
            }
        }
    }

    fun <T : WebViewEvent> execute(cmdUri: String, event: T?): HandleResult {
        val method = mHandleMap[cmdUri] ?: return HandleResult.NotConsume
        val clazz = method.declaringClass
        val obj = clazz.newInstance()
        return (if (event == null) method.invoke(obj) else method.invoke(
            obj,
            event
        )) as HandleResult
    }
}