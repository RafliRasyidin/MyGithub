package com.pkc.mygithub.data.model

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("login")
	val username: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: String? = null,
)
