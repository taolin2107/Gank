package me.taolin.app.gank.base

import android.support.v7.widget.RecyclerView
import me.taolin.app.gank.data.entity.Gank

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/19
 * @description
 */
abstract class BaseListAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    var gankList = ArrayList<Gank>()

    fun setDataList(list: List<Gank>) {
        gankList.clear()
        addDataList(list)
    }

    fun addDataList(list: List<Gank>) {
        gankList.addAll(list)
    }

    override fun getItemCount() = gankList.size
}