package com.tylermarien.blogomattic.ui.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tylermarien.blogomattic.data.Post

class PostViewModel: ViewModel() {

    val post = MutableLiveData<Post>()

}