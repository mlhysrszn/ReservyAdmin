package com.mlhysrszn.reservy.pages.panel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.mlhysrszn.reservy.ShadowedBlueVariant
import com.mlhysrszn.reservy.common.Id
import com.mlhysrszn.reservy.common.noBorder
import com.mlhysrszn.reservy.components.AdminPageLayout
import com.mlhysrszn.reservy.components.widgets.ButtonContent
import com.mlhysrszn.reservy.components.widgets.InputContent
import com.mlhysrszn.reservy.data.createBusiness
import com.mlhysrszn.reservy.data.model.ReservationType
import com.mlhysrszn.reservy.data.model.ReservationTypeRequest
import com.mlhysrszn.reservy.data.model.WorkingHourRequest
import com.mlhysrszn.reservy.getSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.alignContent
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.id
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.AlignContent
import org.jetbrains.compose.web.css.px
import kotlinx.browser.window

data class CreateBusinessUIState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val reservationTypes: List<ReservationType> = listOf(),
)

@Page("/create-business")
@Composable
fun CreateBusinessScreen() {
    document.title = "Reservy - Create Business"

    var state by remember { mutableStateOf(CreateBusinessUIState()) }

    val scope = rememberCoroutineScope()

    AdminPageLayout {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(left = 340.px, top = 100.px, bottom = 100.px)
        ) {
            InputContent(
                id = Id.NAME_INPUT,
                placeholder = "Name"
            )

            InputContent(
                id = Id.ADDRESS_INPUT,
                placeholder = "Address"
            )

            InputContent(
                id = Id.CITY_INPUT,
                placeholder = "City"
            )

            InputContent(
                id = Id.COUNTRY_INPUT,
                placeholder = "Country"
            )

            InputContent(
                id = Id.PHONE_NUMBER_INPUT,
                placeholder = "Phone Number"
            )

            InputContent(
                id = Id.EMAIL_INPUT,
                placeholder = "E-Mail"
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

                WorkingHourContent(day = "Monday")
                WorkingHourContent(day = "Tuesday")
                WorkingHourContent(day = "Wednesday")
                WorkingHourContent(day = "Thursday")
                WorkingHourContent(day = "Friday")
                WorkingHourContent(day = "Saturday")
                WorkingHourContent(day = "Sunday")
            }

            ReservationAreaContent(
                reservationTypes = state.reservationTypes,
                onPlusClick = {
                    state = state.copy(
                        reservationTypes = state.reservationTypes.toMutableList().apply {
                            add(ReservationType())
                        }
                    )
                },
                onMinusClick = {index ->
                    state = state.copy(
                        reservationTypes = state.reservationTypes.toMutableList().apply {
                            removeAt(index)
                        }
                    )
                }
            )

            ButtonContent(
                text = "Create Business",
                onClick = {
                    val name = document.getElementById(Id.NAME_INPUT)?.asDynamic().value as String
                    val address = document.getElementById(Id.ADDRESS_INPUT)?.asDynamic().value as String
                    val city = document.getElementById(Id.CITY_INPUT)?.asDynamic().value as String
                    val country = document.getElementById(Id.COUNTRY_INPUT)?.asDynamic().value as String
                    val phoneNumber = document.getElementById(Id.PHONE_NUMBER_INPUT)?.asDynamic().value as String
                    val email = document.getElementById(Id.EMAIL_INPUT)?.asDynamic().value as String

                    val workingHours = listOf(
                        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
                    ).map { day ->
                        val openHour = document.getElementById(day.plus("-StartTime"))?.asDynamic().value as String
                        val closeHour = document.getElementById(day.plus("-EndTime"))?.asDynamic().value as String
                        WorkingHourRequest(
                            dayName = day.lowercase(),
                            openHour = openHour,
                            closeHour = closeHour,
                        )
                    }.filter { workingHour ->
                        workingHour.openHour.isNotEmpty() && workingHour.closeHour.isNotEmpty()
                    }

                    val reservationTypes = List(state.reservationTypes.size) { index ->
                        val name = document.getElementById("ReservationType-${index}")?.asDynamic().value as String
                        val timePeriod = document.getElementById("ReservationType-${index}-TimePeriod")?.asDynamic().value as String
                        val price = document.getElementById("ReservationType-${index}-Price")?.asDynamic().value as String
                        ReservationTypeRequest(
                            name = name,
                            timePeriod = timePeriod.toInt(),
                            price = price.toDouble(),
                        )
                    }

                    if (name.isEmpty() || address.isEmpty() || city.isEmpty() || country.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || reservationTypes.isEmpty() || reservationTypes.any { it.name.isEmpty() || it.timePeriod.toString().isEmpty() || it.price.toString().isEmpty() }) {
                        window.alert("Please fill in all the fields.")
                    } else {
                        scope.launch {
                            createBusiness(
                                name = name,
                                address = address,
                                city = city,
                                country = country,
                                phoneNumber = phoneNumber,
                                email = email,
                                workingHours = workingHours,
                                reservationTypes = reservationTypes,
                                onSuccess = {
                                    println("Success!")
                                    window.alert("Business created successfully.")
                                },
                                onError = {
                                    println("Error: $it")
                                    window.alert(it)
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun WorkingHourContent(
    day: String,
) {
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
                placeholder = "Start Time (09:00)"
            )

            InputContent(
                id = day.plus("-EndTime"),
                placeholder = "End Time (18:00)"
            )
        }
    }
}

@Composable
private fun ReservationAreaContent(
    reservationTypes: List<ReservationType>,
    onPlusClick: () -> Unit = {},
    onMinusClick: (Int) -> Unit = {},
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
                )

                InputContent(
                    id = "ReservationType-${index}-TimePeriod",
                    placeholder = if (type.timePeriod == 0) "Time Period" else type.timePeriod.toString(),
                )

                InputContent(
                    id = "ReservationType-${index}-Price",
                    placeholder = if (type.price == 0.0) "Price" else type.price.toString(),
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
                        onMinusClick(index)
                    }
                ) {
                    SpanText(
                        text = "-",
                        modifier = Modifier
                            .fontSize(20.px)
                            .color(getSitePalette().white)
                    )
                }
            }
        }
    }
}
