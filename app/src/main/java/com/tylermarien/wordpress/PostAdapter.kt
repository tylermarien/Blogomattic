package com.tylermarien.wordpress

import android.os.Build
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tylermarien.wordpress.data.Post
import kotlinx.android.synthetic.main.view_post.view.*
import java.text.SimpleDateFormat

class PostAdapter(val posts: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun getItemCount() = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_post, parent, false) as CardView

        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.view.title.text = post.title
        holder.view.excerpt.text = toHtml(post.excerpt)
        holder.view.author.text = post.author.name
        holder.view.date.text = DateFormatter.format(post.date)
        holder.view.comments_count.text = post.discussion.commentsCount.toString()
    }

    class ViewHolder(val view: CardView): RecyclerView.ViewHolder(view)

    companion object {
        private val DateFormatter = SimpleDateFormat("MMMM d, YYYY")
    }
}

fun toHtml(source: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(source)
    }
}