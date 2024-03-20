package com.peakmain.pkwebview

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.pkwebview.bean.HotelParams
import com.peakmain.pkwebview.bean.WebViewModel
import com.peakmain.pkwebview.implements.HandlerUrlParamsImpl
import com.peakmain.webview.H5Utils
import com.peakmain.webview.activity.WebViewActivity
import com.peakmain.webview.annotation.CacheMode
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.manager.cache.CacheConfig
import com.peakmain.webview.manager.cache.CacheWebViewManager
import com.peakmain.webview.sealed.StatusBarState
import com.peakmain.webview.utils.GsonUtils
import com.peakmain.webview.utils.WebViewUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val launcher = registerForActivityResult(StartActivityForResult()) {
            Log.e("TAG", "收到结果:${it.resultCode}")
        }

        CacheWebViewManager.instance.setCacheConfig(
            CacheConfig.Builder(this)
                .setCacheDir("").build()
        )
        CacheWebViewManager.instance.preLoadUrl(this, BuildConfig.config.url)
        findViewById<TextView>(R.id.tv_normal_webview).setOnClickListener {
            val intent = Intent(this@MainActivity, NormalWebActivity::class.java)
            intent.putExtra("url", BuildConfig.config.url)
            startActivity(intent)
        }
        findViewById<TextView>(R.id.tv_webview).setOnClickListener {
            H5Utils()
                .isShowToolBar(false)
                .setWebViewCacheMode(CacheMode.LOAD_DEFAULT)
                //.setLoadingWebViewState(LoadingWebViewState.ProgressBarLoadingStyle)
                .updateStatusBar { title, activity ->
                    if (title.contains("商城")) {
                        (activity as WebViewActivity?)?.updateStateBar(StatusBarState.NoStatusModeState)
                    } else {
                        (activity as WebViewActivity?)?.updateStateBar()
                    }
                }
                .updateToolBar { title, activity ->
                    if (title.contains("商城")) {
                        (activity as WebViewActivity?)?.showToolbar(false)
                        activity?.showHeadView(true)
                    } else {
                        (activity as WebViewActivity?)?.showToolbar(true)
                        activity?.showHeadView(false)
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
                //第一次执行
                /*     .executeJs("onCallbackDone", webViewModel) { webView, fragment ->
                         //第二次执行执行js
                     }*/
                /*  .setHeadContentView(R.layout.hotel_list_head) {

                  }*/
                //.setHandleUrlParamsCallback(HandlerUrlParamsImpl())
                .startActivityForResult(
                    this, launcher,
                    WebViewConfigBean(
                       BuildConfig.config.url
                    )
                )
        }
    }


    private fun getViewModel(): String {
        val hashMap = HashMap<String, String>()
        hashMap["hotelParams"] = GsonUtils.toJson(HotelParams())
        return GsonUtils.toJson(WebViewModel(1, hashMap, "hotel/params"))
    }

}