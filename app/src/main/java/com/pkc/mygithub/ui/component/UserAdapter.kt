package com.pkc.mygithub.ui.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pkc.mygithub.R
import com.pkc.mygithub.data.model.User
import com.pkc.mygithub.databinding.ItemUserBinding

class UserAdapter : ListAdapter<User, UserAdapter.UserViewHolder>(DiffCallback) {

    class UserViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    var onItemClick: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)

        val context = holder.itemView.context
        val binding = ItemUserBinding.bind(holder.itemView)

        with(binding) {

            Glide.with(context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_error_image)
                .into(imgUser)

            tvUsername.text = user.username
            tvType.text = user.type

            root.setOnClickListener {
                onItemClick?.invoke(user)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
}