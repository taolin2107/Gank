package me.taolin.app.gank.data.source

import me.taolin.app.gank.data.PageType

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/26
 * @description
 */
interface PageTypeSource {

    fun getPageTypeList(): List<PageType>
}