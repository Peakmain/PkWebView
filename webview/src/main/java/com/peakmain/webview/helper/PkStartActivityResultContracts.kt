package com.peakmain.webview.helper

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.peakmain.webview.bean.ActivityResultBean

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
class PkStartActivityResultContracts(private val requestCode:Int): ActivityResultContract<Intent, ActivityResultBean>() {
    override fun createIntent(context: Context, input: Intent?): Intent {
        return input!!
    }

    override fun parseResult(resultCode: Int, intent: Intent?): ActivityResultBean {
        return ActivityResultBean(resultCode,requestCode, intent)
    }
}