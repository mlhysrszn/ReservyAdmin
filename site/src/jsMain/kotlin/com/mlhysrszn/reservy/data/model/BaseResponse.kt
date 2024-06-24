package com.mlhysrszn.reservy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: Int? = null,
    val message: String? = null,
    val data: T? = null
)