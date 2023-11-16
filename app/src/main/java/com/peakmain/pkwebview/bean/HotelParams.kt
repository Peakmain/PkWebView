package com.peakmain.pkwebview.bean

/**
 * author ：Peakmain
 * createTime：2023/11/15
 * mail:2726449200@qq.com
 * describe：
 */
data class HotelParams(
    var searchWord: String?="",
    var startDate: String?="2023-11-15",
    var endDate: String?="2023-11-16",
    var poiId: String="",
    var locationCityName: String="上海市",
    var locationLongitude: String="0.0",
    var locationLatitude: String="0.0",
    var locationType: Int = 1,
    var brandList: ArrayList<Int> = ArrayList(),
    var tagCodeList: ArrayList<String> = ArrayList(),
    var order: Int = 0,
    var longitude: String = "",
    var latitude: String = "",
    var cityName: String=""
)