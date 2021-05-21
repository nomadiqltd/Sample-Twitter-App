package com.nomadiq.sampletwitterproject.ui

import com.bumptech.glide.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nomadiq.sampletwitterproject.BuildConfig.*
import com.nomadiq.sampletwitterproject.ui.data.Tweet
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// TODO - hide
private const val BASE_URL = "https://api.twitter.com/1.1/"

// Logging
fun provideLoggingInterceptor(): Interceptor =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

// Create a new Interceptor.
var headerAuthorizationInterceptor: Interceptor = object : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val headers: Headers = request.headers.newBuilder().
        add("clientKey", CLIENT_KEY).
        add("clientSecret", CLIENT_SECRET).
        add("Authorization", "Bearer $BEARER_TOKEN").
        build()
        request = request.newBuilder().headers(headers).build()
        return chain.proceed(request)
    }
}

private val client = OkHttpClient().newBuilder()
    .addInterceptor(provideLoggingInterceptor())
    .addNetworkInterceptor(headerAuthorizationInterceptor)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(client)
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

/**
 * A public interface that exposes the [getTweets] method
 */
interface ApiService {
    /**
     * Returns a [List] of [Tweet]
     */
    @GET("lists/statuses.json?list_id=871746761387323394&tweet_mode=extended&include_entities=1&count=10/")
    suspend fun getTweets(): List<Tweet>
   // suspend fun getTweets(@Query("list_id") listId : String ): List<Tweet>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object TwitterApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}