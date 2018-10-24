package com.tylermarien.blogomattic.api

import com.tylermarien.blogomattic.data.Post

data class PostsResponse(val found: Int, val posts: List<Post>)