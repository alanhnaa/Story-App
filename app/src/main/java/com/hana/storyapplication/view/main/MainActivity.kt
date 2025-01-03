package com.hana.storyapplication.view.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hana.storyapplication.data.adapter.StoryAdapter
import com.hana.storyapplication.data.response.ListStoryItem
import com.hana.storyapplication.databinding.ActivityMainBinding
import com.hana.storyapplication.view.ViewModelFactory
import com.hana.storyapplication.view.uploadstory.UploadStoryActivity
import com.hana.storyapplication.view.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Splash Screen
        Thread.sleep(3000)
        installSplashScreen()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addStory.setOnClickListener {
            val intent = Intent(this, UploadStoryActivity::class.java)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.logout()
            }
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        getSession()
        showRv()
    }

    private fun getSession() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                viewModel.isLoading.observe(this) { state ->
                    showLoading(state)
                }

                viewModel.getStory().observe(this) { stories ->
                    setStoryList(stories)
                }
            }
        }
    }

    private fun showRv() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBarMain.visibility = View.VISIBLE
        else binding.progressBarMain.visibility = View.GONE
    }

    private fun setStoryList(stories: List<ListStoryItem>?) {
        val adapter = StoryAdapter()
        adapter.submitList(stories)
        binding.rvStory.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}