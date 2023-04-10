package com.peakmain.webview.implement

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.bean.ActivityResultBean
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.constants.WebViewConstants
import com.peakmain.webview.helper.PkStartActivityResultContracts
import com.peakmain.webview.interfaces.H5IntentConfig

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultH5IntentConfigImpl : H5IntentConfig {
    override fun startActivity(context: Context?, url: String) {
        context?.startActivity(
            Intent(context, WebViewActivity::class.java)
                .putExtra(WebViewConstants.LIBRARY_WEB_VIEW, WebViewConfigBean(url))
        )
    }

    override fun startActivityForResult(context: Activity?, url: String, requestCode: Int) {
        val intent = Intent(context, WebViewActivity::class.java)
            .putExtra(WebViewConstants.LIBRARY_WEB_VIEW, WebViewConfigBean(url))
        context?.startActivityForResult(intent, requestCode)
    }

    override fun startActivityForResult(
        context: Fragment?, url: String, requestCode: Int,
        block: ((ActivityResultBean) -> Unit)?) {
        if (context == null || context.context == null) return
        val activityFrResult = context.registerForActivityResult(PkStartActivityResultContracts(requestCode)) {
            //返回结果
            block?.invoke(it)
        }
        val intent = Intent(context.context, WebViewActivity::class.java)
            .putExtra(WebViewConstants.LIBRARY_WEB_VIEW, WebViewConfigBean(url))
        activityFrResult.launch(intent)
    }
}