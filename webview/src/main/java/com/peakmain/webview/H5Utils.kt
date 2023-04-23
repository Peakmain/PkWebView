package com.peakmain.webview

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.peakmain.webview.abstracts.AbstractH5IntentConfigDecorator
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.implement.DefaultH5IntentConfigImpl
import com.peakmain.webview.interfaces.H5IntentConfig

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
class H5Utils(decoratorConfig: H5IntentConfig = DefaultH5IntentConfigImpl()) :
    AbstractH5IntentConfigDecorator(decoratorConfig) {
    fun isShowToolBar(showTitle: Boolean): H5Utils {
        params.isShowToolBar = showTitle
        return this
    }

    fun updateStatusBar(updateStatusBar: ((String, WebViewActivity?) -> Unit)? = null): H5Utils {
        params.updateStatusBar = updateStatusBar
        return this
    }

    fun updateToolBar(updateToolBarBar: ((String, WebViewActivity?) -> Unit)? = null): H5Utils {
        params.updateToolBarBar = updateToolBarBar
        return this
    }
}