package com.peakmain.pkwebview

import android.app.Application
import android.content.Context
import com.peakmain.pkwebview.handle.OnlineServiceHandle
import com.peakmain.pkwebview.handle.PageActionHandle
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
            //.setLoadingView(ReplaceLoadingConfigImpl())
            .setLoadingWebViewState(LoadingWebViewState.HorizontalProgressBarLoadingStyle)
            .registerEntities(OnlineServiceHandle::class.java,PageActionHandle::class.java)
            .setUserAgent(BuildConfig.config.userAgent)
            .build()
        super.onCreate()
    }


}