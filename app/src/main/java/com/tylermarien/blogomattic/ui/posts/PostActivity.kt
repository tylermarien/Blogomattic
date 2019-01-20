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
import com.squareup.picasso.Picasso
import com.tylermarien.blogomattic.utils.PicassoImageGetter
import com.tylermarien.blogomattic.R
import com.tylermarien.blogomattic.data.Post
import com.tylermarien.blogomattic.utils.formatContent
import com.tylermarien.blogomattic.ui.comments.CommentsActivity
import com.tylermarien.blogomattic.utils.CircleTransform
import com.tylermarien.blogomattic.utils.formatTitle
import kotlinx.android.synthetic.main.view_post.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

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
        val maxWidth = calculateMaxWidth(windowManager)
        model.post.observe(this, Observer { post ->
            post?.let {
                title = formatTitle(it.title)

                titleView.text = formatTitle(it.title)

                picasso.load(post.author.avatarUrl)
                    .resize(getAvatarSizeInPixels(), getAvatarSizeInPixels())
                    .transform(CircleTransform())
                    .into(avatarView)

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

    private fun getAvatarSizeInPixels() =
        (AVATAR_SIZE_IN_DP * resources.displayMetrics.density).roundToInt()

    companion object {
        const val PARAM_POST = "POST"
        private const val CONTENT_PADDING = 16
        private const val AVATAR_SIZE_IN_DP = 24
        private val DateFormatter = SimpleDateFormat("MMMM d, YYYY")
    }

}