package com.peakmain.webview.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import androidx.collection.ArraySet
import androidx.core.content.ContextCompat
import com.peakmain.webview.bean.cache.WebResource
import okhttp3.Headers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.util.Locale

/**
 * author ：Peakmain
 * createTime：2023/04/24
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewUtils private constructor() {
    companion object {
        @JvmStatic
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            WebViewUtils()
        }
    }

    private val mCacheContentType = ArrayList<String>()
    private val mCacheImageContentType = ArrayList<String>()

    init {
        //初始化图片
        mCacheImageContentType.add("image/gif")
        mCacheImageContentType.add("image/jpeg")
        mCacheImageContentType.add("image/png")
        mCacheImageContentType.add("image/svg+xml")
        mCacheImageContentType.add("image/bmp")
        mCacheImageContentType.add("image/webp")
        mCacheImageContentType.add("image/tiff")
        mCacheImageContentType.add("image/vnd.microsoft.icon")
        mCacheImageContentType.add("image/x-icon")
        //初始化其他资源
        mCacheContentType.add("application/javascript")
        mCacheContentType.add("application/ecmascript")
        mCacheContentType.add("application/x-ecmascript")
        mCacheContentType.add("application/x-javascript")
        mCacheContentType.add("text/ecmascript")
        mCacheContentType.add("text/javascript")
        mCacheContentType.add("text/javascript1.0")
        mCacheContentType.add("text/javascript1.1")
        mCacheContentType.add("text/javascript1.2")
        mCacheContentType.add("text/javascript1.3")
        mCacheContentType.add("text/javascript1.4")
        mCacheContentType.add("text/javascript1.5")
        mCacheContentType.add("text/jscript")
        mCacheContentType.add("text/livescript")
        mCacheContentType.add("text/x-ecmascript")
        mCacheContentType.add("text/x-javascript")
        mCacheContentType.add("text/css")
        mCacheContentType.add("application/octet-stream")
        mCacheContentType.add("application/pdf")
        mCacheContentType.add("text/plain")
        mCacheContentType.add("text/html")
        mCacheContentType.add("text/xml")

        // 字体文件
        mCacheContentType.add("application/x-font-ttf")
        mCacheContentType.add("application/x-font-opentype")
        mCacheContentType.add("application/vnd.ms-fontobject")
        mCacheContentType.add("application/font-woff")
        mCacheContentType.add("font/woff2")
        mCacheContentType.add("font/ttf")

    }

    @SuppressLint("MissingPermission")
    fun checkNetworkInfo(
        context: Context, noNetworkBlock: (() -> Unit)? = null,
        networkBlock: (() -> Unit)? = null,
    ) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_NETWORK_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (networkCapabilities == null ||
                    (!networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            && !networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                ) {
                    noNetworkBlock?.invoke()
                } else {
                    networkBlock?.invoke()
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
                    noNetworkBlock?.invoke()
                } else {
                    networkBlock?.invoke()
                }
            }
        }

    }

    fun isImageType(contentType: String): Boolean {
        return mCacheImageContentType.contains(contentType)
    }


    fun getMimeType(url: String): String {
        val lowerUrl = url.lowercase()
        return when {
            lowerUrl.endsWith(".js") -> "application/javascript"
            lowerUrl.endsWith(".css") -> "text/css"
            lowerUrl.endsWith(".png") -> "image/png"
            lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg") -> "image/jpeg"
            lowerUrl.endsWith(".gif") -> "image/gif"
            lowerUrl.endsWith(".webp") -> "image/webp"
            lowerUrl.endsWith(".bmp") -> "image/bmp"
            lowerUrl.endsWith(".svg") -> "image/svg+xml"
            lowerUrl.endsWith(".woff") -> "application/font-woff"
            lowerUrl.endsWith(".woff2") -> "font/woff2"
            lowerUrl.endsWith(".ttf") -> "application/x-font-ttf"
            lowerUrl.endsWith(".otf") -> "application/x-font-opentype"
            lowerUrl.endsWith(".eot") -> "application/vnd.ms-fontobject"
            // 可以根据需要添加其他文件类型的 MIME 类型
            else -> "text/plain"
        }
    }
    fun getContentType(resource: WebResource): String? {
        val headers = resource.responseHeaders
        var contentType: String? = null
        if (headers != null) {
            val uppercaseKey = "Content-Type"
            val lowercaseKey = uppercaseKey.lowercase(Locale.getDefault())
            val contentTypeValue =
                if (headers.containsKey(uppercaseKey)) headers[uppercaseKey] else headers[lowercaseKey]


            if (!TextUtils.isEmpty(contentTypeValue)) {
                val contentTypeArray = contentTypeValue!!.split(";").toTypedArray()
                if (contentTypeArray.isNotEmpty()) {
                    contentType = contentTypeArray[0]
                }
            }
        }
        return contentType
    }
    fun isCacheContentType(contentType: String): Boolean {
        return mCacheContentType.contains(contentType)
    }

    fun getMd5(message: String, upperCase: Boolean = true): String {
        var md5str = ""
        try {
            val md = MessageDigest.getInstance("MD5")
            val input = message.toByteArray()
            val buff = md.digest(input)
            md5str = bytesToHex(buff, upperCase)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return md5str
    }

    private fun bytesToHex(bytes: ByteArray, upperCase: Boolean): String {
        val md5str = StringBuffer()
        var digital: Int
        for (i in bytes.indices) {
            digital = bytes[i].toInt()
            if (digital < 0) {
                digital += 256
            }
            if (digital < 16) {
                md5str.append("0")
            }
            md5str.append(Integer.toHexString(digital))
        }
        return if (upperCase) {
            md5str.toString().uppercase(Locale.getDefault())
        } else md5str.toString().uppercase(Locale.getDefault())
    }

    /**
     * 根据statusCode 获取状态值
     */
    fun getMessage(statusCode: Int): String? {
        return when (statusCode) {
            100 -> "Continue"
            101 -> "Switching Protocols"
            200 -> "OK"
            201 -> "Created"
            202 -> "Accepted"
            203 -> "Non-Authoritative Information"
            204 -> "No Content"
            205 -> "Reset Content"
            206 -> "Partial Content"
            300 -> "Multiple Choices"
            301 -> "Moved Permanently"
            302 -> "Found"
            303 -> "See Other"
            304 -> "Not Modified"
            305 -> "Use Proxy"
            306 -> "Unused"
            307 -> "Temporary Redirect"
            400 -> "Bad Request"
            401 -> "Unauthorized"
            402 -> "Payment Required"
            403 -> "Forbidden"
            404 -> "Not Found"
            405 -> "Method Not Allowed"
            406 -> "Not Acceptable"
            407 -> "Proxy Authentication Required"
            408 -> "Request Time-out"
            409 -> "Conflict"
            410 -> "Gone"
            411 -> "Length Required"
            412 -> "Precondition Failed"
            413 -> "Request Entity Too Large"
            414 -> "Request-URI Too Large"
            415 -> "Unsupported Media Type"
            416 -> "Requested range not satisfiable"
            417 -> "Expectation Failed"
            500 -> "Internal Server Error"
            501 -> "Not Implemented"
            502 -> "Bad Gateway"
            503 -> "Service Unavailable"
            504 -> "Gateway Time-out"
            505 -> "HTTP Version not supported"
            else -> "unknown"
        }
    }

    /**
     * 获取资源文件的类型扩展名
     */
    fun getFileExtensionFromUrl(url: String): String? {
        var url = url
        url = url.lowercase(Locale.getDefault())
        if (!TextUtils.isEmpty(url)) {
            val fragment = url.lastIndexOf('#')
            if (fragment > 0) {
                url = url.substring(0, fragment)
            }
            val query = url.lastIndexOf('?')
            if (query > 0) {
                url = url.substring(0, query)
            }
            val filenamePos = url.lastIndexOf('/')
            val filename = if (0 <= filenamePos) url.substring(filenamePos + 1) else url

            // 如果文件名包含特殊字符，我们认为它对我们的匹配目的无效
            if (filename.isNotEmpty()) {
                val dotPos = filename.lastIndexOf('.')
                if (0 <= dotPos) {
                    return filename.substring(dotPos + 1)
                }
            }
        }
        return ""
    }

    fun getVersionCode(context: Context): Long {
        val packageManager = context.packageManager
        val packageInfo: PackageInfo
        var versionCode = 0L
        try {
            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = packageInfo.longVersionCode
            } else {
                versionCode = packageInfo.versionCode.toLong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionCode
    }

    fun generateHeadersMap(headers: Headers): Map<String, String>? {
        val headersMap: MutableMap<String, String> = HashMap()
        for (key in headers.names()) {
            val values = StringBuilder()
            for (value in listToSet(headers.values(key))) {
                if (!TextUtils.isEmpty(values)) {
                    values.append(" ")
                }
                values.append(value)
            }
            headersMap[key] = values.toString().trim { it <= ' ' }
        }
        return headersMap
    }

    private fun listToSet(origin: List<String?>): Set<String?> {
        val target: MutableSet<String?> = ArraySet(origin.size)
        target.addAll(origin)
        return target
    }

    @Throws(IOException::class)
    fun streamToBytes(`in`: InputStream): ByteArray? {
        val out = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len: Int
        while (`in`.read(buffer).also { len = it } > -1) {
            out.write(buffer, 0, len)
        }
        out.flush()
        `in`.close()
        return out.toByteArray()
    }

    @Throws(IOException::class)
    fun deleteContents(dir: File) {
        val files = dir.listFiles() ?: throw IOException("not a readable directory: $dir")
        for (file in files) {
            if (file.isDirectory) {
                deleteContents(file)
            }
            if (!file.delete()) {
                throw IOException("failed to delete file: $file")
            }
        }
    }
}