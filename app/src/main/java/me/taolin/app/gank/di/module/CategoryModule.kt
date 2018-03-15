package me.taolin.app.gank.di.module

import dagger.Module
import dagger.Provides
import me.taolin.app.gank.di.ActivityScope
import me.taolin.app.gank.ui.category.CategoryContract
import me.taolin.app.gank.ui.category.CategoryPresenter

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/15
 * @description
 */
@Module class CategoryModule {

    @ActivityScope
    @Provides
    fun providePresenter(presenter: CategoryPresenter): CategoryContract.Presenter = presenter
}