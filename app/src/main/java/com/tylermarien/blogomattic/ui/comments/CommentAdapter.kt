package com.tylermarien.blogomattic.ui.comments

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tylermarien.blogomattic.R
import com.tylermarien.blogomattic.data.Comment
import com.tylermarien.blogomattic.utils.fromHtml
import kotlinx.android.synthetic.main.view_comment.view.*
import java.text.SimpleDateFormat

class CommentAdapter(
    private val comments: List<Comment>
): RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    override fun getItemCount() = comments.size

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_comment, parent, false) as CardView

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = comments[position]

        holder.view.authorView.text = comment.author.name
        holder.view.dateView.text = DateFormatter.format(comment.date)
        holder.view.contentView.text = fromHtml(comment.content)
    }

    class ViewHolder(val view: CardView): RecyclerView.ViewHolder(view)

    companion object {
        private val DateFormatter = SimpleDateFormat("MMMM d, YYYY")
    }
}