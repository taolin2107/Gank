package me.taolin.app.gank.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.avos.avoscloud.feedback.Comment
import me.taolin.app.gank.App
import me.taolin.app.gank.R
import java.text.SimpleDateFormat

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/21
 * @description
 */
class FeedbackAdapter(private val dataList: List<Comment>) : RecyclerView.Adapter<FeedbackAdapter.Holder>() {

    private val dataFormat = SimpleDateFormat("MM/dd hh:mm")

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        return Holder(LayoutInflater.from(App.instance).inflate(R.layout.layout_feedback_item, parent, false))
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val comment = dataList[position]
        holder.content.text = comment.content
        holder.createTime.text = dataFormat.format(comment.createdAt)
        if (comment.commentType == Comment.CommentType.DEV) {
            holder.root.gravity = Gravity.START
            holder.container.gravity = Gravity.START
            holder.container.setBackgroundResource(R.drawable.ic_feedback_dev)
        } else {
            holder.root.gravity = Gravity.END
            holder.container.gravity = Gravity.END
            holder.container.setBackgroundResource(R.drawable.ic_feedback_user)
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view.findViewById(R.id.root) as LinearLayout
        val container = view.findViewById(R.id.container) as LinearLayout
        val content = view.findViewById(R.id.content) as TextView
        val createTime = view.findViewById(R.id.create_time) as TextView
    }
}