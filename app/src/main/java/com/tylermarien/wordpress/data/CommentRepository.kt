package com.tylermarien.wordpress.data

import com.tylermarien.wordpress.api.WordPressService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentRepository {

    fun commentsByPost(postId: Int): Single<List<Comment>> {
        return WordPressService.instance.commentsByPost(postId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.comments }
    }

}