package me.taolin.app.gank.executor

import io.reactivex.Scheduler

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/19
 * @description
 */
interface PostThreadExecutor {

    fun getSchedule(): Scheduler
}
