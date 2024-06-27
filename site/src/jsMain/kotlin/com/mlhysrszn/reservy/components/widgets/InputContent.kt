package com.mlhysrszn.reservy.components.widgets

import androidx.compose.runtime.Composable
import com.mlhysrszn.reservy.LoginInputStyle
import com.mlhysrszn.reservy.getSitePalette
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Input
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.InputEvent

@Composable
fun InputContent(
    id: String = "",
    placeholder: String,
    text: String = "",
    onValueChange: (String) -> Unit = {}
) {
    Input(
        type = InputType.Text,
        attrs = LoginInputStyle.toModifier()
            .id(id)
            .margin(bottom = 24.px, left = 10.px)
            .width(350.px)
            .height(54.px)
            .padding(leftRight = 20.px)
            .backgroundColor(getSitePalette().white)
            .borderRadius(r = 40.px)
            .fontSize(16.px)
            .outline(
                width = 0.px,
                style = LineStyle.None,
                color = Colors.Transparent
            )
            .toAttrs {
                attr("value", text)
                attr("placeholder", placeholder)
                onInput {
                    val input = it.target as HTMLInputElement
                    onValueChange(input.value)
                }
            }
    )
}