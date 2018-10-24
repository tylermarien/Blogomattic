package com.tylermarien.wordpress.ui.posts

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.tylermarien.wordpress.data.Post

class PostViewModel(application: Application): AndroidViewModel(application) {

    val post = MutableLiveData<Post>()

}