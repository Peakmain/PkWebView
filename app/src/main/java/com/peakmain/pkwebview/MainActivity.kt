package com.peakmain.pkwebview

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import com.peakmain.webview.H5Utils
import com.peakmain.webview.bean.WebViewConfigBean
import com.peakmain.webview.sealed.LoadingWebViewState
import com.peakmain.webview.sealed.StatusBarState

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
                .startActivityForResult(
                    this, launcher,
                    WebViewConfigBean(
                        "http://xbu-activity.yaduo.com/mallIndex"
                    )
                )
        }
    }

}