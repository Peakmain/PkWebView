package com.peakmain.webview.bean.cache;


import com.peakmain.webview.utils.WebViewUtils;

import java.util.Locale;
import java.util.Map;

/**
 * author ：Peakmain
 * createTime：2024/3/4
 * mail:2726449200@qq.com
 * describe：
 */
public class CacheRequest {

    // 缓存唯一识别 将请求链接转为MD5
    private String key;

    private String url;

    // 资源类型
    private String mimeType;

    // 设置是否缓存模式
    private boolean cacheMode;

    private int mWebViewCacheMode;

    private Map<String, String> mHeaders;

    private String mUserAgent;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        // md5 进入DiskLruCache 缓存时必须为小写字母
        this.key = WebViewUtils.getInstance().getMd5(url,true).toLowerCase(Locale.ROOT);
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(boolean cacheMode) {
        this.cacheMode = cacheMode;
    }

    public int getWebViewCacheMode() {
        return mWebViewCacheMode;
    }

    public void setWebViewCacheMode(int webViewCacheMode) {
        mWebViewCacheMode = webViewCacheMode;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public String getUserAgent() {
        return mUserAgent;
    }

    public void setUserAgent(String userAgent) {
        mUserAgent = userAgent;
    }

}
