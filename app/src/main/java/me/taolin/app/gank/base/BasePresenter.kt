package me.taolin.app.gank.base

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/17
 * @description
 */
interface BasePresenter<in T> {

    fun takeView(view: T)

    fun dropView()
}
