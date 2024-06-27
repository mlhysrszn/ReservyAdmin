package com.mlhysrszn.reservy.components

import androidx.compose.runtime.Composable
import com.mlhysrszn.reservy.pages.panel.SidePanel
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize

@Composable
fun AdminPageLayout(hasBusiness: Boolean = false, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            SidePanel(hasBusiness)
            content()
        }
    }
}