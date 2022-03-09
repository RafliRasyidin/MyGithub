package com.pkc.mygithub.utils

import android.content.Context
import android.util.Log
import com.pkc.mygithub.model.User
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object JSONHelper {

    private val TAG = JSONHelper::class.simpleName

    private fun parsingFileToString(context: Context, fileName: String): String? {
        return try {
            val jsonFile = context.assets.open(fileName)
            val buffer = ByteArray(jsonFile.available())
            jsonFile.read(buffer)
            jsonFile.close()
            String(buffer)
        } catch (e: IOException) {
            Log.e(TAG, e.message.toString())
            null
        }
    }

    fun loadDataUser(context: Context): List<User> {
        val listData = ArrayList<User>()

            val userObject = JSONObject(parsingFileToString(context, "githubuser.json").toString())
            val users = userObject.getJSONArray("users")
            for (index in 0 until users.length()) {
                val user = users.getJSONObject(index)

                val username = user.getString("username")
                val name = user.getString("name")
                val avatar = user.getString("avatar")
                val company = user.getString("company")
                val location = user.getString("location")
                val repository = user.getInt("repository")
                val follower = user.getInt("follower")
                val following = user.getInt("following")

                val userModel =
                    User(username, name, avatar, company, location, repository, follower, following)
                listData.add(userModel)
            }
        return listData
    }

}