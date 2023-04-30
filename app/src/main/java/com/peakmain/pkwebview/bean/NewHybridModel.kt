package com.peakmain.pkwebview.bean

/**
 * author ：Peakmain
 * createTime：2023/4/28
 * mail:2726449200@qq.com
 * describe：
 */
data class NewHybridModel(val callId:String,val data:DataModel)
data class DataModel(val page:String,val data:DataSubModel)
data class DataSubModel(val url:String)