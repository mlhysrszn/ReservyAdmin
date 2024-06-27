package com.mlhysrszn.reservy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Business(
    val id: Int,
    val name: String,
    val address: String,
    val city: String,
    val country: String,
    val phoneNumber: String,
    val email: String,
    val category: String,
    val workingHours: List<WorkingHour>,
    val reservationTypes: List<ReservationType>,
)