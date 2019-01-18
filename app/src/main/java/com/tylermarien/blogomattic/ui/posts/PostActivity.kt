package com.tylermarien.blogomattic.ui.posts

import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_post.*
import java.text.SimpleDateFormat
import android.util.DisplayMetrics
import android.view.WindowManager
import com.tylermarien.blogomattic.utils.PicassoImageGetter
import com.tylermarien.blogomattic.R
import com.tylermarien.blogomattic.data.Post
import com.tylermarien.blogomattic.utils.formatContent
import com.tylermarien.blogomattic.ui.comments.CommentsActivity
import com.tylermarien.blogomattic.utils.formatTitle
import org.koin.android.viewmodel.ext.android.viewModel

class PostActivity: AppCompatActivity() {

    private val model: PostViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        setupActionBar()
        setupObservers()
        setupModel()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupObservers() {
        val maxWidth = calculateMaxWidth(windowManager)
        model.post.observe(this, Observer { post ->
            post?.let {
                title = formatTitle(it.title)

                authorView.text = it.author.name
                dateView.text = DateFormatter.format(it.date)
                contentView.text = formatContent(
                    it.content,
                    PicassoImageGetter(contentView, this, maxWidth)
                )
                contentView.movementMethod = LinkMovementMethod.getInstance()
                commentsCountView.text = it.discussion.commentsCount.toString()
                commentsIconView.setOnClickListener { _ -> openComments(it) }
                commentsCountView.setOnClickListener { _ -> openComments(it) }
            }
        })
    }

    private fun setupModel() {
        model.post.value = intent.getParcelableExtra(PARAM_POST)
    }

    private fun calculateMaxWidth(windowManager: WindowManager): Float {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels.toFloat() - (CONTENT_PADDING * displayMetrics.density)
    }

    private fun openComments(post: Post) {
        val intent = Intent(this, CommentsActivity::class.java).apply {
            putExtra(CommentsActivity.PARAM_POST, post)
        }

        startActivity(intent)
    }

    companion object {
        const val PARAM_POST = "POST"
        private const val CONTENT_PADDING = 16
        private val DateFormatter = SimpleDateFormat("MMMM d, YYYY")
    }

}