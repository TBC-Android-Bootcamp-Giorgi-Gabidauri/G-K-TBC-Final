package com.gabo.gk.comon.helpers

import com.gabo.gk.domain.model.ProductModelDomain

interface HashModelHelper {
    fun modelToHashMap(productModel: ProductModelDomain): HashMap<String, Any?>
}