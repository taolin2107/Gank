package me.taolin.app.gank.di.component

import android.content.Context
import me.taolin.app.gank.di.module.AppModule
import me.taolin.app.gank.executor.PostThreadExecutor
import me.taolin.app.gank.executor.ThreadExecutor
import dagger.Component
import me.taolin.app.gank.App
import javax.inject.Singleton

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/19
 * @description
 */

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(application: App)

    fun getContext(): Context

    fun getThreadExecutor(): ThreadExecutor

    fun getPostThreadExecutor(): PostThreadExecutor
}
