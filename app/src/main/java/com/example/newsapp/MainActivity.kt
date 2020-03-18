package com.example.newsapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.Adapter.NewsAdapter
import com.example.newsapp.EndPointModels.TopHeadLines
import com.example.newsapp.Model.Article
import com.example.newsapp.Services.NewsService
import com.example.newsapp.Services.ServiceBuilder
import com.example.newsapp.Utils.log
import com.example.newsapp.Utils.showtoast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    NewsAdapter.onNewsClick {
    lateinit var newsService: NewsService

    lateinit var newsAdapter: NewsAdapter
    lateinit var articleList: ArrayList<Article>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        articleList = ArrayList<Article>()
        newsAdapter = NewsAdapter(articleList, this, this)
        recylerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recylerView.adapter = newsAdapter
        recylerView.itemAnimator = DefaultItemAnimator()



        swipe_refresh.setOnRefreshListener(this)
        swipe_refresh.setColorSchemeResources(R.color.colorAccent)

        onLoadingSwipeRefresh("")


    }

    override fun onNewsClick(position: Int) {
        var intent: Intent = Intent(this, DetailActivity::class.java)
        var bundle = Bundle()
        bundle.putString("author", articleList.get(position).author)
        bundle.putString("title", articleList.get(position).title)
        bundle.putString("description", articleList.get(position).description)
        bundle.putString("url", articleList.get(position).url)
        bundle.putString("urlToImage", articleList.get(position).urlToImage)
        bundle.putString("publishedAt", articleList.get(position).publishedAt)
        intent.putExtra("news", bundle)
        startActivity(intent)


        startActivity(intent)
    }


    fun loadNews(keyword: String) {
        articleList.clear()
        swipe_refresh.isRefreshing = true

        newsService = ServiceBuilder.buildService(NewsService::class.java)

        var getHeadLines: Call<TopHeadLines>
        if (keyword.length > 2) {
            val queryMap = HashMap<String, String>()
            queryMap.put("q", keyword)
            queryMap.put("pagesize", "2")
            queryMap.put("sortBy", "publishedAt")
            queryMap.put("apiKey", resources.getString(R.string.news_api_key))
            getHeadLines = newsService.getNewsSearch(queryMap)
        } else {
            val queryMap = HashMap<String, String>()
            queryMap.put("country", "us")
            queryMap.put("apiKey", resources.getString(R.string.news_api_key))
            getHeadLines = newsService.getHeadLines(queryMap)
        }
        getHeadLines.enqueue(object : Callback<TopHeadLines> {

            override fun onResponse(call: Call<TopHeadLines>, response: Response<TopHeadLines>) {
                if (response.isSuccessful)
                    response.body().let { topHeadLines ->
                        topHeadLines?.articles?.let { articleList.addAll(it) }
                        swipe_refresh.isRefreshing = false

                        newsAdapter.notifyDataSetChanged()
                    }
            }

            override fun onFailure(call: Call<TopHeadLines>, t: Throwable) {
                log(t.message.toString())
                this@MainActivity.showtoast(t.message.toString())
                swipe_refresh.isRefreshing = false
                top_headline_textview.visibility = View.INVISIBLE

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        var searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchView: SearchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        var searchMenuItem: MenuItem = menu.findItem(R.id.action_search)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Search News"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    onLoadingSwipeRefresh(it)
                }


                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
//                    if (newText.length > 2) {
////                        onLoadingSwipeRefresh(newText)
////                    }


                }
                return true

            }
        })


        searchMenuItem.icon.setVisible(false, false)

        return true
    }

    override fun onRefresh() {

        loadNews("")

    }

    fun onLoadingSwipeRefresh(keyword: String) {
        swipe_refresh.post {
            loadNews(keyword)
        }
    }


}
