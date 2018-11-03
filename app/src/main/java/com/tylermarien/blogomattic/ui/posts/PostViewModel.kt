package com.tylermarien.blogomattic.ui.posts

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.tylermarien.blogomattic.data.Post

class PostViewModel: ViewModel() {

    val post = MutableLiveData<Post>()

}