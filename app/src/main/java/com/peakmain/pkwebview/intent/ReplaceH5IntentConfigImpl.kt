package com.peakmain.pkwebview.intent

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.peakmain.webview.bean.ActivityResultBean
import com.peakmain.webview.interfaces.H5IntentConfig

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
class ReplaceH5IntentConfigImpl:H5IntentConfig {
    override fun startActivity(context: Context?, url: String) {
      Log.e("TAG","ReplaceH5IntentConfigImpl url:$url")
    }

    override fun startActivityForResult(context: Activity?, url: String, requestCode: Int) {
    }

    override fun startActivityForResult(
        context: Fragment?,
        url: String,
        requestCode: Int,
        block: ((ActivityResultBean) -> Unit)?
    ) {
    }

}