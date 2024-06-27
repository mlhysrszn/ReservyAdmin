package com.mlhysrszn.reservy.pages.panel

import androidx.compose.runtime.*
import com.mlhysrszn.reservy.ShadowedBlueVariant
import com.mlhysrszn.reservy.common.Id
import com.mlhysrszn.reservy.common.noBorder
import com.mlhysrszn.reservy.components.AdminPageLayout
import com.mlhysrszn.reservy.components.widgets.ButtonContent
import com.mlhysrszn.reservy.components.widgets.InputContent
import com.mlhysrszn.reservy.data.getBusiness
import com.mlhysrszn.reservy.data.model.ReservationType
import com.mlhysrszn.reservy.data.model.ReservationTypeRequest
import com.mlhysrszn.reservy.data.model.WorkingHourRequest
import com.mlhysrszn.reservy.data.updateBusiness
import com.mlhysrszn.reservy.getSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.px

data class UpdateBusinessUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val name: String = "",
    val address: String = "",
    val city: String = "",
    val country: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val reservationTypes: List<ReservationType> = listOf(),
    var workingHours: Map<String, Pair<String, String>> = emptyMap()
)

@Page("/update-business")
@Composable
fun UpdateBusinessScreen() {
    document.title = "Reservy - Update Business"
    val businessId = localStorage.getItem("businessId")?.toIntOrNull() ?: 0

    var state by remember { mutableStateOf(UpdateBusinessUIState()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        state = state.copy(isLoading = true)
        getBusiness(
            id = businessId,
            onSuccess = { business ->
                state = UpdateBusinessUIState(
                    isLoading = false,
                    name = business!!.name,
                    address = business.address,
                    city = business.city,
                    country = business.country,
                    phoneNumber = business.phoneNumber,
                    email = business.email,
                    reservationTypes = business.reservationTypes,
                    workingHours = business.workingHours.associate { it.dayName to (it.openHour to it.closeHour) }
                )
                println("Business: $business")
            },
            onError = { error ->
                println(error)
                state = state.copy(isError = true, isLoading = false)
            }
        )
        state = state.copy(isLoading = false)
    }

    if (state.isLoading) {
        SpanText(text = "Loading...")
        return
    }

    AdminPageLayout(hasBusiness = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(left = 340.px, top = 100.px)
        ) {
            InputContent(
                id = Id.NAME_INPUT,
                placeholder = "Name",
                text = state.name,
                onValueChange = {
                    println(it)
                    state = state.copy(name = it)
                }
            )

            InputContent(
                id = Id.ADDRESS_INPUT,
                placeholder = "Address",
                text = state.address,
                onValueChange = { state = state.copy(address = it) }
            )

            InputContent(
                id = Id.CITY_INPUT,
                placeholder = "City",
                text = state.city,
                onValueChange = { state = state.copy(city = it) }
            )

            InputContent(
                id = Id.COUNTRY_INPUT,
                placeholder = "Country",
                text = state.country,
                onValueChange = { state = state.copy(country = it) }
            )

            InputContent(
                id = Id.PHONE_NUMBER_INPUT,
                placeholder = "Phone Number",
                text = state.phoneNumber,
                onValueChange = { state = state.copy(phoneNumber = it) }
            )

            InputContent(
                id = Id.EMAIL_INPUT,
                placeholder = "E-Mail",
                text = state.email,
                onValueChange = { state = state.copy(email = it) }
            )

            Column {
                SpanText(
                    modifier = Modifier
                        .color(getSitePalette().blue)
                        .fontSize(22.px)
                        .fontWeight(FontWeight.Bold)
                        .margin(leftRight = 10.px, top = 24.px)
                        .alignContent(AlignContent.Center),
                    text = "Working Hours",
                )

                WorkingHourContent(day = "Monday", state)
                WorkingHourContent(day = "Tuesday", state)
                WorkingHourContent(day = "Wednesday", state)
                WorkingHourContent(day = "Thursday", state)
                WorkingHourContent(day = "Friday", state)
                WorkingHourContent(day = "Saturday", state)
                WorkingHourContent(day = "Sunday", state)
            }

            ReservationAreaContent(
                reservationTypes = state.reservationTypes,
                onPlusClick = {
                    state = state.copy(
                        reservationTypes = state.reservationTypes.toMutableList().apply {
                            add(ReservationType())
                        }
                    )
                }
            )


            ButtonContent(
                text = "Update Business",
                onClick = {
                    scope.launch {
                        updateBusiness(
                            id = businessId,
                            name = state.name,
                            address = state.address,
                            city = state.city,
                            country = state.country,
                            phoneNumber = state.phoneNumber,
                            email = state.email,
                            workingHours = state.workingHours.map { (day, hours) ->
                                WorkingHourRequest(day, hours.first, hours.second)
                            },
                            reservationTypes = state.reservationTypes.map {
                                ReservationTypeRequest(it.name, it.timePeriod, it.price)
                            },
                            onSuccess = {
                                window.alert("Business updated successfully")
                            },
                            onError = { error ->
                                window.alert("Error updating business: $error")
                            }
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun WorkingHourContent(
    day: String,
    state: UpdateBusinessUIState
) {
    val workingHours = state.workingHours[day] ?: Pair("", "")
    println(workingHours)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SpanText(
            modifier = Modifier
                .id(day)
                .color(getSitePalette().blue)
                .margin(leftRight = 10.px)
                .alignContent(AlignContent.Center),
            text = day,
        )

        Row {
            InputContent(
                id = day.plus("-StartTime"),
                placeholder = "Start Time (09:00)",
                text = workingHours.first,
                onValueChange = { newValue ->
                    state.workingHours = state.workingHours.toMutableMap().apply {
                        this[day] = Pair(newValue, workingHours.second)
                    }
                }
            )

            InputContent(
                id = day.plus("-EndTime"),
                placeholder = "End Time (18:00)",
                text = workingHours.second,
                onValueChange = { newValue ->
                    state.workingHours = state.workingHours.toMutableMap().apply {
                        this[day] = Pair(workingHours.first, newValue)
                    }
                }
            )
        }
    }
}

@Composable
private fun ReservationAreaContent(
    reservationTypes: List<ReservationType>,
    onPlusClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth().margin(bottom = 24.px)
    ) {
        SpanText(
            modifier = Modifier
                .color(getSitePalette().blue)
                .weight(1)
                .fontSize(22.px)
                .fontWeight(FontWeight.Bold)
                .margin(leftRight = 10.px, top = 24.px)
                .alignContent(AlignContent.Center),
            text = "Reservation Types",
        )

        Button(
            modifier = ShadowedBlueVariant.toModifier()
                .backgroundColor(getSitePalette().blue)
                .color(getSitePalette().white)
                .borderRadius(r = 40.px)
                .fontSize(14.px)
                .noBorder()
                .cursor(Cursor.Pointer),
            onClick = {
                onPlusClick()
            }
        ) {
            SpanText(
                text = "+ Add Reservation Type",
                modifier = Modifier
                    .fontSize(20.px)
                    .color(getSitePalette().white)
            )
        }

        reservationTypes.forEachIndexed { index, type ->
            Row(
                modifier = Modifier.thenIf(
                    condition = index == 0,
                    other = Modifier.margin(top = 24.px)
                )
            ) {
                InputContent(
                    id = "ReservationType-${index}",
                    placeholder = type.name.ifEmpty { "Name" },
                    text = type.name,
                    onValueChange = { newValue ->
                        type.name = newValue
                    }
                )

                InputContent(
                    id = "ReservationType-${index}-TimePeriod",
                    placeholder = type.timePeriod.toString().ifEmpty { "Time Period" },
                    text = type.timePeriod.toString(),
                    onValueChange = { newValue ->
                        type.timePeriod = newValue.toInt()
                    }
                )

                InputContent(
                    id = "ReservationType-${index}-Price",
                    placeholder = type.price.toString().ifEmpty { "Price" },
                    text = type.price.toString(),
                    onValueChange = { newValue ->
                        type.price = newValue.toDouble()
                    }
                )
            }
        }
    }
}
