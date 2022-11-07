package com.gabo.gk.ui.modelTransformers

import com.gabo.gk.domain.model.UserModelDomain
import com.gabo.gk.ui.model.user.UserModelUi

fun UserModelDomain.toUi() =
    UserModelUi(
        id, uid, email, password, type, userName, profileImg, contactNumber,
        availableAmount, searchList, soldProducts.map { it.toUi() }.toMutableList(),
        purchasedProducts.map { it.toUi() }.toMutableList(), token,
        savedProducts.map { it.toUi() }.toMutableList(), notifications
    )

fun UserModelUi.toDomain() =
    UserModelDomain(
        id, uid, email, password, type, userName, profileImg, contactNumber,
        availableAmount, searchList, soldProducts.map { it.toDomain() }.toMutableList(),
        purchasedProducts.map { it.toDomain() }.toMutableList(), token,
        savedProducts.map { it.toDomain() }.toMutableList(), notifications
    )