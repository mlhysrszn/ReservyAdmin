package com.mlhysrszn.reservy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WorkingHourRequest(
    val dayName: String,
    val openHour: String,
    val closeHour: String,
)