package com.nomadiq.sampletwitterproject.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nomadiq.sampletwitterproject.data.network.TwitterApi
import com.nomadiq.sampletwitterproject.domain.entity.Tweet
import kotlinx.coroutines.*

private const val TAG = "FragmentMainViewModel"

class FragmentMainViewModel : ViewModel() {

    private var viewModelJob = Job()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    // Backing property
    private val _tweets = MutableLiveData<List<Tweet>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val tweets: LiveData<List<Tweet>>
        get() = _tweets

    // The external LiveData interface to the property is immutable, so only this class can modify
    private var _tweet = MutableLiveData<Tweet>()

    val tweet: LiveData<Tweet>
        get() = _tweet

    init {
        getTweets()
    }

    /**
     * Gets tweets information from API Retrofit service and updates the [Tweet] [List]
     */
    private fun getTweets() {
        viewModelScope.launch {

            val receivedTweetList =
                TwitterApi.retrofitService.getTweets()
            try {
                _response.value = "Success: ${receivedTweetList.size} tweets retrieved"
                Log.d(TAG, _response.value.toString())
                if (receivedTweetList.isNotEmpty()) {
                    _tweets.value = receivedTweetList
                }
            } catch (t: Throwable) {
                _response.value = "Failure: " + t.message
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}


