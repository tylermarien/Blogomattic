package com.tylermarien.wordpress

import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tylermarien.wordpress.data.Post
import kotlinx.android.synthetic.main.view_post.view.*

class PostAdapter(val posts: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun getItemCount() = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_post, parent, false) as ConstraintLayout

        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.view.title.text = post.title
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.view.excerpt.text = Html.fromHtml(post.excerpt, Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.view.excerpt.text = Html.fromHtml(post.excerpt)
        }
    }

    class ViewHolder(val view: ConstraintLayout): RecyclerView.ViewHolder(view)

}