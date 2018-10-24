package com.tylermarien.wordpress.ui.posts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_post.*
import java.text.SimpleDateFormat
import android.util.DisplayMetrics
import com.tylermarien.wordpress.utils.PicassoImageGetter
import com.tylermarien.wordpress.R
import com.tylermarien.wordpress.data.Post
import com.tylermarien.wordpress.utils.fromHtml
import com.tylermarien.wordpress.ui.comments.CommentsActivity

class PostActivity: AppCompatActivity() {

    private val model by lazy {
        ViewModelProviders.of(this).get(PostViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val maxWidth = displayMetrics.widthPixels.toFloat() + (16 * displayMetrics.density)

        model.post.observe(this, Observer { post ->
            post?.let {
                title = it.title

                authorView.text = it.author.name
                dateView.text = DateFormatter.format(it.date)
                contentView.text = fromHtml(
                    it.content,
                    PicassoImageGetter(contentView, this, maxWidth)
                )
                contentView.movementMethod = LinkMovementMethod.getInstance()
                commentsCountView.text = it.discussion.commentsCount.toString()
                commentsIconView.setOnClickListener { _ -> openComments(it) }
                commentsCountView.setOnClickListener { _ -> openComments(it) }
            }
        })

        model.post.value = intent.getParcelableExtra(PARAM_POST)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun openComments(post: Post) {
        val intent = Intent(this, CommentsActivity::class.java).apply {
            putExtra(CommentsActivity.PARAM_POST, post)
        }

        startActivity(intent)
    }

    companion object {
        const val PARAM_POST = "POST"
        private val DateFormatter = SimpleDateFormat("MMMM d, YYYY")
    }

}