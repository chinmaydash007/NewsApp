package com.example.newsapp.Adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.newsapp.Model.Article
import com.example.newsapp.R
import com.example.newsapp.Utils.DateFormat
import com.example.newsapp.Utils.DateToTimeFormat
import com.example.newsapp.Utils.getRandomDrawbleColor
import kotlinx.android.synthetic.main.single_news_card.view.*

class NewsAdapter(var articleList: List<Article>, var context: Context,var onNewClick: onNewsClick) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        var view: View =
            LayoutInflater.from(context).inflate(R.layout.single_news_card, parent, false)
        return NewsViewHolder(view,onNewClick)

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var article = articleList.get(position)
        holder.title.text = article.title
        holder.author.text = article.author
        holder.desc.text = article.description
        holder.source.text = article.source?.name
        holder.author.text = article.author
        holder.time.text = "\u2022 ${DateToTimeFormat(article.publishedAt)}"
        holder.publishedAt.text = DateFormat(article.publishedAt)

        var requestOptions: RequestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(getRandomDrawbleColor())
            .error(getRandomDrawbleColor())
            .centerCrop()

        Glide.with(context).load(article.urlToImage).apply(requestOptions)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

            })
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)


    }

    inner class NewsViewHolder(itemView: View,onPersonClick: onNewsClick) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var title = itemView.title
        var desc = itemView.desc
        var author = itemView.author
        var publishedAt = itemView.publishedAt
        var source = itemView.source
        var time = itemView.time
        var imageView = itemView.img
        var progressBar = itemView.progress_load_photo
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            onNewClick.onNewsClick(adapterPosition)
        }


    }
    interface onNewsClick{
        fun onNewsClick(position: Int)
    }
}