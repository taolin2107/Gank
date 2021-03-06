package me.taolin.app.gank.ui.category

import `in`.srain.cube.views.ptr.PtrDefaultHandler2
import `in`.srain.cube.views.ptr.PtrFrameLayout
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_category.*
import me.taolin.app.gank.App
import me.taolin.app.gank.R
import me.taolin.app.gank.base.BaseFragment
import me.taolin.app.gank.base.BaseListAdapter
import me.taolin.app.gank.data.entity.Gank
import me.taolin.app.gank.di.component.DaggerCategoryComponent
import me.taolin.app.gank.di.module.CategoryModule
import me.taolin.app.gank.ui.adapter.ArticleListAdapter
import me.taolin.app.gank.ui.adapter.ImageListAdapter
import me.taolin.app.gank.utils.CATEGORY_BEAUTY
import javax.inject.Inject

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/26
 * @description
 */
class CategoryFragment : BaseFragment(), CategoryContract.View {

    private val KEY_CATEGORY = "category"
    private lateinit var pageCategory: String
    @Inject lateinit var presenter: CategoryContract.Presenter
    private lateinit var listAdapter: BaseListAdapter<*>

    companion object {
        fun newInstance(category: String): CategoryFragment {
            val fragment = CategoryFragment()
            fragment.pageCategory = category
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CATEGORY)) {
            pageCategory = savedInstanceState.getString(KEY_CATEGORY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initInjector()
        presenter.takeView(this)

        if (pageCategory == CATEGORY_BEAUTY) {
            listAdapter = ImageListAdapter(activity)
            articleListView.layoutManager = GridLayoutManager(activity, 2)
        } else {
            listAdapter = ArticleListAdapter(activity)
            articleListView.layoutManager = LinearLayoutManager(activity)
        }
        articleListView.adapter = listAdapter
        listAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                emptyView.visibility = if (listAdapter.itemCount > 0) View.GONE else View.VISIBLE
            }
        })

        refreshLayout.autoLoadMore()
        refreshLayout.setLastUpdateTimeRelateObject(this)
        refreshLayout.setPtrHandler(object : PtrDefaultHandler2() {
            override fun onRefreshBegin(frame: PtrFrameLayout?) {
                presenter.loadCategoryData(pageCategory)
            }

            override fun onLoadMoreBegin(frame: PtrFrameLayout?) {
                presenter.loadMoreCategoryData(pageCategory)
            }
        })
        presenter.loadCategoryData(pageCategory)
    }

    override fun refreshList(list: List<Gank>) {
        listAdapter.setDataList(list)
        listAdapter.notifyDataSetChanged()
        stopRefresh()
    }

    override fun loadedMoreData(list: List<Gank>) {
        listAdapter.addDataList(list)
        listAdapter.notifyDataSetChanged()
        stopRefresh()
    }

    private fun stopRefresh() {
        refreshLayout.refreshComplete()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.dropView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_CATEGORY, pageCategory)
    }

    private fun initInjector() {
        DaggerCategoryComponent.builder()
                .appComponent(App.instance.component)
                .categoryModule(CategoryModule())
                .build()
                .inject(this)
    }
}