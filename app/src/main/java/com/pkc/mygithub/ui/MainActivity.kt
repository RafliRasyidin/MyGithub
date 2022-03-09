package com.pkc.mygithub.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pkc.mygithub.databinding.ActivityMainBinding
import com.pkc.mygithub.ui.component.UserAdapter
import com.pkc.mygithub.utils.JSONHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()

        initDataUser()

        onItemUserClick()
    }

    private fun onItemUserClick() {
        userAdapter.onItemClick = { user ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, user)
            startActivity(intent)
        }
    }

    private fun initDataUser() {
        val users = JSONHelper.loadDataUser(this)
        userAdapter.submitList(users)
    }

    private fun setupAdapter() {
        userAdapter = UserAdapter()
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }
}