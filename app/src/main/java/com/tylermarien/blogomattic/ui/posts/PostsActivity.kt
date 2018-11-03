package com.tylermarien.blogomattic.ui.posts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.tylermarien.blogomattic.ui.dialogs.ErrorDialog
import com.tylermarien.blogomattic.R
import com.tylermarien.blogomattic.utils.Status
import com.tylermarien.blogomattic.data.Post
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

        model.posts.observe(this, Observer {
            if (it != null) {
                posts.adapter = PostAdapter(it, this)
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

}
