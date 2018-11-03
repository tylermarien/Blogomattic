package com.tylermarien.blogomattic.ui.comments

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tylermarien.blogomattic.utils.Status
import com.tylermarien.blogomattic.data.Comment
import com.tylermarien.blogomattic.data.CommentRepository
import com.tylermarien.blogomattic.data.Post
import io.reactivex.disposables.CompositeDisposable

class CommentsViewModel(private val commentRepo: CommentRepository): ViewModel() {

    private val disposables = CompositeDisposable()

    val status = MutableLiveData<Status>()
    val error = MutableLiveData<String>()
    val post = MutableLiveData<Post>()
    val comments = MutableLiveData<List<Comment>>()

    fun loadComments(postId: Int) {
        status.value = Status.LOADING
        val disposable = commentRepo.commentsByPost(postId)
            .subscribe({
                status.value = Status.SUCCESS
                comments.value = it
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