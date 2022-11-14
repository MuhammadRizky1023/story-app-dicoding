package com.example.submission2.story.detailstory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submission2.api.ListStory
import com.example.submission2.databinding.ActivityDetailStoryBinding
import com.example.submission2.story.allstory.AllStoryActivity

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupActions()
    }

    private fun setupActions() {
        val detail = intent.getParcelableExtra<ListStory>(EXTRA_NAME) as ListStory
        binding.apply {
            tvName.text = detail.name
            tvDescription.text = detail.description
            tvCreatedAt.text =
                StringBuilder().append("Posted at ").append(detail.createdAt.toString())
            Glide.with(this@DetailStoryActivity)
                .load(detail.photoUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgUploaded)
        }

        binding.btnShare.setOnClickListener {
            val shareUser = Intent(Intent.ACTION_SEND)
            shareUser.type = "text/plain"
            val textOnShare = "Share this user profile via"
            shareUser.putExtra(Intent.EXTRA_TEXT, textOnShare)
            startActivity(Intent.createChooser(shareUser, "Share Via"))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        val backTo = Intent(this, AllStoryActivity::class.java)
        startActivity(backTo)
        return true
    }


}
