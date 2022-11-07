package com.gabo.gk.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gabo.gk.comon.extensions.loadImage
import com.gabo.gk.databinding.DividerViewBinding
import com.gabo.gk.databinding.NavMenuItemBinding
import com.gabo.gk.databinding.UserProfileViewBinding
import com.gabo.gk.ui.model.navDrawer.DrawerMenuViewType
import com.gabo.gk.ui.model.navDrawer.MenuItemModel
import com.gabo.gk.ui.model.navDrawer.NavDrawerModel
import com.gabo.gk.ui.model.navDrawer.UserProfileModel

class NavDrawerAdapter(
    private val itemClick: (MenuItemModel) -> Unit,
    private val profileClick: (UserProfileModel) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<NavDrawerModel> = emptyList()

    fun submitList(list: List<NavDrawerModel>) {
        this.list = list
    }

    inner class ProfileVH(private val binding: UserProfileViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: UserProfileModel, profileClick: (UserProfileModel) -> Unit) {
            with(binding) {
                itemView.setOnClickListener { profileClick(model) }
                model.icon?.let { ivIcon.loadImage(model.icon) }
                tvUserName.text = model.userName
            }
        }
    }

    inner class DividerVH(binding: DividerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class MenuVH(private val binding: NavMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MenuItemModel, itemClick: (MenuItemModel) -> Unit) {
            with(binding) {
                itemView.setOnClickListener { itemClick(model) }
                model.icon?.let { ivIcon.loadImage(model.icon) }
                tvTitle.text = model.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            USER_PROFILE -> {
                ProfileVH(
                    UserProfileViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }
            DIVIDER -> {
                DividerVH(
                    DividerViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                MenuVH(
                    NavMenuItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            USER_PROFILE -> {
                (holder as ProfileVH).bind(list[position].userProfile!!, profileClick)
            }
            DIVIDER -> {
                (holder as DividerVH)
            }
            MENU_ITEM -> (holder as MenuVH).bind(list[position].menuItem!!, itemClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position].viewType) {
            DrawerMenuViewType.User -> USER_PROFILE
            DrawerMenuViewType.Divider -> DIVIDER
            else -> MENU_ITEM
        }
    }

    override fun getItemCount() = list.size

    companion object {
        const val USER_PROFILE = 1
        const val DIVIDER = 2
        const val MENU_ITEM = 3
    }
}