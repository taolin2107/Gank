package me.taolin.app.gank.ui.adapter

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import me.taolin.app.gank.App
import me.taolin.app.gank.R
import me.taolin.app.gank.data.entity.Gank
import me.taolin.app.gank.ui.content.ContentActivity
import me.taolin.app.gank.utils.KEY_CONTENT_URL

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/19
 * @description
 */
class ImageListAdapter(private val activity: Activity?, private val imageList: List<Gank>) : RecyclerView.Adapter<ImageListAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        return Holder(LayoutInflater.from(activity).inflate(R.layout.layout_image_list_item, parent, false))
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val gank = imageList[position]
        Glide.with(App.instance).load(gank.url).into(holder.image)
        holder.image.setOnClickListener {
            val intent = Intent(activity, ContentActivity::class.java)
            intent.putExtra(KEY_CONTENT_URL, gank.url)
            activity?.startActivity(intent)
        }
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById(R.id.image) as ImageView
    }
}