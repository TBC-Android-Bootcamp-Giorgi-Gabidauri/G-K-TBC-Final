package com.gabo.gk.ui.model.navDrawer

enum class ViewType {
    MenuItem,
    User,
    Divider
}


data class NavDrawerModel(
    val viewType: ViewType,
    val menuItem: MenuItemModel? = null,
    val userProfile: UserProfileModel? = UserProfileModel()
)
