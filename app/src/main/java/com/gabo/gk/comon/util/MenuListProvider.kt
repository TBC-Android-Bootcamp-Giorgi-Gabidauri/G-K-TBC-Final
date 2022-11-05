package com.gabo.gk.comon.util

import com.gabo.gk.R
import com.gabo.gk.ui.model.navDrawer.DrawerMenuViewType
import com.gabo.gk.ui.model.navDrawer.MenuItemModel
import com.gabo.gk.ui.model.navDrawer.NavDrawerModel

object MenuListProvider {
    val menuItemList = listOf(
        NavDrawerModel(DrawerMenuViewType.User),
        NavDrawerModel(DrawerMenuViewType.Divider),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Home.name, icon = R.drawable.ic_home)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Notifications.name,icon = R.drawable.ic_notifications)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Messages.name,icon = R.drawable.ic_email)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Wallet.name,icon = R.drawable.ic_wallet)),
        NavDrawerModel(DrawerMenuViewType.Divider),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Categories.name,icon = R.drawable.ic_grid_view)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Saved.name,icon = R.drawable.ic_heart_filled)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Purchases.name,icon = R.drawable.ic_cart)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Selling.name,icon = R.drawable.ic_sell)),
        NavDrawerModel(DrawerMenuViewType.Divider),
        NavDrawerModel(
            DrawerMenuViewType.MenuItem,
            menuItem = MenuItemModel(title = DrawerMenu.Hints.name)
        ),
        NavDrawerModel(
            DrawerMenuViewType.MenuItem,
            menuItem = MenuItemModel(title = DrawerMenu.Settings.name)
        ),
    )
}
enum class DrawerMenu{
    Home,Notifications,Messages,Wallet,Categories,Saved,Purchases,Selling,Hints,Settings
}
//todo