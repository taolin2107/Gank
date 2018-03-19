package me.taolin.app.gank.ui.about

import me.taolin.app.gank.base.BasePresenter
import me.taolin.app.gank.base.BaseView
import me.taolin.app.gank.data.entity.Version
import java.io.File

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/17
 * @description
 */
interface AboutContract {

    interface View : BaseView<Presenter> {

        fun newVersionChecked(version: Version)

        fun isLatestVersion()

        fun versionDownloaded()
    }

    interface Presenter : BasePresenter<View> {

        fun checkNewVersion(currentVersion: String)

        fun downloadFile(fileUrl: String, saveFile: File)
    }
}