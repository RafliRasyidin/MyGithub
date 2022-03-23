package com.pkc.mygithub.ui.component

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pkc.mygithub.R
import com.pkc.mygithub.ui.detail.FollowersFollowingFragment

class DetailPagerAdapter(activity: AppCompatActivity, private val username: String) :
    FragmentStateAdapter(
        activity
    ) {

    override fun getItemCount(): Int {
        return TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        return FollowersFollowingFragment.newInstance(username, position)
    }

    companion object {
        val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
    }
}