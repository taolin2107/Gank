package me.taolin.app.gank.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.content.FileProvider
import me.taolin.app.gank.utils.GANK_APK_FILE_NAME
import java.io.File

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/19
 * @description
 */
class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == intent?.action) {
            try {
                openFile(context, intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun openFile(context: Context?, fileId: Long) {
        if (fileId != -1L) {
            val query = DownloadManager.Query()
            query.setFilterById(fileId)
            val cursor = (context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).query(query)
            if (cursor.moveToFirst()) {
                val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                val fileUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                val mimeType = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE))

                if (status == DownloadManager.STATUS_SUCCESSFUL && fileUri.contains(GANK_APK_FILE_NAME)) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    val filePath = Uri.parse(fileUri).path
                    // Android7.0 apk安装接口有更改
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        val contentUri = FileProvider.getUriForFile(context,
                                "me.taolin.app.gank.fileProvider", File(filePath))
                        intent.setDataAndType(contentUri, mimeType)
                    } else {
                        intent.setDataAndType(Uri.fromFile(File(filePath)), mimeType)
                    }
                    context.startActivity(intent)
                }
            }
            cursor.close()
        }
    }
}