package com.tylermarien.blogomattic.ui.posts

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tylermarien.blogomattic.R
import com.tylermarien.blogomattic.data.Post
import com.tylermarien.blogomattic.utils.fromHtml
import kotlinx.android.synthetic.main.view_post.view.*
import java.text.SimpleDateFormat

class PostAdapter(
    val posts: List<Post>,
    private val listener: OnClickListener
): RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onPostClicked(post: Post)
    }

    override fun getItemCount() = posts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_post, parent, false) as CardView

        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.view.setOnClickListener {
            listener.onPostClicked(post)
        }
        holder.view.titleView.text = post.title
        holder.view.excerptView.text = fromHtml(post.excerpt)
        holder.view.authorView.text = post.author.name
        holder.view.dateView.text = DateFormatter.format(post.date)
        holder.view.commentsCountView.text = post.discussion.commentsCount.toString()
    }

    class ViewHolder(val view: CardView): RecyclerView.ViewHolder(view)

    companion object {
        private val DateFormatter = SimpleDateFormat("MMMM d, YYYY")
    }
}