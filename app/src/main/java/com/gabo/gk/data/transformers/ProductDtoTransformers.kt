package com.gabo.gk.data.transformers

import com.gabo.gk.data.global.dto.ProductDto
import com.gabo.gk.data.local.entity.ProductEntity
import com.gabo.gk.domain.model.ProductModelDomain

fun ProductDto.toDomain() = ProductModelDomain(
    id, uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved, purchasedBy
)

fun ProductModelDomain.toDto() = ProductDto(
    id, uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved, purchasedBy
)

fun ProductModelDomain.toEntity() = ProductEntity(
    id, uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved, purchasedBy
)

fun ProductEntity.toDomain() = ProductModelDomain(
    id, uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved, purchasedBy
)