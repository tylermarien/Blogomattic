package com.tylermarien.blogomattic.ui.posts

import androidx.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_post.*
import java.text.SimpleDateFormat
import com.squareup.picasso.Picasso
import com.tylermarien.blogomattic.R
import com.tylermarien.blogomattic.data.Post
import com.tylermarien.blogomattic.ui.comments.CommentsActivity
import com.tylermarien.blogomattic.utils.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class PostActivity: AppCompatActivity() {

    private val model: PostViewModel by viewModel()
    private val picasso: Picasso by inject()

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
        val maxWidth = calculateMaxWidth(CONTENT_PADDING, windowManager)
        val avatarSide = convertPixelsToDp(AVATAR_SIZE_IN_DP, this)
        val formatter = SimpleDateFormat("MMMM d, YYYY", Locale.getDefault())

        model.post.observe(this, Observer { post ->
            post?.let {
                title = formatTitle(it.title)

                titleView.text = formatTitle(it.title)

                picasso.load(post.author.avatarUrl)
                    .resize(avatarSide, avatarSide)
                    .transform(CircleTransform())
                    .into(avatarView)

                authorView.text = it.author.name
                dateView.text = formatter.format(it.date)
                contentView.text = formatContent(
                    it.content,
                    PicassoImageGetter(contentView, this, maxWidth)
                )
                contentView.movementMethod = LinkMovementMethod.getInstance()
                commentsCountView.text = resources.getQuantityString(R.plurals.comments, it.discussion.commentsCount, it.discussion.commentsCount)
                commentsIconView.setOnClickListener { _ -> openComments(it) }
                commentsCountView.setOnClickListener { _ -> openComments(it) }
            }
        })
    }

    private fun setupModel() {
        model.post.value = intent.getParcelableExtra(PARAM_POST)
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
        private const val AVATAR_SIZE_IN_DP = 24
    }

}