package com.pkc.mygithub.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.pkc.mygithub.R
import com.pkc.mygithub.databinding.ActivityDetailBinding
import com.pkc.mygithub.ui.component.DetailPagerAdapter
import com.pkc.mygithub.ui.component.DetailPagerAdapter.Companion.TAB_TITLES
import com.pkc.mygithub.utils.toShortNumber

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var username: String = ""

    private lateinit var viewModel: DetailViewModel

    private lateinit var mediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME).toString()

        supportActionBar?.hide()

        setupPagerAdapter()

        setupTabLayout()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        binding.imgBack.setOnClickListener {
            finish()
        }

        observeUserDetail()

        observeLoading()

    }

    private fun setupPagerAdapter() {
        val pagerAdapter = DetailPagerAdapter(this, username)
        binding.vp.adapter = pagerAdapter
    }

    private fun setupTabLayout() {
        mediator = TabLayoutMediator(binding.tabs, binding.vp) { tabs, pos ->
            tabs.text = when (pos) {
                0 -> getString(TAB_TITLES[0])
                else -> getString(TAB_TITLES[1])
            }
        }
        mediator.attach()
    }

    private fun observeUserDetail() {
        viewModel.getDetailUser(username)
        viewModel.user.observe(this) { user ->
            binding.tvToolbar.text = user.username
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(user.avatarUrl)
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_error_image)
                    .into(imgUser)

                tvRepo.text = user.publicRepos.toString().toShortNumber()
                tvFollowing.text = user.followers.toString().toShortNumber()
                tvFollowers.text = user.following.toString().toShortNumber()
                tvLocation.text = user.location
                tvCompany.text = user.company
                tvName.text = user.name
            }
        }
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.animLoading.visibility = View.VISIBLE
        } else {
            binding.animLoading.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediator.detach()
    }

    companion object {
        const val EXTRA_USERNAME = "extraUsername"
    }
}