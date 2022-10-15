package com.gabo.gk.base

interface BaseUseCase<Params, Result> {
    suspend operator fun invoke(params: Params): Result
}