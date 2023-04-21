package com.peakmain.pkwebview.intent

import android.content.Context
import com.peakmain.webview.abstracts.AbstractH5IntentConfigDecorator
import com.peakmain.webview.bean.WebViewConfigBean

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
class ReplaceH5ConfigDecorator : AbstractH5IntentConfigDecorator(ReplaceH5IntentConfigImpl()) {
    override fun startActivity(context: Context?, bean: WebViewConfigBean) {
        var replaceUrl = bean.url
        replaceUrl += "/token?=111"
        bean.url = replaceUrl
        super.startActivity(context, bean)
    }
}