package me.taolin.app.gank.executor

import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/19
 * @description
 */
class JobExecutor @Inject constructor(): ThreadExecutor {

    private val INITIAL_POOL_SIZE = 3
    private val MAX_POOL_SIZE = 5
    private val KEEP_ALIVE_TIME = 10L
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    private val workQueue: LinkedBlockingDeque<Runnable> = LinkedBlockingDeque()
    private val threadFactory: ThreadFactory = JobThreadFactory()
    private val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, workQueue, threadFactory)

    override fun execute(command: Runnable) {
        threadPoolExecutor.execute(command)
    }

    private inner class JobThreadFactory : ThreadFactory {

        private val THREAD_NAME = "android_"
        private var counter = 0

        override fun newThread(r: Runnable?): Thread = Thread(r, THREAD_NAME + (counter++))
    }
}
