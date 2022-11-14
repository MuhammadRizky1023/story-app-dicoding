package com.example.submission2.story.allstory

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.ViewModelFactory
import com.example.submission2.api.ListStory
import com.example.submission2.preferences.UserPreference
import com.example.submission2.databinding.ActivityAllStoryBinding
import com.example.submission2.preferences.LoginSession
import com.example.submission2.main.MainActivity
import com.example.submission2.maps.MapsActivity
import com.example.submission2.story.detailstory.DetailStoryActivity
import com.example.submission2.story.pagingstory.PagingStoriesViewModel
import com.example.submission2.story.storyadapter.StoryListAdapter
import com.google.android.gms.maps.model.LatLng

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class AllStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllStoryBinding
    private lateinit var adapter: StoryListAdapter

    private lateinit var allStoryViewModel: AllStoryViewModel
    private val pagingStoriesViewModel: PagingStoriesViewModel by viewModels {
        PagingStoriesViewModel.PagingViewModelFactory(this)
    }

    private var storyLoc: ArrayList<LatLng>? = null
    private var storyName: ArrayList<String>? = null
    private var storyDesc: ArrayList<String>? = null

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = StoryListAdapter()
        adapter.notifyDataSetChanged()

        setupViewModel()
        setupAction()

        binding.apply {
            rvAllstory.layoutManager = LinearLayoutManager(this@AllStoryActivity)
            rvAllstory.setHasFixedSize(true)
            rvAllstory.adapter = adapter
        }

        // display all story using paging
        val loginSession = LoginSession(this)
        val token = loginSession.passToken().toString()
        showLoading(true)
        pagingStoriesViewModel.allStory.observe(this) {
            adapter.submitData(lifecycle, it)
            showLoading(false)
        }

        // get stories loc, user name, and desc, to display marker to maps activity
        storyLoc = ArrayList()
        storyName = ArrayList()
        storyDesc = ArrayList()
        allStoryViewModel.getAllStories("Bearer $token")
        allStoryViewModel.getStoriesPaging().observe(this) {
            if (it != null) {
                for (i in it.indices) {
                    storyLoc?.add(LatLng(it[i].lat, it[i].lon))
                    storyName?.add(it[i].name.toString())
                    storyDesc?.add(it[i].description.toString())
                }
            }
        }
    }

    private fun setupViewModel(){
        allStoryViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[AllStoryViewModel::class.java]

        allStoryViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                binding.nameTextView.text = getString(R.string.greetingStory, user.name)
            }
        }
    }

    private fun setupAction() {
        adapter.setOnItemClickCallback(object : StoryListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStory) {
                val intentToDetail = Intent(this@AllStoryActivity, DetailStoryActivity::class.java)
                intentToDetail.putExtra(DetailStoryActivity.EXTRA_NAME, data)
                startActivity(intentToDetail)
            }
        })

        // to send data and go to maps activity
        binding.fabMaps.setOnClickListener {
            val intentMap = Intent(this, MapsActivity::class.java)
            intentMap.putExtra(MapsActivity.EXTRA_LOC, storyLoc)
            intentMap.putExtra(MapsActivity.EXTRA_NAME, storyName)
            intentMap.putExtra(MapsActivity.EXTRA_DESC, storyDesc)
            startActivity(
                intentMap,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        val backTo = Intent(this, MainActivity::class.java)
        startActivity(backTo)
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
