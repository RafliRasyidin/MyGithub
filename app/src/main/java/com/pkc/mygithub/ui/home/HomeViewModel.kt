package com.pkc.mygithub.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pkc.mygithub.data.model.SearchResponse
import com.pkc.mygithub.data.model.User
import com.pkc.mygithub.data.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val TAG = HomeViewModel::class.java.simpleName

    private var _users = MutableLiveData<List<User?>>()
    val users: LiveData<List<User?>> = _users

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUsers(username: String) {
        _isLoading.postValue(true)
        NetworkConfig.getUserService()
            .searchUser(username)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.postValue(false)
                        val body = response.body()?.users
                        body?.let { users ->
                            _users.postValue(users)
                        }
                    } else {
                        Log.e(TAG, response.message())
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }
            })
    }
}