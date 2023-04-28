package com.peakmain.webview.manager

import android.net.Uri
import android.text.TextUtils
import android.webkit.WebView
import com.peakmain.webview.bean.WebViewEvent
import com.peakmain.webview.sealed.HandleResult
import com.peakmain.webview.utils.LogWebViewUtils
import com.peakmain.webview.utils.WebViewEventManager

/**
 * author ：Peakmain
 * createTime：2023/04/26
 * mail:2726449200@qq.com
 * describe：
 */
internal class WebViewHandle(
    val webView: WebView?,
    private val handleUrlParamsBlock: ((Uri?, WebViewEvent,String?) -> Unit)?
) {
    private val mWebViewEventManager: WebViewEventManager = WebViewEventManager.instance

    /**
     * 处理url
     */
    fun handleUrl(url: String): HandleResult {
        if (!TextUtils.isEmpty(url)) {
            return handleUrl(Uri.parse(url))
        }
        //默认没有处理
        return HandleResult.NotConsume
    }

    /**
     * scheme:[//[user:password@]host[:port]][/]path[?query][#fragment]
     * http://www.example.com:8080/index.html?name=Peamain&age=30#section-1
     *   <p>scheme:协议名称，http</p>
     *   <p>host:主机名，www.example.com</p>
     *   <p>port:主机端口号:8080</p>
     *   <p>authority:主机和端口号，www.example.com:8080</p>
     *   <p>path:资源在服务器上的路径,index/html</p>
     *   <p>query:一串参数，可以用于传递信息，name=Peakmain&age=18</p>
     *   <p>fragment:片段标识符号,section-1</p>
     */
    private fun handleUrl(url: Uri?): HandleResult {
        if (url == null) return HandleResult.NotConsume
        val scheme = url.scheme
        val authority = url.authority
        val path = url.path
        if (!TextUtils.isEmpty(scheme) || !TextUtils.isEmpty(authority)) {
            //http://www.example.com:8080/index.html
            val cmdUri = String.format("%s://%s%s", scheme, authority, path)
            val event = WebViewEvent(webView, webView?.context)
            LogWebViewUtils.e("PkWebView cmdUri:$cmdUri")
            handleUrlParamsBlock?.invoke(url, event,path)
           /* val params = url.getQueryParameter(eventKey)
            if (!TextUtils.isEmpty(params)) {
                val decodeParam: String =
                    EncodeUtils.decode(params!!.replace(" ", "+"))
                LogWebViewUtils.e("PkWebView decodeParam:$decodeParam")
                val webViewModel: WebViewModel =
                    GsonUtils.fromJson(decodeParam, WebViewModel::class.java)
                event.webViewModel = webViewModel
            }*/
            return mWebViewEventManager.execute(cmdUri, event)
        }
        return HandleResult.NotConsume
    }
}