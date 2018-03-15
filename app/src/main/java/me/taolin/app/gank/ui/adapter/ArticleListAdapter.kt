package me.taolin.app.gank.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.taolin.app.gank.App
import me.taolin.app.gank.R
import me.taolin.app.gank.data.entity.Gank

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/15
 * @description
 */
class ArticleListAdapter(private val articleList: List<Gank>) : RecyclerView.Adapter<ArticleListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        return Holder(LayoutInflater.from(App.instance).inflate(R.layout.layout_article_list_item, parent, false))
    }

    override fun getItemCount() = articleList.size

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.articleTitle?.text = "${articleList[position].desc} @${articleList[position].who} "
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val articleTitle = view.findViewById(R.id.article_title) as TextView
    }
}