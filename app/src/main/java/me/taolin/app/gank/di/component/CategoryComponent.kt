package me.taolin.app.gank.di.component

import dagger.Component
import me.taolin.app.gank.di.ActivityScope
import me.taolin.app.gank.di.module.CategoryModule
import me.taolin.app.gank.ui.category.CategoryFragment

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/15
 * @description
 */

@ActivityScope
@Component(dependencies = [(AppComponent::class)], modules = [(CategoryModule::class)])
interface CategoryComponent {

    fun inject(fragment: CategoryFragment)
}