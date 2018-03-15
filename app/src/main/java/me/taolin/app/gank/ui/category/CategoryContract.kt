package me.taolin.app.gank.ui.category

import me.taolin.app.gank.base.BasePresenter
import me.taolin.app.gank.base.BaseView
import me.taolin.app.gank.data.entity.Gank

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/15
 * @description
 */
interface CategoryContract {

    interface View : BaseView<Presenter> {

        fun refreshList(list: List<Gank>)
    }

    interface Presenter : BasePresenter<View> {

        fun loadCategoryData(category: String, pageNum: Int, pageCount: Int)
    }
}