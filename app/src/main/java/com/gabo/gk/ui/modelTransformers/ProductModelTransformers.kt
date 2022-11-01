package com.gabo.gk.ui.modelTransformers

import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.ui.model.product.ProductModelUi

fun ProductModelUi.toDomain() = ProductModelDomain(
    id,uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved
)
fun ProductModelDomain.toUi() = ProductModelUi(
    id,uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved
)