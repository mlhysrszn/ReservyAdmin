package com.mlhysrszn.reservy.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BusinessRequest(
    val name: String,
    val address: String,
    val city: String,
    val country: String,
    val phoneNumber: String,
    val email: String,
    val workingHours: List<WorkingHourRequest>,
    val reservationTypes: List<ReservationTypeRequest>,
)