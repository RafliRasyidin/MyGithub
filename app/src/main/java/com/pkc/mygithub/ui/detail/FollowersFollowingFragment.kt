package com.pkc.mygithub.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pkc.mygithub.databinding.FragmentFollowersFollowingBinding
import com.pkc.mygithub.ui.component.UserAdapter

class FollowersFollowingFragment : Fragment() {

    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewModel: DetailViewModel

    private lateinit var userAdapter: UserAdapter

    private var username: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(ARG_USERNAME)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        setupAdapter()

        setupViewPager()

        onItemUserClick()

    }

    private fun onItemUserClick() {
        userAdapter.onItemClick = { user ->
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
            requireActivity().startActivity(intent)
        }
    }

    private fun observeFollowers() {
        viewModel.getUserFollowers(username.toString())
        viewModel.userFollowers.observe(viewLifecycleOwner) { userFollowers ->
            userAdapter.submitList(userFollowers)
        }
    }

    private fun observeFollowing() {
        viewModel.getUserFollowing(username.toString())
        viewModel.userFollowing.observe(viewLifecycleOwner) { userFollowing ->
            userAdapter.submitList(userFollowing)
        }
    }

    private fun setupViewPager() {
        var index: Int? = 0

        index = arguments?.getInt(ARG_POSITION, 0)

        when (index) {
            0 -> observeFollowing()
            1 -> observeFollowers()
        }
    }

    private fun setupAdapter() {
        userAdapter = UserAdapter()
        binding.rvUser.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = userAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_USERNAME = "argUsername"
        private const val ARG_POSITION = "argPosition"

        fun newInstance(username: String, position: Int): FollowersFollowingFragment {
            return FollowersFollowingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putString(ARG_USERNAME, username)
                }
            }
        }
    }
}