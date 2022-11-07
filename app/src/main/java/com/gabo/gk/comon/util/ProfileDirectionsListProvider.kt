package com.gabo.gk.comon.util

import com.gabo.gk.R
import com.gabo.gk.ui.model.profileDirection.ProfileDirectionModel

object ProfileDirectionsListProvider {
    val profileDirections = listOf(
        ProfileDirectionModel(img = R.drawable.ic_heart_filled,title = "Saved"),
        ProfileDirectionModel(img = R.drawable.ic_wallet,title = "Wallet"),
        ProfileDirectionModel(img = R.drawable.ic_cart,title = "Purchases",),
        ProfileDirectionModel(img = R.drawable.ic_sell,title = "Selling"),
        ProfileDirectionModel(img = R.drawable.ic_notifications,title = "Notifications"),
        ProfileDirectionModel(img = R.drawable.ic_grid_view,title = "Categories")
    )
}