package me.taolin.app.gank.ui.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.taolin.app.gank.R
import me.taolin.app.gank.base.BaseListAdapter
import me.taolin.app.gank.ui.content.ContentActivity
import me.taolin.app.gank.utils.KEY_CONTENT_URL

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/15
 * @description
 */
class ArticleListAdapter(private val activity: Activity?) : BaseListAdapter<ArticleListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        return Holder(LayoutInflater.from(activity).inflate(R.layout.layout_article_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val gank = gankList[position]
        holder.articleTitle.text = activity?.getString(R.string.article_list_item_title, gank.desc, gank.who)
        holder.articleTitle.setOnClickListener {
            val intent = Intent(activity, ContentActivity::class.java)
            intent.putExtra(KEY_CONTENT_URL, gank.url)
            activity?.startActivity(intent)
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val articleTitle = view.findViewById(R.id.article_title) as TextView
    }
}