package com.peakmain.webview.utils

import java.lang.reflect.Field

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：
 */
internal object ReflectUtils {

    @JvmStatic
    fun invokeStaticMethod(
        clzName: String?,
        methodName: String?,
        methodParamTypes: Array<Class<*>?>,
        vararg methodParamValues: Any?,
    ): Any? {
        if (clzName == null || methodName == null) return null
        try {
            val clz = Class.forName(clzName)
            val med = clz.getDeclaredMethod(methodName, *methodParamTypes)
            med.isAccessible = true
            return med.invoke(null, *methodParamValues)
        } catch (e: Exception) {
            logExceptionMessage(e)
        }
        return null
    }

    fun invokeMethod(
        clzName: String?,
        methodName: String?,
        methodReceiver: Any?,
        methodParamTypes: Array<Class<*>?>,
        vararg methodParamValues: Any?,
    ): Any? {
        try {
            if (methodReceiver == null || clzName == null || methodName == null) {
                return null
            }
            val clz = Class.forName(clzName)
            val med = clz.getDeclaredMethod(methodName, *methodParamTypes)
            med.isAccessible = true
            return med.invoke(methodReceiver, *methodParamValues)
        } catch (e: Exception) {
            logExceptionMessage(e)
        }
        return null
    }

    fun getStaticField(clzName: String?, filedName: String?): Any? {
        if (clzName == null || filedName == null) return null
        try {
            var field: Field? = null
            val clz = Class.forName(clzName)
            field = clz.getField(filedName)
            if (field != null) {
                return field[""]
            }
        } catch (e: Exception) {
            logExceptionMessage(e)
        }
        return null
    }

    fun getField(clzName: String?, obj: Any?, filedName: String?): Any? {
        try {
            if (obj == null || clzName == null || filedName == null) {
                return null
            }
            val clz = Class.forName(clzName)
            val field = clz.getField(filedName)
            return field[obj]
        } catch (e: Exception) {
            logExceptionMessage(e)
        }
        return null
    }

    private fun logExceptionMessage(e: Exception) {
        LogWebViewUtils.e("getStaticField got Exception:$e")
    }
}