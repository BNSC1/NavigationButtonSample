package com.bn.navigationbuttonsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.bn.navigationbuttonsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
    }
    private val navController get() = navHostFragment.navController
    private var isRootFragment = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            setupToolbar()
        }
    }

    private fun ActivityMainBinding.setupToolbar() {
        setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(
            this@MainActivity,
            navController,
            layoutDrawer
        )
        toggle = ActionBarDrawerToggle(
            this@MainActivity, layoutDrawer,
            toolbar, R.string.action_open, R.string.action_close
        )
        toggle.syncState()
        layoutDrawer.addDrawerListener(toggle)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            isRootFragment = when (destination.id) {
                R.id.firstFragment -> true
                else -> false
            }
            updateDrawerNavigation()
        }
    }

    private fun ActivityMainBinding.updateDrawerNavigation() {
        supportActionBar?.let { actionBar ->
            if (isRootFragment) {
                actionBar.setDisplayHomeAsUpEnabled(false)
                toggle.syncState()
                toolbar.setNavigationOnClickListener {
                    layoutDrawer.openDrawer(
                        GravityCompat.START
                    )
                }
                layoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
            } else {
                layoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                actionBar.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener { onBackPressed() }
            }
        }
    }

}