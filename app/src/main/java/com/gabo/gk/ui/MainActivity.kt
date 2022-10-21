package com.gabo.gk.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.comon.util.DrawerMenu
import com.gabo.gk.comon.util.MenuListProvider.menuItemList
import com.gabo.gk.databinding.ActivityMainBinding
import com.gabo.gk.ui.adapters.NavDrawerAdapter
import com.gabo.gk.ui.home.user.home.HomeFragmentDirections
import com.gabo.gk.ui.model.navDrawer.MenuItemModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NavDrawerAdapter
    private lateinit var nav: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nav = findNavController(R.id.navHostFragment)

        setupAdapter()
        setupDrawerMenu()
        changeDrawerIcon()
        setupListeners()
    }

    private fun setupListeners() {
        val listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                changeDrawerIcon()
            }
        nav.addOnDestinationChangedListener(listener)
    }

    private fun setupDrawerMenu() {
        binding.ivDrawerMenu.setOnClickListener { binding.root.open() }
        binding.navLayout.ivArrowBack.setOnClickListener { binding.root.close() }
    }

    private fun setupAdapter() {
        adapter = NavDrawerAdapter(
            itemClick = { navigate(it) },
            profileClick = { openUserProfile() })
        with(binding) {
            navLayout.rvMenu.adapter = adapter
            navLayout.rvMenu.layoutManager = LinearLayoutManager(this@MainActivity)
        }
        adapter.submitList(menuItemList)

    }

    private fun openUserProfile() {
        if (nav.currentDestination == nav.findDestination(R.id.homeFragment)) {
            nav.navigate(HomeFragmentDirections.actionHomeFragmentToUserProfileFragment())
            changeDrawerIcon()
            binding.root.close()
        }
    }

    private fun changeDrawerIcon() {
        with(binding) {
            if (nav.currentDestination == nav.findDestination(R.id.homeFragment)) {
                ivDrawerMenu.visibility = View.VISIBLE
                ivDrawerMenu.setImageResource(R.drawable.ic_drawer_menu)
                ivDrawerMenu.setOnClickListener { binding.root.open() }
            } else {
//                ivDrawerMenu.setImageResource(R.drawable.ic_arrow_back)
//                ivDrawerMenu.setOnClickListener {
//                    nav.navigateUp()
//                    changeDrawerIcon()
//                }
                ivDrawerMenu.visibility = View.GONE
            }
        }
    }

    private fun navigate(model: MenuItemModel) {
        binding.root.close()
        if (nav.currentDestination == nav.findDestination(R.id.homeFragment)) {
            when (model.title) {
                DrawerMenu.Notifications.name -> nav.navigate(R.id.action_homeFragment_to_notificationsFragment)
                DrawerMenu.Messages.name -> nav.navigate(R.id.action_homeFragment_to_messagesFragment)
                DrawerMenu.Wallet.name -> nav.navigate(R.id.action_homeFragment_to_walletFragment)
                DrawerMenu.Categories.name -> nav.navigate(R.id.action_homeFragment_to_categoriesFragment)
                DrawerMenu.Saved.name -> nav.navigate(R.id.action_homeFragment_to_savedItemsFragment)
                DrawerMenu.Purchases.name -> nav.navigate(R.id.action_homeFragment_to_purchasesFragment)
                DrawerMenu.Selling.name -> nav.navigate(R.id.action_homeFragment_to_sellingFragment)
                DrawerMenu.Hints.name -> nav.navigate(R.id.action_homeFragment_to_hintsFragment)
                DrawerMenu.Settings.name -> nav.navigate(R.id.action_homeFragment_to_settingsFragment)
            }
            changeDrawerIcon()
        }
    }

}