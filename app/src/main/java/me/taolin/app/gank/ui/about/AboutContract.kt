package me.taolin.app.gank.ui.about

import me.taolin.app.gank.base.BasePresenter
import me.taolin.app.gank.base.BaseView

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/17
 * @description
 */
interface AboutContract {

    interface View : BaseView<Presenter> {

        fun newVersionChecked(versionUrl: String)

        fun isLatestVersion()
    }

    interface Presenter : BasePresenter<View> {

        fun checkNewVersion(currentVersion: String)
    }
}