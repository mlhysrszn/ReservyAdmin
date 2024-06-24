package com.mlhysrszn.reservy.components.widgets

import androidx.compose.runtime.Composable
import com.mlhysrszn.reservy.ShadowedBlueVariant
import com.mlhysrszn.reservy.common.noBorder
import com.mlhysrszn.reservy.getSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.ButtonSize
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px

@Composable
fun ButtonContent(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = ShadowedBlueVariant.toModifier()
            .margin(top = 24.px)
            .width(350.px)
            .height(54.px)
            .backgroundColor(getSitePalette().blue)
            .color(getSitePalette().white)
            .borderRadius(r = 40.px)
            .fontSize(14.px)
            .noBorder()
            .cursor(Cursor.Pointer),
        size = ButtonSize.LG,
        onClick = {
            onClick()
        }
    ) {
        SpanText(
            text = text,
            modifier = Modifier
                .fontSize(20.px)
                .color(getSitePalette().white)
        )
    }
}