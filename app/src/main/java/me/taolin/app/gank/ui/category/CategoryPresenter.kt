package me.taolin.app.gank.ui.category

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.taolin.app.gank.data.source.GankApiRepository
import me.taolin.app.gank.executor.PostThreadExecutor
import me.taolin.app.gank.executor.ThreadExecutor
import me.taolin.app.gank.utils.ITEM_NUM_WITH_ONE_PAGE
import javax.inject.Inject

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/15
 * @description
 */
class CategoryPresenter @Inject constructor(private val threadExecutor: ThreadExecutor,
                                            private val postExecutor: PostThreadExecutor) : CategoryContract.Presenter {

    private var categoryView: CategoryContract.View? = null
    @Inject lateinit var gankApi: GankApiRepository
    private var disposable: Disposable? = null
    private var currentPage = 1

    override fun takeView(view: CategoryContract.View) {
        categoryView = view
    }

    override fun dropView() {
        categoryView = null
        disposable?.dispose()
    }

    override fun loadCategoryData(category: String) {
        currentPage = 1
        loadCategoryData(category, false)
    }

    override fun loadMoreCategoryData(category: String) {
        currentPage++
        loadCategoryData(category, true)
    }

    private fun loadCategoryData(category: String, isLoadMore: Boolean) {
        disposable = gankApi.getCategoryData(category, ITEM_NUM_WITH_ONE_PAGE, currentPage)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutor.getSchedule())
                .subscribe({
                    if (isLoadMore) {
                        categoryView?.loadedMoreData(it.results)
                    } else {
                        categoryView?.refreshList(it.results)
                    }
                }, { throwable ->
                    throwable.printStackTrace()
                })
    }
}