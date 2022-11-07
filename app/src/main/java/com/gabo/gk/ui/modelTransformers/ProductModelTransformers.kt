package com.gabo.gk.ui.modelTransformers

import com.gabo.gk.domain.model.ProductModelDomain
import com.gabo.gk.domain.model.TransactionModelDomain
import com.gabo.gk.ui.model.product.ProductModelUi
import com.gabo.gk.ui.model.transaction.TransactionModelUi

fun ProductModelUi.toDomain() = ProductModelDomain(
    id, uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved, purchasedBy
)

fun ProductModelDomain.toUi() = ProductModelUi(
    id, uid, title, productCondition, description, productType, productCategory, canBeSoldOnline,
    photos, price, negotiablePrice, sellerName, sellerPhoneNumber, location, sold, searchList,
    isSaved, purchasedBy
)

fun ProductModelDomain.toTransactionDomain() =
    TransactionModelDomain(title, price, sold, purchasedBy, photos)

fun ProductModelUi.toTransactionUi() = TransactionModelUi(title, price, sold, purchasedBy, photos)