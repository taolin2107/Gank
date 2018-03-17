package me.taolin.app.gank.di.module

import dagger.Module
import dagger.Provides
import me.taolin.app.gank.di.ActivityScope
import me.taolin.app.gank.ui.about.AboutContract
import me.taolin.app.gank.ui.about.AboutPresenter

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/17
 * @description
 */
@Module class AboutModule {

    @ActivityScope
    @Provides
    fun providePresenter(presenter: AboutPresenter): AboutContract.Presenter = presenter
}