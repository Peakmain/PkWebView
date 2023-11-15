package com.peakmain.webview

import android.view.View
import androidx.annotation.LayoutRes
import com.peakmain.webview.abstracts.AbstractH5IntentConfigDecorator
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.annotation.CacheMode
import com.peakmain.webview.annotation.CacheModeState
import com.peakmain.webview.bean.WebViewEvent
import com.peakmain.webview.callback.HandleUrlParamsCallback
import com.peakmain.webview.implement.DefaultH5IntentConfigImpl
import com.peakmain.webview.interfaces.H5IntentConfig
import com.peakmain.webview.interfaces.LoadingViewConfig
import com.peakmain.webview.sealed.LoadingWebViewState

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

    fun setLoadingView(loadingViewConfig: LoadingViewConfig): H5Utils {
        params.mLoadingViewConfig = loadingViewConfig
        params.mLoadingWebViewState = LoadingWebViewState.CustomLoadingStyle
        return this
    }

    fun setLoadingWebViewState(loadingWebViewState: LoadingWebViewState): H5Utils {
        params.mLoadingWebViewState = loadingWebViewState
        return this
    }

    fun setWebViewCacheMode(@CacheModeState cacheMode: Int = CacheMode.LOAD_NO_CACHE): H5Utils {
        params.mCacheMode = cacheMode
        return this
    }

    fun setHandleUrlParamsCallback(
        handleUrlParamsCallback:
        HandleUrlParamsCallback<out WebViewEvent>,
    ): H5Utils {
        params.mHandleUrlParamsCallback = handleUrlParamsCallback
        return this
    }

    fun setHeadContentView(view: View?, block: ((View) -> Unit)? = null): H5Utils {
        params.mHeadContentView = view
        params.mHeadContentViewId = 0
        params.mHeadViewBlock = block
        return this
    }

    fun setHeadContentView(@LayoutRes viewIdRes: Int, block: ((View) -> Unit)? = null): H5Utils {
        params.mHeadContentView = null
        params.mHeadContentViewId = viewIdRes
        params.mHeadViewBlock = block
        return this
    }
}