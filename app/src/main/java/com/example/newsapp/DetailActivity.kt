package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.math.floor

class DetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    var isHideToolbarView = false
    lateinit var mUrl: String
    lateinit var mImg: String
    lateinit var mTitle: String
    lateinit var mDate: String
    lateinit var mSource: String
    lateinit var mAuthor: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        toolbar.let {
            setSupportActionBar(toolbar)
        }
        supportActionBar?.setTitle("")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appbar.addOnOffsetChangedListener(this)


    }

    fun initWebView(url: String) {
        webView.let {

            it.settings.loadsImagesAutomatically = true
            it.settings.javaScriptEnabled = true
            it.settings.domStorageEnabled = true
            it.settings.setSupportZoom(true)
            it.settings.builtInZoomControls = true
            it.settings.displayZoomControls = true
            it.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
            it.webViewClient = WebViewClient()
            it.loadUrl(url)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true;
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        var maxScroll: Int? = appbar?.totalScrollRange
        var percentage = Math.abs(verticalOffset) / (maxScroll as Float)
        if (percentage == 1f && isHideToolbarView) {
            date_behavior.visibility = View.GONE
            title_appbar.visibility = View.VISIBLE
            isHideToolbarView = !isHideToolbarView

        } else if (percentage < 1f && isHideToolbarView) {
            date_behavior.visibility = View.VISIBLE
            title_appbar.visibility = View.VISIBLE
            isHideToolbarView = !isHideToolbarView
        }
    }
}
