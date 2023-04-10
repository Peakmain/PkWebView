package com.peakmain.webview.abstracts

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.peakmain.webview.bean.ActivityResultBean
import com.peakmain.webview.interfaces.H5IntentConfig

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：H5启动装饰器类
 */
abstract class AbstractH5IntentConfigDecorator(private val decoratorConfig: H5IntentConfig) :
    H5IntentConfig {
    override fun startActivity(context: Context?, url: String) {
        decoratorConfig.startActivity(context, url)
    }

    override fun startActivityForResult(context: Activity?, url: String, requestCode: Int) {
        decoratorConfig.startActivityForResult(context, url, requestCode)
    }

    override fun startActivityForResult(
        context: Fragment?, url: String, requestCode: Int,
        block: ((ActivityResultBean) -> Unit)?
    ) {
        decoratorConfig.startActivityForResult(context, url, requestCode, block)
    }
}