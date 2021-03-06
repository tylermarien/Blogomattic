package com.tylermarien.blogomattic.ui.posts

import androidx.lifecycle.Observer
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tylermarien.blogomattic.ui.dialogs.ErrorDialog
import com.tylermarien.blogomattic.R
import com.tylermarien.blogomattic.utils.Status
import com.tylermarien.blogomattic.data.Post
import com.tylermarien.blogomattic.utils.convertPixelsToDp
import kotlinx.android.synthetic.main.activity_posts.*
import org.koin.android.viewmodel.ext.android.viewModel

class PostsActivity : AppCompatActivity(), PostAdapter.OnClickListener {

    private val model: PostsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        setupViews()
        setupObservers()
        setupListeners()
    }

    override fun onPostClicked(post: Post) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra(PostActivity.PARAM_POST, post)
        }

        startActivity(intent)
    }

    private fun setupViews() {
        posts.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        model.status.observe(this, Observer {
            refresh.isRefreshing = it == Status.LOADING
        })

        val avatarSize = convertPixelsToDp(AVATAR_SIZE_IN_DP, this)
        model.posts.observe(this, Observer {
            if (it != null) {
                posts.adapter = PostAdapter(it, avatarSize, this)
            }
        })

        model.error.observe(this, Observer {
            ErrorDialog.create(it)
                .show(supportFragmentManager, ErrorDialog.TAG)
        })
    }

    private fun setupListeners() {
        refresh.setOnRefreshListener {
            model.loadPosts()
        }
    }

    companion object {
        private const val AVATAR_SIZE_IN_DP = 24
    }

}
