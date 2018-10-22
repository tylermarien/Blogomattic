package com.tylermarien.wordpress

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_post.*
import java.text.SimpleDateFormat
import android.util.DisplayMetrics
import android.util.Log


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
        Log.d("Density", displayMetrics.density.toString())
        val maxWidth = displayMetrics.widthPixels.toFloat() + (16 * displayMetrics.density)

        model.post.observe(this, Observer { post ->
            post?.let {
                title = it.title

                authorView.text = it.author.name
                dateView.text = DateFormatter.format(it.date)
                contentView.text = fromHtml(it.content, PicassoImageGetter(contentView, this, maxWidth))
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

    companion object {
        const val PARAM_POST = "POST"
        private val DateFormatter = SimpleDateFormat("MMMM d, YYYY")
    }

}