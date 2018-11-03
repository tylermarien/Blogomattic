package com.tylermarien.blogomattic.data

import com.tylermarien.blogomattic.api.WordPressService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostRepository(private val service: WordPressService) {

    fun allPosts(): Single<List<Post>> {
        return service.allPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.posts }
    }

}