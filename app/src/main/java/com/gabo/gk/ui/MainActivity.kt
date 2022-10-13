package com.gabo.gk.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.databinding.ActivityMainBinding
import com.gabo.gk.ui.adapters.NavDrawerAdapter
import com.gabo.gk.ui.model.navDrawer.MenuItemModel
import com.gabo.gk.ui.model.navDrawer.NavDrawerModel
import com.gabo.gk.ui.model.navDrawer.ViewType

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var adapter: NavDrawerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()

        toggle = ActionBarDrawerToggle(this, binding.root, R.string.open, R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupAdapter() {
        adapter = NavDrawerAdapter { Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show() }
        with(binding) {
            navLayout.root.adapter = adapter
            navLayout.root.layoutManager = LinearLayoutManager(this@MainActivity)
        }
        val menuItemList = listOf(
            NavDrawerModel(ViewType.User),
            NavDrawerModel(ViewType.Divider),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "1 Item")),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "2 Item")),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "3 Item")),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "4 Item")),
            NavDrawerModel(ViewType.Divider),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "6 Item")),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "7 Item")),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "8 Item")),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "9 Item")),
            NavDrawerModel(ViewType.Divider),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "10 Item")),
            NavDrawerModel(ViewType.MenuItem, menuItem = MenuItemModel(title = "11 Item")),
        )

        adapter.submitList(menuItemList)
    }
}