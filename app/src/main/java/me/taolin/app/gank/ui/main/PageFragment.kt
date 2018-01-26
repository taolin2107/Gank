package me.taolin.app.gank.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.taolin.app.gank.R
import me.taolin.app.gank.base.BaseFragment

/**
 * @author taolin
 * @version v1.0
 * @date 2018/01/26
 * @description
 */
class PageFragment : BaseFragment() {

    private val KEY_TYPE = "type"
    private var pageType: String? = null

    companion object {
        fun newInstance(type: String): PageFragment {
            val fragment = PageFragment()
            fragment.pageType = type
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_TYPE)) {
            pageType = savedInstanceState.getString(KEY_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_page, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putString(KEY_TYPE, pageType)
    }
}