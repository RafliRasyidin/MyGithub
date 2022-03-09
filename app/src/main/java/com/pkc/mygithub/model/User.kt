package com.pkc.mygithub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String? = null,
    val name: String? = null,
    val avatar: String? = null,
    val company: String? = null,
    val location: String? = null,
    val repository: Int? = null,
    val follower: Int? = null,
    val following: Int? = null
) : Parcelable