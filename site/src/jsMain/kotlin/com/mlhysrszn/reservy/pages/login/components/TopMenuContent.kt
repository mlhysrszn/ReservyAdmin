package com.mlhysrszn.reservy.pages.login.components

import androidx.compose.runtime.Composable
import com.mlhysrszn.reservy.getSitePalette
import com.mlhysrszn.reservy.pages.login.LoginRegisterState
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.px

@Composable
fun TopMenuContent(
    state: LoginRegisterState,
    currentState: LoginRegisterState,
    onStateChange: (LoginRegisterState) -> Unit,
) {
    SpanText(
        modifier = Modifier
            .fillMaxWidth()
            .textAlign(TextAlign.Center)
            .padding(topBottom = 20.px)
            .fontSize(22.px)
            .fontWeight(FontWeight.Bold)
            .color(if (currentState == state) getSitePalette().white else getSitePalette().blue)
            .backgroundColor(if (currentState == state) getSitePalette().blue else getSitePalette().white)
            .transition(CSSTransition(property = "background-color", duration = 400.ms))
            .cursor(Cursor.Pointer)
            .onClick {
                onStateChange(state)
            },
        text = state.text
    )
}