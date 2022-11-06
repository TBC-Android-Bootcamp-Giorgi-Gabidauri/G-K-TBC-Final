package com.gabo.gk.domain.useCases.formatters

import android.text.Editable
import android.text.InputFilter
import javax.inject.Inject

class FormatCVCUseCase@Inject constructor() {
    var current = ""
    operator fun invoke(params: Editable) {
        if (params.toString() != current) {
            val userInput = params.toString().replace(Regex("[^\\d]"), "")
            if (userInput.length <= 3) {
                current = userInput.chunked(1).joinToString("")
                params.filters = arrayOfNulls<InputFilter>(0)
            }
            params.replace(0, params.length, current, 0, current.length)
        }
    }
}