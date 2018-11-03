package com.tylermarien.blogomattic.data

import com.tylermarien.blogomattic.api.WordPressService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentRepository(private val service: WordPressService) {

    fun commentsByPost(postId: Int): Single<List<Comment>> {
        return service.commentsByPost(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.comments }
    }

}