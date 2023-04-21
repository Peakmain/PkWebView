package com.peakmain.webview.abstracts

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.peakmain.webview.bean.ActivityResultBean
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.interfaces.H5IntentConfig

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：H5启动装饰器类
 */
abstract class AbstractH5IntentConfigDecorator(private val decoratorConfig: H5IntentConfig) :
    H5IntentConfig {
    override fun startActivity(context: Context?, bean: WebViewConfigBean) {
        decoratorConfig.startActivity(context, bean)
    }

    override fun startActivityForResult(
        context: Activity?,
        bean: WebViewConfigBean,
        requestCode: Int
    ) {
        decoratorConfig.startActivityForResult(context, bean, requestCode)
    }

    override fun startActivityForResult(
        context: Fragment?,
        bean: WebViewConfigBean,
        requestCode: Int
    ) {
        decoratorConfig.startActivityForResult(context, bean, requestCode)
    }

    override fun startActivityForResult(
        context: Fragment?,
        launcher: ActivityResultLauncher<Intent>?,
        bean: WebViewConfigBean
    ) {
        decoratorConfig.startActivityForResult(context, launcher, bean)
    }

    override fun startActivityForResult(
        context: FragmentActivity?,
        launcher: ActivityResultLauncher<Intent>?,
        bean: WebViewConfigBean
    ) {
        decoratorConfig.startActivityForResult(context, launcher, bean)
    }
}