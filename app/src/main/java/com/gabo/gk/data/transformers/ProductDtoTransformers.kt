package com.gabo.gk.data.transformers

import com.gabo.gk.data.dto.ProductDto
import com.gabo.gk.domain.model.ProductModelDomain

fun ProductDto.toDomain() = ProductModelDomain(
    uid,
    title,
    productCondition,
    description,
    productType,
    productCategory,
    canBeSoldOnline,
    photos,
    price,
    negotiablePrice,
    sellerName,
    sellerPhoneNumber,
    location
)

fun ProductModelDomain.toDto() = ProductDto(
    uid,
    title,
    productCondition,
    description,
    productType,
    productCategory,
    canBeSoldOnline,
    photos,
    price,
    negotiablePrice,
    sellerName,
    sellerPhoneNumber,
    location
)