package com.gabo.gk.ui.modelTransformers

import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.ui.model.product.ProductModelUi

fun ProductModelUi.toDomain() = ProductModelDomain(
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
    location,
    sold,
    searchList
)
fun ProductModelDomain.toUi() = ProductModelUi(
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
    location,
    sold,
    searchList
)