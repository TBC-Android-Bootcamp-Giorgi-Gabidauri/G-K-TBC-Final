package com.gabo.gk.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.gk.R
import com.gabo.gk.comon.extensions.launchStarted
import com.gabo.gk.comon.extensions.snackBar
import com.gabo.gk.comon.util.DrawerMenu
import com.gabo.gk.comon.util.MenuListProvider.menuItemList
import com.gabo.gk.databinding.ActivityMainBinding
import com.gabo.gk.ui.adapters.NavDrawerAdapter
import com.gabo.gk.ui.model.navDrawer.MenuItemModel
import com.gabo.gk.ui.model.user.UserModelUi
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NavDrawerAdapter
    private lateinit var nav: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nav = findNavController(R.id.navHostFragment)
        setupAdapter()
        setupDrawerMenu()
        changeDrawerIcon()
        setupListeners()
        setupObservers()
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
        nav.navigate(R.id.userProfileFragment)
        changeDrawerIcon()
        binding.root.close()
    }

    private fun changeDrawerIcon() {
        with(binding) {
            when (nav.currentDestination) {
                nav.findDestination(R.id.categoriesFragment),
                nav.findDestination(R.id.addSellingProductFragment),
                nav.findDestination(R.id.loginFragment),
                nav.findDestination(R.id.registerFragment),
                nav.findDestination(R.id.productDetailsFragment),
                nav.findDestination(R.id.splashScreenFragment),
                nav.findDestination(R.id.walletFragment),
                nav.findDestination(R.id.fillBalanceFragment),
                nav.findDestination(R.id.editProfileFragment) -> {
                    ivDrawerMenu.visibility = View.GONE
                    root.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else -> {
                    ivDrawerMenu.visibility = View.VISIBLE
                    ivDrawerMenu.setImageResource(R.drawable.ic_drawer_menu)
                    ivDrawerMenu.setOnClickListener { binding.root.open() }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (nav.currentDestination == nav.findDestination(R.id.homeFragment)) {
            finish()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    private fun navigate(model: MenuItemModel) {
        binding.root.close()
        nav.popBackStack(R.id.homeFragment, false)
        when (model.title) {
            DrawerMenu.Home.name -> nav.navigate(R.id.homeFragment)
            DrawerMenu.Notifications.name -> nav.navigate(R.id.notificationsFragment)
            DrawerMenu.Messages.name -> nav.navigate(R.id.messagesFragment)
            DrawerMenu.Wallet.name -> nav.navigate(R.id.walletFragment)
            DrawerMenu.Categories.name -> nav.navigate(R.id.categoriesFragment)
            DrawerMenu.Saved.name -> nav.navigate(R.id.savedItemsFragment)
            DrawerMenu.Purchases.name -> nav.navigate(R.id.purchasesFragment)
            DrawerMenu.Selling.name -> nav.navigate(R.id.sellingFragment)
            DrawerMenu.Hints.name -> nav.navigate(R.id.hintsFragment)
            DrawerMenu.Settings.name -> nav.navigate(R.id.settingsFragment)
        }
        changeDrawerIcon()
    }

    private fun setInfo(user: UserModelUi) {
        val list = menuItemList.toMutableList()
        list[0].userProfile =
            list[0].userProfile?.copy(icon = user.profileImg, userName = user.userName)
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }

    private fun setupObservers() {
        launchStarted {
            viewModel.defaultState.collect {
                with(binding) {
                    when {
                        it.data != null ->  setInfo(it.data)
                        it.msg.isNotEmpty() -> root.snackBar(it.msg)
                    }
                }
            }
        }
    }
}