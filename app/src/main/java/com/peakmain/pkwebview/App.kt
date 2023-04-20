package com.peakmain.pkwebview

import android.app.Application
import com.peakmain.pkwebview.loading.ReplaceLoadingConfigImpl
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
        PkWebViewInit.Builder(this)
            .setLoadingView(ReplaceLoadingConfigImpl())
            .setLoadingWebViewState(LoadingWebViewState.HorizontalProgressBarLoadingStyle)
            .build()
        super.onCreate()
    }
}