package me.taolin.app.gank.ui.about

import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.taolin.app.gank.data.entity.Version
import me.taolin.app.gank.executor.PostThreadExecutor
import me.taolin.app.gank.executor.ThreadExecutor
import me.taolin.app.gank.utils.URL_LATEST_VERSION
import org.json.JSONObject
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
            val json = JSONObject(it)
            Version(json.getString("version"), json.getString("url"))
        }.subscribeOn(Schedulers.from(threadExecutor))
        .observeOn(postExecutor.getSchedule())
        .subscribe({ latestVersion ->
            Log.d(TAG, "checkNewVersion: $latestVersion")
            if (latestVersion.version > currentVersion) {
                aboutView?.newVersionChecked(latestVersion.url)
            } else {
                aboutView?.isLatestVersion()
            }
        }, { throwable ->
            throwable.printStackTrace()
        })
    }

}