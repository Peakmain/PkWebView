package com.peakmain.pkwebview.base

import android.app.Instrumentation
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.Before

/**
 * author ：Peakmain
 * createTime：2023/04/20
 * mail:2726449200@qq.com
 * describe：
 */
open class BaseTest {
    lateinit var mInstrument: Instrumentation
   lateinit var mDevice: UiDevice
    @Before
    fun setUp(){
        mInstrument= InstrumentationRegistry.getInstrumentation()
        mDevice= UiDevice.getInstance(mInstrument)
    }
}