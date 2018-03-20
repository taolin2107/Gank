package me.taolin.app.gank.ui.about

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
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
import javax.inject.Inject

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/17
 * @description
 */
class AboutFragment : BaseFragment(), AboutContract.View {

    private val REQUEST_CODE_WRITE_PERMISSION = 0x01
    @Inject lateinit var presenter: AboutContract.Presenter
    private var newVersion: Version? = null

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
                    newVersion = version
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(App.instance, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                // Should we show an explanation
                            } else {
                                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE_WRITE_PERMISSION)
                            }
                        } else {
                            presenter.downloadFile(version.url)
                        }
                    } else {
                        presenter.downloadFile(version.url)
                    }
                    dialog.dismiss()
                })
                .create()
                .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_WRITE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                newVersion?.let {
                    presenter.downloadFile(it.url)
                }
            }
        }
    }

    override fun isLatestVersion() {
        AlertDialog.Builder(activity)
                .setTitle(R.string.check_new_version)
                .setMessage(R.string.is_latest_version_message)
                .setPositiveButton(android.R.string.ok, { dialog, _ -> dialog.dismiss() })
                .create()
                .show()
    }

    private fun initInjector() {
        DaggerAboutComponent.builder()
                .appComponent(App.instance.component)
                .aboutModule(AboutModule())
                .build()
                .inject(this)
    }
}