package com.app.grofiesta.ui.main.view

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import com.app.grofiesta.R
import com.app.grofiesta.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.app_header_layout.*

class WebViewActivity : BaseActivity() {

    var webUrl = ""
    var webTitle = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webUrl = intent.getStringExtra("webUrl")!!
        webTitle = intent.getStringExtra("webTitle")!!
        if (webTitle == "About Us")
            setContentView(R.layout.activity_web_view_about_us)
        else
            setContentView(R.layout.activity_web_view)


        imgBack.setOnClickListener { finish() }


        txtPageTitle.text = webTitle

        try {
            webview.settings.javaScriptEnabled = true
            webview.settings.domStorageEnabled = true // Add this
            webview.settings.javaScriptCanOpenWindowsAutomatically = true
            webview.webViewClient = MyWebViewClient()
            webview.isClickable = true
            webview.loadUrl(webUrl)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            webView: WebView,
            url: String
        ): Boolean {
            return if (url.startsWith("http:") || url.startsWith("https:")) {
                false
            } else {
                if (url.startsWith("intent://")) {
                    try {
                        val context = webView.context
                        val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        if (intent != null) {
                            val packageManager = context.packageManager
                            val info = packageManager.resolveActivity(
                                intent,
                                PackageManager.MATCH_DEFAULT_ONLY
                            )
                            // This IF statement can be omitted if you are not strict about
                            // opening the Google form url in WebView & can be opened in an
                            // External Browser
                            if (intent != null && (intent.scheme == "https"
                                        || intent.scheme == "http")
                            ) {
                                val fallbackUrl = intent.getStringExtra(
                                    "browser_fallback_url"
                                )
                                webView.loadUrl(fallbackUrl!!)
                                return true
                            }
                            if (info != null) {
                                context.startActivity(intent)
                            } else {
                                // Call external broswer
                                val fallbackUrl = intent.getStringExtra(
                                    "browser_fallback_url"
                                )
                                val browserIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(fallbackUrl)
                                )
                                context.startActivity(browserIntent)
                            }
                            true
                        } else {
                            false
                        }
                    } catch (e: java.lang.Exception) {
                        false
                    }
                } else {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    webView.context.startActivity(intent)
                    true
                }
            }
        }

//        override fun onPageStarted(
//            view: WebView,
//            url: String,
//            favicon: Bitmap
//        ) {
//            super.onPageStarted(view, url, favicon)
////            this.progressBar.setVisibility(View.VISIBLE)
//        }
//
//        override fun onPageFinished(view: WebView, url: String) {
//            super.onPageFinished(view, url)
////            progressBar.setVisibility(View.GONE)
//        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && this.webview.canGoBack()) {
            this.webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        webview.destroy()
        finish()
    }
}
