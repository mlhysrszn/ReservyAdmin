package com.mlhysrszn.reservy.data

import com.mlhysrszn.reservy.data.model.BusinessRequest
import com.mlhysrszn.reservy.data.model.LoginRequest
import com.mlhysrszn.reservy.data.model.RegisterRequest
import com.mlhysrszn.reservy.data.model.ReservationTypeRequest
import com.mlhysrszn.reservy.data.model.User
import com.mlhysrszn.reservy.data.model.WorkingHourRequest
import com.mlhysrszn.reservy.utils.ApiUtils.post
import com.mlhysrszn.reservy.utils.ApiUtils.safeApiCall
import kotlinx.browser.localStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend fun login(
    email: String,
    password: String,
    onSuccess: (User?) -> Unit = {},
    onError: (String) -> Unit = {}
) = safeApiCall<User>(
    call = {
        post(
            path = "login",
            body = Json.encodeToString(LoginRequest(email, password)).encodeToByteArray()
        )
    },
    onSuccess = onSuccess,
    onError = onError
)

suspend fun register(
    firstName: String,
    lastName: String,
    email: String,
    phoneNumber: String,
    password: String,
    onSuccess: (User?) -> Unit = {},
    onError: (String) -> Unit = {}
) = safeApiCall<User>(
    call = {
        post(
            path = "register",
            body = Json.encodeToString(
                RegisterRequest(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phoneNumber = phoneNumber,
                    password = password,
                    isAdmin = true
                )
            ).encodeToByteArray()
        )
    },
    onSuccess = onSuccess,
    onError = onError
)

suspend fun createBusiness(
    name: String,
    address: String,
    city: String,
    country: String,
    phoneNumber: String,
    email: String,
    workingHours: List<WorkingHourRequest>,
    reservationTypes: List<ReservationTypeRequest>,
    onSuccess: (Unit?) -> Unit = {},
    onError: (String) -> Unit = {}
) = safeApiCall(
    call = {
        post(
            path = "create_business",
            body = Json.encodeToString(
                BusinessRequest(
                    name = name,
                    address = address,
                    city = city,
                    country = country,
                    phoneNumber = phoneNumber,
                    email = email,
                    workingHours = workingHours,
                    reservationTypes = reservationTypes
                )
            ).encodeToByteArray()
        )
    },
    onSuccess = onSuccess,
    onError = onError
)

fun logout() {
    localStorage.clear()
}