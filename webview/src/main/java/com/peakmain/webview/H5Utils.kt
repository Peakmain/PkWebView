package com.peakmain.webview

import com.peakmain.webview.abstracts.AbstractH5IntentConfigDecorator
import com.peakmain.webview.implement.DefaultH5IntentConfigImpl
import com.peakmain.webview.interfaces.H5IntentConfig

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
class H5Utils(decoratorConfig: H5IntentConfig = DefaultH5IntentConfigImpl()) :
    AbstractH5IntentConfigDecorator(decoratorConfig)