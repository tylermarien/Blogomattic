package com.tylermarien.blogomattic.ui.posts

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tylermarien.blogomattic.utils.Status
import com.tylermarien.blogomattic.data.Post
import com.tylermarien.blogomattic.data.PostRepository
import io.reactivex.disposables.CompositeDisposable

class PostsViewModel(private val postRepo: PostRepository): ViewModel() {
    private val disposables = CompositeDisposable()

    val status = MutableLiveData<Status>()
    val error = MutableLiveData<String>()
    val posts = MutableLiveData<List<Post>>()

    init {
        loadPosts()
    }

    fun loadPosts() {
        status.value = Status.LOADING
        val disposable = postRepo.allPosts().subscribe({
            status.value = Status.SUCCESS
            posts.value = it
        }, {
            status.value = Status.ERROR
            error.value = it.message
        })

        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
    }

}