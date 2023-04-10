package com.peakmain.pkwebview.intent

import android.content.Context
import com.peakmain.webview.abstracts.AbstractH5IntentConfigDecorator
import com.peakmain.webview.implement.DefaultH5IntentConfigImpl

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
class ReplaceH5ConfigDecorator:AbstractH5IntentConfigDecorator(ReplaceH5IntentConfigImpl()){
    override fun startActivity(context: Context?, url: String) {
        var replaceUrl=url
        replaceUrl+="/token?=111"
        super.startActivity(context, replaceUrl)
    }
}