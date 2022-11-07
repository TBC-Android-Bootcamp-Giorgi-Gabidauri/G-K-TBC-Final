package com.gabo.gk.ui.modelTransformers

import com.gabo.gk.domain.model.CurrentUserSortModelDomain
import com.gabo.gk.ui.model.sort.CurrentUserSortModelUi

fun CurrentUserSortModelDomain.toUi() = CurrentUserSortModelUi(
    uid, field, equalsTo
)

fun CurrentUserSortModelUi.toDomain() = CurrentUserSortModelDomain(
    uid, field, equalsTo
)