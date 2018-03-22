package me.taolin.app.gank.ui.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.Toast
import com.avos.avoscloud.feedback.FeedbackAgent
import kotlinx.android.synthetic.main.activity_main.*
import me.taolin.app.gank.R
import me.taolin.app.gank.base.BaseActivity
import me.taolin.app.gank.ui.about.AboutFragment
import me.taolin.app.gank.ui.category.CategoryFragment
import me.taolin.app.gank.ui.feedback.FeedbackFragment
import me.taolin.app.gank.utils.*

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
        if (supportFragmentManager.findFragmentById(R.id.content_panel) == null) {
            replaceContent(CategoryFragment.newInstance(CATEGORY_ALL))
        }
        FeedbackAgent(this).sync()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        title = item.title
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.type_today -> replaceContent(CategoryFragment.newInstance(CATEGORY_ALL))
            R.id.type_android -> replaceContent(CategoryFragment.newInstance(CATEGORY_ANDROID))
            R.id.type_ios -> replaceContent(CategoryFragment.newInstance(CATEGORY_IOS))
            R.id.type_front_end -> replaceContent(CategoryFragment.newInstance(CATEGORY_FRONT_END))
            R.id.type_beauty -> replaceContent(CategoryFragment.newInstance(CATEGORY_BEAUTY))
            R.id.type_relax_video -> replaceContent(CategoryFragment.newInstance(CATEGORY_VIDEO))
            R.id.type_about -> replaceContent(AboutFragment())
            R.id.type_feedback -> replaceContent(FeedbackFragment())
        }
        return true
    }

    private fun replaceContent(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.content_panel, fragment).commit()
    }

    private var backPressedCount = 0

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (backPressedCount == 0) {
                backPressedCount++
                Toast.makeText(this, R.string.press_back_again_to_exit, Toast.LENGTH_SHORT).show()
            } else {
                super.onBackPressed()
            }
        }
    }
}
