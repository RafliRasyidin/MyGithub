package com.pkc.mygithub.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pkc.mygithub.data.model.User
import com.pkc.mygithub.data.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val TAG = DetailViewModel::class.java.simpleName

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _userFollowing = MutableLiveData<List<User>>()
    val userFollowing: LiveData<List<User>> = _userFollowing

    private var _userFollowers = MutableLiveData<List<User>>()
    val userFollowers: LiveData<List<User>> = _userFollowers

    fun getDetailUser(username: String) {
        _isLoading.postValue(true)
        NetworkConfig.getUserService()
            .getDetailUser(username)
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    _isLoading.postValue(false)
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let { user ->
                            _user.postValue(user)
                        }
                    } else {
                        Log.e(TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    _isLoading.postValue(false)
                    Log.e(TAG, t.message.toString())
                }
            })
    }

    fun getUserFollowers(username: String) {
        NetworkConfig.getUserService()
            .getUserFollowers(username)
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let { userFollowers ->
                            _userFollowers.postValue(userFollowers)
                        }
                    } else {
                        Log.e(TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }
            })
    }

    fun getUserFollowing(username: String) {
        NetworkConfig.getUserService()
            .getUserFollowing(username)
            .enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        body?.let { userFollowing ->
                            _userFollowing.postValue(userFollowing)
                        }
                    } else {
                        Log.e(TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }
            })
    }
}