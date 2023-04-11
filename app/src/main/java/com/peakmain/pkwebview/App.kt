package com.peakmain.pkwebview

import android.app.Application
import com.peakmain.webview.PkWebViewInit

/**
 * author ：Peakmain
 * createTime：2023/04/04
 * mail:2726449200@qq.com
 * describe：
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        PkWebViewInit.Builder(this)
            .build()
    }
}