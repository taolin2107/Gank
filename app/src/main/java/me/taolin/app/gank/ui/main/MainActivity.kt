package me.taolin.app.gank.ui.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import me.taolin.app.gank.R
import me.taolin.app.gank.base.BaseActivity

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        title = item.title
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.type_today -> { }
            R.id.type_android -> { }
            R.id.type_ios -> {}
            R.id.type_front_end -> {}
            R.id.type_beauty -> {}
            R.id.type_relax_video -> {}
            R.id.type_about -> {}
            R.id.type_feedback -> {}
        }
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
