package com.peakmain.webview.bean.cache;

import static java.net.HttpURLConnection.HTTP_BAD_METHOD;
import static java.net.HttpURLConnection.HTTP_GONE;
import static java.net.HttpURLConnection.HTTP_MOVED_PERM;
import static java.net.HttpURLConnection.HTTP_MULT_CHOICE;
import static java.net.HttpURLConnection.HTTP_NOT_AUTHORITATIVE;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NOT_IMPLEMENTED;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_REQ_TOO_LONG;

import java.util.Map;

import okhttp3.internal.http.StatusLine;

/**
 * author ：Peakmain
 * createTime：2024/3/4
 * mail:2726449200@qq.com
 * describe：
 */
public class WebResource {
    // 资源返回值
    private int responseCode;

    // 描述状态代码的短语，例如“OK”
    private String message;

    // 响应资源的Header集合
    private Map<String, String> responseHeaders;

    // 是否新变更
    private boolean isModified = true;

    private boolean isCacheByOurseleves = false;

    private byte[] originBytes;


    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public boolean isCacheByOurseleves() {
        return isCacheByOurseleves;
    }

    public void setCacheByOurseleves(boolean cacheByOurseleves) {
        isCacheByOurseleves = cacheByOurseleves;
    }

    public byte[] getOriginBytes() {
        return originBytes;
    }

    public void setOriginBytes(byte[] originBytes) {
        this.originBytes = originBytes;
    }


    /**
     * 参考
     * Okhttp3.internal.cache.CacheStrategy
     * @return boolean
     */
    public boolean isCacheable() {
        return responseCode == HTTP_OK
                || responseCode == HTTP_NOT_AUTHORITATIVE
                || responseCode == HTTP_NO_CONTENT
                || responseCode == HTTP_MULT_CHOICE
                || responseCode == HTTP_MOVED_PERM
                || responseCode == HTTP_NOT_FOUND
                || responseCode == HTTP_BAD_METHOD
                || responseCode == HTTP_GONE
                || responseCode == HTTP_REQ_TOO_LONG
                || responseCode == HTTP_NOT_IMPLEMENTED
                || responseCode == StatusLine.HTTP_PERM_REDIRECT;
    }
}
