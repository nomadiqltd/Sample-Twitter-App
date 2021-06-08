package com.nomadiq.sampletwitterproject.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nomadiq.sampletwitterproject.domain.entity.Tweet

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Tweet>?) {
    val adapter = recyclerView.adapter as TweetListAdapter
    adapter.submitList(data)
}

@BindingAdapter("setTweetCreatedAt")
fun TextView.setTweetCreatedAt(item: Tweet) {
    item?.let {
        text = item.createdAt
    }
}

@BindingAdapter("setTweetFullText")
fun TextView.setTweetFullText(item: Tweet) {
    item?.let {
        text = item.fullText
    }
}
