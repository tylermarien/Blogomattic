package com.tylermarien.blogomattic.api

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.Path
import java.util.*

interface WordPressService {
    @GET("posts")
    fun allPosts(): Single<PostsResponse>

    @GET("posts/{postId}/replies")
    fun commentsByPost(@Path("postId") postId: Int): Single<CommentsResponse>

    companion object {
        private const val BASE_URL =
            "https://public-api.wordpress.com/rest/v1.1/sites/en.blog.wordpress.com/"

        private val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        private var moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        val instance: WordPressService = retrofit.create(
            WordPressService::class.java)
    }

}