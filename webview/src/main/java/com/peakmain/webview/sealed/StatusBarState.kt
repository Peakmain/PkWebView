package com.peakmain.webview.sealed

import android.os.Parcel
import android.os.Parcelable

/**
 * author ：Peakmain
 * createTime：2023/04/21
 * mail:2726449200@qq.com
 * describe：
 */
sealed class StatusBarState : Parcelable {
    /**
     * 黑色的字体
     */
    object LightModeState : StatusBarState()
    /**
     * 白色的字体
     */

    object DartModeState : StatusBarState()
    /**
     * 自定义状态栏颜色
     */
    object StatusColorState : StatusBarState()
    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<StatusBarState> = object : Parcelable.Creator<StatusBarState> {
            override fun createFromParcel(parcel: Parcel): StatusBarState {
                return when (parcel.readInt()) {
                    0 -> LightModeState
                    1 -> DartModeState
                    2 -> StatusColorState
                    else -> throw IllegalArgumentException("Invalid parcel data")
                }
            }

            override fun newArray(size: Int): Array<StatusBarState?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(when (this) {
            is LightModeState -> 0
            is DartModeState -> 1
            is StatusColorState -> 2
        })
    }
}
