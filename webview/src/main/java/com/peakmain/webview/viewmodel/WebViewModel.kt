package com.peakmain.webview.viewmodel

import android.app.Activity
import android.view.View
import androidx.lifecycle.ViewModel
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.sealed.StatusBarState
import com.peakmain.webview.utils.StatusBarUtils

/**
 * author ：Peakmain
 * createTime：2023/04/21
 * mail:2726449200@qq.com
 * describe：
 */
class WebViewModel : ViewModel() {
    fun initStatusBar(activity: Activity, webViewConfigBean: WebViewConfigBean?) {
        if (webViewConfigBean == null) return
        when (webViewConfigBean.statusBarState) {
            is StatusBarState.LightModeState -> {
                StatusBarUtils.setColor(
                    activity,
                    webViewConfigBean.statusBarColor,
                    0
                )
                StatusBarUtils.setLightMode(activity)
            }
            is StatusBarState.DartModeState -> {
                StatusBarUtils.setColor(
                    activity,
                    webViewConfigBean.statusBarColor,
                    0
                )
                StatusBarUtils.setDarkMode(activity)
            }
            is StatusBarState.StatusColorState -> {
                StatusBarUtils.setColor(
                    activity,
                    webViewConfigBean.statusBarColor,
                    0
                )
            }
            is StatusBarState.NoStatusModeState -> {
                StatusBarUtils.hideStatusBar(activity)
            }
        }
    }
}