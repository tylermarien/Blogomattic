package com.tylermarien.wordpress

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.tylermarien.wordpress.data.Post
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PostAdapter.OnClickListener {

    private val model by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            ErrorDialog.create(it).show(supportFragmentManager, ErrorDialog.TAG)
        })
    }

    private fun setupListeners() {
        refresh.setOnRefreshListener {
            model.loadPosts()
        }
    }

}
