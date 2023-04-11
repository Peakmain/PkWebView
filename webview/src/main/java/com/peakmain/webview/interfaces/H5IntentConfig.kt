package com.peakmain.webview.interfaces

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.peakmain.webview.bean.ActivityResultBean
import com.peakmain.webview.helper.PkStartActivityResultContracts

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
interface H5IntentConfig {
    fun startActivity(context: Context?, url: String)
    fun startActivityForResult(context: Activity?, url: String, requestCode: Int)
    fun startActivityForResult(context: Fragment?, url: String, requestCode: Int)
    fun startActivityForResult(
        context: FragmentActivity?,
        launcher: ActivityResultLauncher<Intent>?,
        url: String
    )
    fun startActivityForResult(
        context: Fragment?,
        launcher: ActivityResultLauncher<Intent>?,
        url: String
    )
}