package com.pkc.mygithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.pkc.mygithub.databinding.ActivityDetailBinding
import com.pkc.mygithub.model.User
import com.pkc.mygithub.utils.toShortNumber

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getParcelableExtra(EXTRA_USER)

        setToolbar()

        initView()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setToolbar() {
        supportActionBar?.title = user?.username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        val userAvatar = ContextCompat.getDrawable(
            this,
            resources.getIdentifier(
                user?.avatar,
                "drawable",
                packageName
            ),
        )
        binding.apply {
            imgUser.setImageDrawable(userAvatar)
            tvCompany.text = user?.company
            tvFollowers.text = user?.follower?.toShortNumber()
            tvFollowing.text = user?.following?.toShortNumber()
            tvLocation.text = user?.location.toString()
            tvName.text = user?.name
            tvRepo.text = user?.repository.toString()
        }
    }

    companion object {
        const val EXTRA_USER = "extraUser"
    }
}