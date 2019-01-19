package com.tylermarien.blogomattic.ui.posts

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.tylermarien.blogomattic.R
import com.tylermarien.blogomattic.data.Post
import com.tylermarien.blogomattic.utils.CircleTransform
import com.tylermarien.blogomattic.utils.formatExcerpt
import com.tylermarien.blogomattic.utils.formatTitle
import kotlinx.android.synthetic.main.view_post.view.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.text.SimpleDateFormat

class PostAdapter(
    val posts: List<Post>,
    private val avatarSize: Int,
    private val listener: OnClickListener
): RecyclerView.Adapter<PostAdapter.ViewHolder>(), KoinComponent {

    private val picasso: Picasso by inject()

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
        holder.view.titleView.text = formatTitle(post.title)
        holder.view.excerptView.text = formatExcerpt(post.excerpt)

        picasso.load(post.author.avatarUrl)
            .resize(avatarSize, avatarSize)
            .transform(CircleTransform())
            .into(holder.view.avatarView)

        holder.view.authorView.text = post.author.name
        holder.view.dateView.text = DateFormatter.format(post.date)
        holder.view.commentsCountView.text = post.discussion.commentsCount.toString()
    }

    class ViewHolder(val view: CardView): RecyclerView.ViewHolder(view)

    companion object {
        private val DateFormatter = SimpleDateFormat("MMMM d, YYYY")
    }
}