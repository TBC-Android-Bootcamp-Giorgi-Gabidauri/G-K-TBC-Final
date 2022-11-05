package com.gabo.gk.ui.model.navDrawer


data class NavDrawerModel(
    val viewType: DrawerMenuViewType,
    val menuItem: MenuItemModel? = null,
    var userProfile: UserProfileModel? = UserProfileModel()
)
