package com.example.submission2.story.storyadapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submission2.api.ListStory
import com.example.submission2.databinding.ListStoryBinding
import com.example.submission2.maps.MapsActivity
import com.example.submission2.story.detailstory.DetailStoryActivity

class StoryListAdapter :
    PagingDataAdapter<ListStory, StoryListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStory) {
            binding.apply {
                tvName.text = data.name
                tvDescription.text = data.description
                Glide.with(itemView.context)
                    .load(data.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgUploaded)

                imgUploaded.setOnClickListener {
                    val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                    intent.putExtra(DetailStoryActivity.EXTRA_NAME, data)
                    Log.d("data : ", data.toString())

                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStory)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStory>() {
            override fun areItemsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
