package com.pkc.mygithub.ui.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pkc.mygithub.model.User
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

            val userAvatar = ContextCompat.getDrawable(
                context,
                context.resources.getIdentifier(
                    user.avatar,
                    "drawable",
                    context.packageName
                ),
            )

            imgUser.setImageDrawable(userAvatar)
            tvUsername.text = user.username
            tvCompany.text = user.company

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