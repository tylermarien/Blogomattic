package com.tylermarien.wordpress

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tylermarien.wordpress.data.Post
import com.tylermarien.wordpress.data.PostRepository
import io.reactivex.disposables.CompositeDisposable

class MainViewModel: ViewModel() {
    private val disposables = CompositeDisposable()
    private val postRepo = PostRepository()

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