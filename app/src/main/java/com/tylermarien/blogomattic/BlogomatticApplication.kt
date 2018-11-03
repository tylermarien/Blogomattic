package com.tylermarien.blogomattic

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tylermarien.blogomattic.api.WordPressService
import com.tylermarien.blogomattic.data.CommentRepository
import com.tylermarien.blogomattic.data.PostRepository
import com.tylermarien.blogomattic.ui.comments.CommentsViewModel
import com.tylermarien.blogomattic.ui.posts.PostViewModel
import com.tylermarien.blogomattic.ui.posts.PostsViewModel
import org.koin.android.ext.android.startKoin
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

@Suppress("UNUSED")
class BlogomatticApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}

val appModule = module {
    // Api
    single { createMoshi() }
    single { createRetrofit(get()) }

    // Repositories
    single { PostRepository(get()) }
    single { CommentRepository(get()) }

    // ViewModels
    viewModel { PostsViewModel(get()) }
    viewModel { PostViewModel() }
    viewModel { CommentsViewModel(get()) }
}

fun createRetrofit(moshi: Moshi): WordPressService = Retrofit.Builder()
    .baseUrl(WordPressService.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()
    .create(WordPressService::class.java)

fun createMoshi(): Moshi = Moshi.Builder()
    .add(Date::class.java, Rfc3339DateJsonAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()