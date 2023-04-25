package com.peakmain.webview.implement

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.constants.WebViewConstants
import com.peakmain.webview.interfaces.H5IntentConfig

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
class DefaultH5IntentConfigImpl : H5IntentConfig {
    override fun startActivity(context: Context?, bean: WebViewConfigBean) {
        context?.startActivity(
            Intent(context, WebViewActivity::class.java)
                .putExtra(WebViewConstants.LIBRARY_WEB_VIEW, bean)
        )
    }

    override fun startActivityForResult(
        context: Activity?,
        bean: WebViewConfigBean,
        requestCode: Int
    ) {
        val intent = Intent(context, WebViewActivity::class.java)
            .putExtra(WebViewConstants.LIBRARY_WEB_VIEW, bean)
        context?.startActivityForResult(intent, requestCode)
    }

    override fun startActivityForResult(
        context: Fragment?,
        bean: WebViewConfigBean,
        requestCode: Int
    ) {
        if (context == null || context.context == null) return
        val intent = Intent(context.context, WebViewActivity::class.java)
            .putExtra(WebViewConstants.LIBRARY_WEB_VIEW, bean)
        context.startActivityForResult(intent, requestCode)
    }


    override fun startActivityForResult(
        context: FragmentActivity?,
        launcher: ActivityResultLauncher<Intent>?,
        bean: WebViewConfigBean
    ) {
        val intent = Intent(context, WebViewActivity::class.java)
            .putExtra(WebViewConstants.LIBRARY_WEB_VIEW, bean)
        launcher?.launch(intent)
    }

    override fun startActivityForResult(
        context: Fragment?,
        launcher: ActivityResultLauncher<Intent>?,
        bean: WebViewConfigBean
    ) {
        if (context == null || context.context == null) return
        val intent = Intent(context.context, WebViewActivity::class.java)
            .putExtra(WebViewConstants.LIBRARY_WEB_VIEW, bean)
        launcher?.launch(intent)
    }
}