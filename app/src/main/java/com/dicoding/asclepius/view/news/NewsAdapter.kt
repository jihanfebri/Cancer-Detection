package com.dicoding.asclepius.view.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.NewsItemBinding
import com.dicoding.asclepius.util.DiffUtil.NewsDiffCallback
import com.dicoding.asclepius.util.transformUtcTimestamp

class NewsAdapter(var articles: List<ArticlesItem>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun updateArticles(newArticles: List<ArticlesItem>) {
        val diffResult = DiffUtil.calculateDiff(NewsDiffCallback(this.articles, newArticles))
        this.articles = newArticles
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    class ViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(item.urlToImage)
                    .into(ivImage)
                tvNewstitle.text = item.title
                binding.tvPublished.text = item.publishedAt?.let { transformUtcTimestamp(it) } ?: "-"
                tvDescription.text = item.description
                root.setOnClickListener {
                    itemView.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}
