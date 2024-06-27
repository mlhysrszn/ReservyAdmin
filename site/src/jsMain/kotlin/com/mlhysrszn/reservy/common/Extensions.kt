package com.mlhysrszn.reservy.common

import com.mlhysrszn.reservy.data.model.User
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.outline
import kotlinx.browser.localStorage
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.w3c.dom.set

fun Modifier.noBorder(): Modifier {
    return this.border(
        width = 0.px,
        style = LineStyle.None,
        color = Colors.Transparent
    ).outline(
        width = 0.px,
        style = LineStyle.None,
        color = Colors.Transparent
    )
}

fun User.remember() {
    localStorage["remember"] = true.toString()
    localStorage["id"] = id.toString()
    localStorage["email"] = email
    localStorage["isAdmin"] = true.toString()
    localStorage["businessId"] = businessId.toString()
}