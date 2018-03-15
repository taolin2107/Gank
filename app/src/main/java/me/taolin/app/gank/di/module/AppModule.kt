package me.taolin.app.gank.di.module

import android.content.Context
import me.taolin.app.gank.executor.JobExecutor
import me.taolin.app.gank.executor.PostThreadExecutor
import me.taolin.app.gank.executor.ThreadExecutor
import me.taolin.app.gank.executor.UIThread
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/19
 * @description
 */

@Module class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideThreadExecutor(executor: JobExecutor): ThreadExecutor = executor

    @Provides
    @Singleton
    fun providePostThreadExecutor(executor: UIThread): PostThreadExecutor = executor
}
