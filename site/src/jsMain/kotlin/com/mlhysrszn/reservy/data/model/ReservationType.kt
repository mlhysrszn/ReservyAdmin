package com.mlhysrszn.reservy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReservationType(
    var name: String = "",
    var timePeriod: Int = 0,
    var price: Double = 0.0,
)
