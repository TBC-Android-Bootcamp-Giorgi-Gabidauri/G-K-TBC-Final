package com.gabo.gk.comon.util

import com.gabo.gk.ui.model.navDrawer.DrawerMenuViewType
import com.gabo.gk.ui.model.navDrawer.MenuItemModel
import com.gabo.gk.ui.model.navDrawer.NavDrawerModel

object MenuListProvider {
    val menuItemList = listOf(
        NavDrawerModel(DrawerMenuViewType.User),
        NavDrawerModel(DrawerMenuViewType.Divider),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Home.name)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Notifications.name)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Messages.name)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Wallet.name)),
        NavDrawerModel(DrawerMenuViewType.Divider),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Categories.name)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Saved.name)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Purchases.name)),
        NavDrawerModel(DrawerMenuViewType.MenuItem, menuItem = MenuItemModel(title = DrawerMenu.Selling.name)),
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