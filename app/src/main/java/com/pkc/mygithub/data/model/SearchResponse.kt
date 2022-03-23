package com.pkc.mygithub.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("items")
	val users: List<User?>? = null
)
