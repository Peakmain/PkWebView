package com.peakmain.webview.bean

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import com.peakmain.webview.sealed.StatusBarState

/**
 * author ：Peakmain
 * createTime：2023/4/1
 * mail:2726449200@qq.com
 * describe：
 */
/**
 * @param url:跳转的url链接
 * @param statusBarColor:状态栏的颜色，一定要设置statusBarState=StatusBarState.StatusColorState才会生效
 * @param statusBarState:设置状态栏状态
 */

data class WebViewConfigBean(
    var url: String? = null,
    var statusBarState: StatusBarState = StatusBarState.LightModeState,
    var statusBarColor: Int = Color.WHITE
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(StatusBarState::class.java.classLoader)
            ?: StatusBarState.LightModeState,
        parcel.readInt(),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeParcelable(statusBarState, flags)
        parcel.writeInt(statusBarColor)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WebViewConfigBean> {
        override fun createFromParcel(parcel: Parcel): WebViewConfigBean {
            return WebViewConfigBean(parcel)
        }

        override fun newArray(size: Int): Array<WebViewConfigBean?> {
            return arrayOfNulls(size)
        }
    }
}
