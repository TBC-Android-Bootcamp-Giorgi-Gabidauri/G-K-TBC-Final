package com.gabo.gk.data.transformers

import com.gabo.gk.data.global.dto.UserDto
import com.gabo.gk.domain.model.UserModelDomain

fun UserDto.toDomain() =
    UserModelDomain(id, uid, email, password, type, userName, profileImg, contactNumber,
        availableAmount, searchList, soldProducts.map { it.toDomain() }.toMutableList(),
        purchasedProducts.map { it.toDomain() }.toMutableList(), token,
        savedProducts.map { it.toDomain() }.toMutableList(),notifications
    )

fun UserModelDomain.toDto() =
    UserDto(id, uid, email, password, type, userName, profileImg, contactNumber,
        availableAmount, searchList, soldProducts.map { it.toDto() }.toMutableList(),
        purchasedProducts.map { it.toDto() }.toMutableList(), token,
        savedProducts.map { it.toDto() }.toMutableList(),notifications
    )