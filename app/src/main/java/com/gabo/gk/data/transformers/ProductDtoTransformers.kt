package com.gabo.gk.data.transformers

import com.gabo.gk.data.dto.ProductDto
import com.gabo.gk.data.local.entity.ProductEntity
import com.gabo.gk.domain.model.ProductModelDomain

fun ProductDto.toDomain() = ProductModelDomain(
    id, uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved
)

fun ProductModelDomain.toDto() = ProductDto(
    id, uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved
)

fun ProductModelDomain.toEntity() = ProductEntity(
    id = id,
    uid = uid,
    title = title,
    productCondition = productCondition,
    description = description,
    productType = productType,
    productCategory = productCategory,
    canBeSoldOnline = canBeSoldOnline,
    photos = photos,
    price = price,
    negotiablePrice = negotiablePrice,
    sellerName = sellerName,
    sellerPhoneNumber = sellerPhoneNumber,
    location = location,
    sold = sold,
    searchList = searchList,
    isSaved = isSaved
)

fun ProductEntity.toDomain() = ProductModelDomain(
    id, uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved
)