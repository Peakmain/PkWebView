package com.peakmain.webview.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import java.io.Reader
import java.lang.reflect.Type

/**
 * author ：Peakmain
 * createTime：2023/4/26
 * mail:2726449200@qq.com
 * describe：
 */
object GsonUtils {
    private val gson: Gson = GsonBuilder().disableHtmlEscaping().create()
    @JvmStatic
    fun <T> fromJson(json: String?, c: Class<T>?): T {
        return gson.fromJson(json, c)
    }

    @JvmStatic
    fun <T> fromJson(json: JsonElement?, c: Class<T>?): T {
        return gson.fromJson(json, c)
    }

    @JvmStatic
    fun <T> fromJson(json: JsonElement?, type: Type?): T {
        return gson.fromJson(json, type)
    }

    @JvmStatic
    fun <T> fromJson(json: String?, type: Type?): T {
        return gson.fromJson(json, type)
    }

    @JvmStatic
    fun <T> fromJson(json: Reader?, type: Type?): T {
        return gson.fromJson(json, type)
    }

    @JvmStatic
    fun toJson(src: Any?): String {
        return gson.toJson(src)
    }
}