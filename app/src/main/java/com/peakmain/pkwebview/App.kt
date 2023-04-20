package com.peakmain.pkwebview

import android.app.Application
import com.peakmain.webview.PkWebViewInit
import com.peakmain.webview.sealed.LoadingWebViewState

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
            .setLoadingWebViewState(LoadingWebViewState.HorizontalProgressBarLoadingStyle)
            .build()
    }
}