package com.tylermarien.blogomattic

import android.app.Application
import com.tylermarien.blogomattic.data.CommentRepository
import com.tylermarien.blogomattic.data.PostRepository
import com.tylermarien.blogomattic.ui.comments.CommentsViewModel
import com.tylermarien.blogomattic.ui.posts.PostViewModel
import com.tylermarien.blogomattic.ui.posts.PostsViewModel
import org.koin.android.ext.android.startKoin
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class BlogomatticApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}

val appModule = module {
    single { PostRepository() }
    single { CommentRepository() }
    viewModel { PostsViewModel(get()) }
    viewModel { PostViewModel() }
    viewModel { CommentsViewModel(get()) }
}