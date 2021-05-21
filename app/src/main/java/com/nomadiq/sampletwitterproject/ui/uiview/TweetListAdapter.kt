package com.nomadiq.sampletwitterproject.ui.uiview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nomadiq.sampletwitterproject.R
import com.nomadiq.sampletwitterproject.databinding.AdapterTweetItemBinding
import com.nomadiq.sampletwitterproject.ui.data.Tweet

class TweetListAdapter() :
    ListAdapter<Tweet,
            RecyclerView.ViewHolder>(DiffCallback) {

    override fun submitList(list: List<Tweet>?) {
        super.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val dataItem = getItem(position) as Tweet
                holder.bind(dataItem)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Tweet>() {
        override fun areItemsTheSame(
            oldItem: Tweet,
            newItem: Tweet
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: Tweet,
            newItem: Tweet
        ): Boolean {
            return oldItem.tweetId == newItem.tweetId
        }
    }

    class ViewHolder(private var binding: AdapterTweetItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tweet: Tweet) {
            binding.tweet = tweet
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AdapterTweetItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}