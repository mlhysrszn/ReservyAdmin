package com.mlhysrszn.reservy

import com.varabyte.kobweb.compose.css.AnimationIterationCount
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.animation
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontFamily
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.silk.components.animation.Keyframes
import com.varabyte.kobweb.silk.components.animation.toAnimation
import com.varabyte.kobweb.silk.components.layout.HorizontalDividerStyle
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.base
import com.varabyte.kobweb.silk.components.style.focus
import com.varabyte.kobweb.silk.components.style.hover
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.theme.modifyComponentStyleBase
import org.jetbrains.compose.web.css.AnimationTimingFunction
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.ms
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) {
    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily(
                "PT Sans",
            )
            .fontSize(18.px)
            .lineHeight(1.5)
    }

    // Silk dividers only extend 90% by default; we want full width dividers in our site
    ctx.theme.modifyComponentStyleBase(HorizontalDividerStyle) {
        Modifier.fillMaxWidth()
    }
}

val LoginInputStyle by ComponentStyle {
    base {
        Modifier
            .transition(CSSTransition(property = "border"))
    }
    focus {
        Modifier.border(
            width = 3.px,
            style = LineStyle.Solid,
            color = getSitePalette().blue
        )
    }
}

val ShadowedBlueVariant by ComponentStyle {
    hover {
        Modifier
            .boxShadow(0.px, 0.px, 20.px, 0.px, getSitePalette().blue)
            .transition(CSSTransition(property = "box-shadow", duration = 400.ms))
    }
}

val LoaderStyle by ComponentStyle.base {
    Modifier
        .border(10.px, LineStyle.Solid, getSitePalette().blue)
        .borderTop(10.px, LineStyle.Solid, getSitePalette().white)
        .borderRadius(50.percent)
        .animation(
            Spin.toAnimation(
                colorMode,
                duration = 1.s,
                timingFunction = AnimationTimingFunction.Linear,
                iterationCount = AnimationIterationCount.Infinite
            )
        )
        .size(72.px)
}

val Spin by Keyframes {
    0.percent {
        Modifier
            .transform { rotate(0.deg) }
    }
    100.percent {
        Modifier
            .transform { rotate(360.deg) }
    }
}