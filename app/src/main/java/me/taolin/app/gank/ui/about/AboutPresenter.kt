package me.taolin.app.gank.ui.about

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.taolin.app.gank.App
import me.taolin.app.gank.R
import me.taolin.app.gank.data.entity.Version
import me.taolin.app.gank.executor.PostThreadExecutor
import me.taolin.app.gank.executor.ThreadExecutor
import me.taolin.app.gank.utils.GANK_APK_FILE_NAME
import me.taolin.app.gank.utils.URL_LATEST_VERSION
import org.json.JSONObject
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/17
 * @description
 */
class AboutPresenter @Inject constructor(private val threadExecutor: ThreadExecutor,
                                         private val postExecutor: PostThreadExecutor) : AboutContract.Presenter {

    private val TAG = "AboutPresenter"

    private var aboutView: AboutContract.View? = null
    private var disposable: Disposable? = null

    override fun takeView(view: AboutContract.View) {
        aboutView = view
    }

    override fun dropView() {
        aboutView = null
        disposable?.dispose()
    }

    override fun checkNewVersion(currentVersion: String) {
        disposable = Observable.create<String> { emit ->
            emit.onNext(URL_LATEST_VERSION)
            emit.onComplete()
        }.map { url ->
            var conn: HttpURLConnection? = null
            var input: InputStream? = null
            try {
                conn = URL(url).openConnection() as HttpURLConnection
                conn.defaultUseCaches = false
                conn.useCaches = false
                conn.connectTimeout = 5000
                conn.readTimeout = 10000
                conn.connect()

                input = conn.inputStream
                val buffer = ByteArray(1024)
                val len = input.read(buffer)
                String(buffer, 0, len)
            } catch (e: Throwable) {
                e.printStackTrace()
                "{version: \"0\", url: \"\"}"
            } finally {
                try {
                    input?.close()
                    conn?.disconnect()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.map {
            Log.d(TAG, "check version info: $it")
            val json = JSONObject(it)
            Version(json.getString("version"), json.getString("url"))
        }.subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutor.getSchedule())
        .subscribe({ latestVersion ->
            if (latestVersion.version > currentVersion) {
                aboutView?.newVersionChecked(latestVersion)
            } else {
                aboutView?.isLatestVersion()
            }
        }, { throwable ->
            throwable.printStackTrace()
        })
    }

    override fun downloadFile(fileUrl: String) {
        val downloadRequest = DownloadManager.Request(Uri.parse(fileUrl))
        downloadRequest.setTitle(App.instance.getString(R.string.version_downloading_title))
        downloadRequest.setDescription(App.instance.getString(R.string.version_downloading_message))
        downloadRequest.setMimeType("application/vnd.android.package-archive")

        //在通知栏显示下载进度
        downloadRequest.allowScanningByMediaScanner()
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        val gankFile = File("${Environment.getExternalStorageDirectory().absolutePath}/download/$GANK_APK_FILE_NAME")
        if (gankFile.exists()) {
            gankFile.delete()
        }

        //设置保存下载apk保存路径
        downloadRequest.setDestinationInExternalPublicDir("download", GANK_APK_FILE_NAME)

        //进入下载队列
        (App.instance.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(downloadRequest)
    }
}