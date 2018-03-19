package me.taolin.app.gank.ui.about

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.taolin.app.gank.R
import me.taolin.app.gank.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*
import me.taolin.app.gank.App
import me.taolin.app.gank.BuildConfig
import me.taolin.app.gank.data.entity.Version
import me.taolin.app.gank.di.component.DaggerAboutComponent
import me.taolin.app.gank.di.module.AboutModule
import java.io.File
import javax.inject.Inject

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/17
 * @description
 */
class AboutFragment : BaseFragment(), AboutContract.View {

    private val TAG = "AboutFragment"
    @Inject lateinit var presenter: AboutContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInjector()
        presenter.takeView(this)

        appVersion.text = getString(R.string.tag_version, BuildConfig.VERSION_NAME)
        checkVersion.setOnClickListener {
            presenter.checkNewVersion(BuildConfig.VERSION_NAME)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }

    override fun newVersionChecked(version: Version) {
        AlertDialog.Builder(activity)
                .setTitle(R.string.new_version_checked_title)
                .setMessage(getString(R.string.new_version_checked_message, BuildConfig.VERSION_NAME, version.version))
                .setNegativeButton(android.R.string.cancel, { dialog, _ -> dialog.dismiss() })
                .setPositiveButton(R.string.download, { dialog, _ ->
                    presenter.downloadFile(version.url, File(""))
                    dialog.dismiss()
                })
                .create()
                .show()
    }

    override fun isLatestVersion() {
        AlertDialog.Builder(activity)
                .setTitle(R.string.check_new_version)
                .setMessage(R.string.is_latest_version_message)
                .setPositiveButton(android.R.string.ok, { dialog, _ -> dialog.dismiss() })
                .create()
                .show()
    }

    override fun versionDownloaded() {
        Log.d(TAG, "versionDownloaded: ")
    }

    private fun initInjector() {
        DaggerAboutComponent.builder()
                .appComponent(App.instance.component)
                .aboutModule(AboutModule())
                .build()
                .inject(this)
    }
}