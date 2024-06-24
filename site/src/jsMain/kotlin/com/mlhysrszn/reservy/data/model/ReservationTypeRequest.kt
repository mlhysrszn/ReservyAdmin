package com.mlhysrszn.reservy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReservationTypeRequest(
    val name: String,
    val timePeriod: Int,
    val price: Double,
)