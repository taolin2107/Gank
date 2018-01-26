package me.taolin.app.gank.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/19
 * @description
 */
class UIThread @Inject constructor(): PostThreadExecutor {

    override fun getSchedule(): Scheduler = AndroidSchedulers.mainThread()

}
