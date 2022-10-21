package com.gabo.gk.comon.helpers

import com.gabo.gk.domain.model.ProductModelDomain
import javax.inject.Inject

class HashModelHelperImpl @Inject constructor() : HashModelHelper {
    override fun modelToHashMap(productModel: ProductModelDomain): HashMap<String, Any?> {
        val productMap = HashMap<String, Any?>()
        val id = (0..999999999999999).random()
        with(productModel) {
            productMap["id"] = id
            productMap["title"] = title
            productMap["description"] = description
            productMap["productType"] = productType
            productMap["productCategory"] = productCategory
            productMap["canBeSoldOnline"] = canBeSoldOnline
            productMap["photos"] = photos
            productMap["price"] = price
            productMap["negotiablePrice"] = negotiablePrice
            productMap["sellerName"] = sellerName
            productMap["sellerPhoneNumber"] = sellerPhoneNumber
            productMap["location"] = location
        }
        return productMap
    }
}
