package com.gabo.gk.ui.modelTransformers

import com.gabo.gk.domain.model.TransactionModelDomain
import com.gabo.gk.ui.model.transaction.TransactionModelUi


fun TransactionModelUi.toDomain() = TransactionModelDomain(title, price, sold,purchasedBy,images)
fun TransactionModelDomain.toUi() = TransactionModelUi(title, price, sold,purchasedBy,images)
