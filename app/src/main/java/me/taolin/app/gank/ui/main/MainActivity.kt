package me.taolin.app.gank.ui.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import me.taolin.app.gank.R
import me.taolin.app.gank.base.BaseActivity
import me.taolin.app.gank.ui.about.AboutFragment
import me.taolin.app.gank.ui.category.CategoryFragment

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open_desc, R.string.drawer_close_desc)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationMenu.setNavigationItemSelectedListener(this)
    }

    override fun onResume() {
        super.onResume()
//        replaceContent(CategoryFragment.newInstance("all"))
        replaceContent(AboutFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        title = item.title
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.type_today -> replaceContent(CategoryFragment.newInstance("all"))
            R.id.type_android -> replaceContent(CategoryFragment.newInstance("Android"))
            R.id.type_ios -> replaceContent(CategoryFragment.newInstance("iOS"))
            R.id.type_front_end -> replaceContent(CategoryFragment.newInstance("前端"))
            R.id.type_beauty -> replaceContent(CategoryFragment.newInstance("福利"))
            R.id.type_relax_video -> replaceContent(CategoryFragment.newInstance("休息视频"))
            R.id.type_about -> replaceContent(AboutFragment())
            R.id.type_feedback -> {}
        }
        return true
    }

    private fun replaceContent(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.content_panel, fragment).commit()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
