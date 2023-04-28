package com.peakmain.pkwebview

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.pkwebview.bean.NewHybridModel
import com.peakmain.webview.H5Utils
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.bean.WebViewModel
import com.peakmain.webview.sealed.StatusBarState
import com.peakmain.webview.utils.EncodeUtils
import com.peakmain.webview.utils.GsonUtils
import com.peakmain.webview.utils.LogWebViewUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val launcher = registerForActivityResult(StartActivityForResult()) {
            Log.e("TAG", "收到结果:${it.resultCode}")
        }
        findViewById<TextView>(R.id.tv_webview).setOnClickListener {
            H5Utils()
                .isShowToolBar(false)
                //.setLoadingWebViewState(LoadingWebViewState.ProgressBarLoadingStyle)
                .updateStatusBar { title, activity ->
                    if (title.contains("商城")) {
                        activity?.updateStateBar(StatusBarState.NoStatusModeState)
                    } else {
                        activity?.updateStateBar()
                    }
                }
                .updateToolBar { title, activity ->
                    if (title.contains("商城")) {
                        activity?.showToolbar(false)
                    } else {
                        activity?.showToolbar(true)
                        /*    activity?.setOnClickListener({
                                activity.finish()
                            }) {
                                Toast.makeText(it.context, "点击右边", Toast.LENGTH_LONG).show()
                            }*/
                        activity?.setToolbarStyle { toolbar, ivLeft, tvTitle, tvRight ->
                            //toolbar?.setBackgroundColor(Color.TRANSPARENT)
                            /*  ivLeft?.setImageResource()
                              tvTitle?.apply {
                                  setTextColor(Color.RED)
                              }
                              tvRight?.visibility = View.VISIBLE*/
                        }
                    }
                }
                .handleUrlParams { url, event, path ->
                    val params = url?.getQueryParameter("param")
                    if (!TextUtils.isEmpty(params)) {
                        val decodeParam: String =
                            EncodeUtils.decode(params!!.replace(" ", "+"))
                        LogWebViewUtils.e("PkWebView decodeParam:$decodeParam")
                        if (TextUtils.equals("/jumpToWhere", path)) {
                            val newHybridModel: NewHybridModel = GsonUtils.fromJson(
                                decodeParam,
                                NewHybridModel::class.java
                            )
                            event.newHybridModel=newHybridModel
                        } else {
                            val webViewModel: WebViewModel =
                                GsonUtils.fromJson(decodeParam, WebViewModel::class.java)
                            event.webViewModel = webViewModel
                        }
                    }
                }
                .startActivityForResult(
                    this, launcher,
                    WebViewConfigBean(
                        BuildConfig.config.url
                    )
                )
        }
    }

}