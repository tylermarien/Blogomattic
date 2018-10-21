package com.tylermarien.wordpress.data

import com.tylermarien.wordpress.api.WordPressService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PostRepository {

    fun allPosts(): Single<List<Post>> {
        return WordPressService.instance.allPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.posts }
    }

}