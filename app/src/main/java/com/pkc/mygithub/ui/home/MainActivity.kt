package com.pkc.mygithub.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pkc.mygithub.R
import com.pkc.mygithub.databinding.ActivityMainBinding
import com.pkc.mygithub.ui.component.UserAdapter
import com.pkc.mygithub.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var userAdapter: UserAdapter

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]

        setupAdapter()

        observeUsers()

        observeLoading()

        onItemUserClick()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    viewModel.searchUsers(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    private fun onItemUserClick() {
        userAdapter.onItemClick = { user ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
            startActivity(intent)
        }
    }

    private fun observeUsers() {
        viewModel.users.observe(this) { users ->
            if (users.isNotEmpty()) {
                userAdapter.submitList(users)
            } else {
                userAdapter.submitList(null)
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
            binding.rvUser.visibility = View.GONE
        } else {
            binding.animLoading.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    private fun setupAdapter() {
        userAdapter = UserAdapter()
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }
    }
}