package com.tylermarien.wordpress.api

import com.tylermarien.wordpress.data.Post

class PostsResponse(val found: Int, val posts: List<Post>)