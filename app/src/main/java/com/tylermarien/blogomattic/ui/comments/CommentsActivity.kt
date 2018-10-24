package com.tylermarien.blogomattic.ui.comments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.tylermarien.blogomattic.ui.dialogs.ErrorDialog
import com.tylermarien.blogomattic.R
import com.tylermarien.blogomattic.utils.Status
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity: AppCompatActivity() {

    private val model by lazy {
        ViewModelProviders.of(this).get(CommentsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        setupViews()
        setupObservers()

        model.post.value = intent.getParcelableExtra(PARAM_POST)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun setupViews() {
        commentsView.layoutManager = LinearLayoutManager(this)
    }

    fun setupObservers() {
        model.post.observe(this, Observer {
            it?.let { post ->
                title = post.title
                model.loadComments(post.id)
            }
        })

        model.status.observe(this, Observer {
            it?.let { status ->
                refreshView.isRefreshing = status == Status.LOADING
            }
        })

        model.comments.observe(this, Observer {
            it?.let { comments ->
                commentsView.adapter =
                        CommentAdapter(comments)
            }
        })

        model.error.observe(this, Observer {
            it?.let { message ->
                ErrorDialog.create(message).show(supportFragmentManager,
                    ErrorDialog.TAG
                )
            }
        })
    }

    companion object {
        const val PARAM_POST = "POST"
    }

}