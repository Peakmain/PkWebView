package com.peakmain.pkwebview

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.peakmain.pkwebview.base.BaseTest
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


/**
 * author ：Peakmain
 * createTime：2023/04/20
 * mail:2726449200@qq.com
 * describe：
 */
@RunWith(JUnit4::class)
class Launch : BaseTest() {
    @Test
    fun startApp() {
        val targetContext = mInstrument.targetContext
        val intent:Intent? =
            targetContext.packageManager.getLaunchIntentForPackage(targetContext.packageName)
        intent?.apply {
            targetContext.startActivity(this)
        }
    }
}