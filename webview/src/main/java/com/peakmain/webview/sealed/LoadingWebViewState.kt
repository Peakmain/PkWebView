package com.peakmain.webview.sealed

/**
 * author ：Peakmain
 * createTime：2023/04/20
 * mail:2726449200@qq.com
 * describe：
 */
sealed class LoadingWebViewState {
    object NotLoading : LoadingWebViewState()
    object HorizontalProgressBarLoadingStyle : LoadingWebViewState()
    object ProgressBarLoadingStyle : LoadingWebViewState()
    object CustomLoadingStyle : LoadingWebViewState()
}