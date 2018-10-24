package com.tylermarien.wordpress.ui.comments

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tylermarien.wordpress.utils.Status
import com.tylermarien.wordpress.data.Comment
import com.tylermarien.wordpress.data.CommentRepository
import com.tylermarien.wordpress.data.Post
import io.reactivex.disposables.CompositeDisposable

class CommentsViewModel: ViewModel() {

    private val disposables = CompositeDisposable()
    private val commentRepo = CommentRepository()

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