package com.peakmain.webview.interfaces

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.peakmain.webview.bean.ActivityResultBean

/**
 * author ：Peakmain
 * createTime：2023/04/10
 * mail:2726449200@qq.com
 * describe：
 */
interface H5IntentConfig {
    fun startActivity(context: Context?, url:String)
    fun startActivityForResult(context: Activity?, url:String,requestCode:Int)
    fun startActivityForResult(context: Fragment?, url:String,requestCode:Int,block: ((ActivityResultBean) -> Unit)? =null)
}