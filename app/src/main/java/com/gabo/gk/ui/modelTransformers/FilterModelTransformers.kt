package com.gabo.gk.ui.modelTransformers

import com.gabo.gk.domain.model.FilterModelDomain
import com.gabo.gk.ui.model.filter.FilterModelUi

fun FilterModelDomain.toUi() = FilterModelUi(
    showFirst,
    type,
    status,
    anyPrice,
    negotiablePrice,
    priceFrom,
    priceTill,
    location,
    categoryField,
    categoryEqualsTo
)

fun FilterModelUi.toDomain() = FilterModelDomain(
    showFirst,
    type,
    status,
    anyPrice,
    negotiablePrice,
    priceFrom,
    priceTill,
    location,
    categoryField,
    categoryEqualsTo
)