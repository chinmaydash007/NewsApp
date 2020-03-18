package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util
import com.example.newsapp.Model.Article
import com.example.newsapp.Utils.DateFormat
import com.example.newsapp.Utils.DateToTimeFormat
import com.example.newsapp.Utils.log
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.math.floor
import kotlin.math.log

class DetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    var isHideToolbarView = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        toolbar.let {
            setSupportActionBar(toolbar)
        }
        supportActionBar?.setTitle("")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appbar.addOnOffsetChangedListener(this)

        var bundle = intent.getBundleExtra("news")


        bundle.let {
            title_on_appbar.text = bundle.getString("title")
            subtitle_on_appbar.text = bundle.getString("description")
            titlee.text = bundle.getString("title")
            time.text = DateToTimeFormat(bundle.getString("publishedAt"))
            date.text = DateFormat(bundle.getString("publishedAt"))
        }


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
        val maxScroll: Int? = appbar?.totalScrollRange
        maxScroll.let {

            var percentage = Math.abs(verticalOffset.toFloat() / maxScroll?.toFloat()!!) as Float
            log(percentage.toString())
            if (percentage == 1.0f && isHideToolbarView) {
                log("Hello")
                date_behavior.visibility = View.GONE
                title_appbar.visibility = View.VISIBLE
                isHideToolbarView = !isHideToolbarView

            } else if (percentage < 1f && !isHideToolbarView) {
                log("Bye")
                date_behavior.visibility = View.VISIBLE
                title_appbar.visibility = View.GONE
                isHideToolbarView = !isHideToolbarView
            }

        }
    }
}
