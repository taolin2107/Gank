package me.taolin.app.gank.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.taolin.app.gank.R
import me.taolin.app.gank.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*
import me.taolin.app.gank.BuildConfig

/**
 * @author taolin
 * @version v1.0
 * @date 2018/03/17
 * @description
 */
class AboutFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appVersion.text = getString(R.string.tag_version, BuildConfig.VERSION_NAME)
    }
}