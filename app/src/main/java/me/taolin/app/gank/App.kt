package me.taolin.app.gank

import android.app.Application
import android.content.Context
import com.avos.avoscloud.AVOSCloud
import com.tencent.bugly.crashreport.CrashReport
import me.taolin.app.gank.di.component.AppComponent
import me.taolin.app.gank.di.component.DaggerAppComponent
import me.taolin.app.gank.di.module.AppModule
import me.taolin.app.gank.utils.APP_ID_FEEDBACK
import me.taolin.app.gank.utils.APP_KEY_FEEDBACK

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/25
 * @description
 */
class App : Application() {

    /**
     * App的单例对象
     */
    companion object {
        lateinit var instance: App
            private set
    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initBugly()
        initLeakCanary()
        component.inject(this)
        AVOSCloud.initialize(this, APP_ID_FEEDBACK, APP_KEY_FEEDBACK)
    }

    private fun initLeakCanary() {
        if (BuildConfig.DEBUG) {
            try {
                val clazz = Class.forName("com.squareup.leakcanary.LeakCanary")
                val isInAnalyzerProcessMethod = clazz.getMethod("isInAnalyzerProcess", Context::class.java)
                if (!(isInAnalyzerProcessMethod.invoke(null, this) as Boolean)) {
                    val installMethod = clazz.getMethod("install", Application::class.java)
                    installMethod.invoke(null, this)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initBugly() {
        CrashReport.initCrashReport(this, "95f8dc5843", BuildConfig.DEBUG)
    }
}
