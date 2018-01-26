package me.taolin.app.gank.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import me.taolin.app.gank.data.PageType
import me.taolin.app.gank.ui.main.PageFragment

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/26
 * @description
 */
class PageAdapter(fm: FragmentManager, private val typeList: List<PageType>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int) = PageFragment.newInstance(typeList[position].type)

    override fun getCount() = typeList.size

    override fun getPageTitle(position: Int) = typeList[position].name
}