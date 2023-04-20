package com.peakmain.pkwebview

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleRegistry
import com.peakmain.pkwebview.intent.ReplaceH5ConfigDecorator
import com.peakmain.pkwebview.intent.ReplaceH5IntentConfigImpl
import com.peakmain.webview.H5Utils
import com.peakmain.webview.helper.PkStartActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val launcher = registerForActivityResult(StartActivityForResult()) {
            Log.e("TAG","收到结果:${it.resultCode}")
        }
        findViewById<TextView>(R.id.tv_webview).setOnClickListener {
            /*H5Utils()
                .startActivity(this, "https://qa-xbu-activity.at-our.com/mallIndex")*/
            H5Utils()
                .startActivityForResult(
                    this, launcher,
                    "https://qa-xbu-activity.at-our.com/mallIndex")
        }
    }

}