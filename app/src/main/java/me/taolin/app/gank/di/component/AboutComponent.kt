package me.taolin.app.gank.di.component

import dagger.Component
import me.taolin.app.gank.di.ActivityScope
import me.taolin.app.gank.di.module.AboutModule
import me.taolin.app.gank.ui.about.AboutFragment

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/17
 * @description
 */

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(AboutModule::class))
interface AboutComponent {

    fun inject(fragment: AboutFragment)
}