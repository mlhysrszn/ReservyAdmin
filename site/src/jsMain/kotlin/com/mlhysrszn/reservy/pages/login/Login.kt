package com.mlhysrszn.reservy.pages.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.mlhysrszn.reservy.common.Id
import com.mlhysrszn.reservy.common.Res
import com.mlhysrszn.reservy.common.remember
import com.mlhysrszn.reservy.data.login
import com.mlhysrszn.reservy.data.register
import com.mlhysrszn.reservy.getSitePalette
import com.mlhysrszn.reservy.navigation.Screen
import com.mlhysrszn.reservy.components.widgets.ButtonContent
import com.mlhysrszn.reservy.components.widgets.InputContent
import com.mlhysrszn.reservy.pages.login.components.TopMenuContent
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.px
import org.w3c.dom.HTMLInputElement

enum class LoginRegisterState(val text: String) {
    Login("Login"),
    Register("Register")
}

@Page("/index")
@Composable
fun LoginScreen() {
    document.title = "Reservy - Login"

    val scope = rememberCoroutineScope()
    val context = rememberPageContext()

    var errorText by remember { mutableStateOf("") }
    var loginRegisterState by remember { mutableStateOf(LoginRegisterState.Login) }

    Column(
        modifier = Modifier
            .borderRadius(r = 8.px)
            .backgroundColor(getSitePalette().white)
            .boxShadow(0.px, 0.px, 14.px, 0.px, getSitePalette().blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .backgroundColor(getSitePalette().white)
                .borderRadius(r = 8.px),
            horizontalArrangement = Arrangement.Center
        ) {
            TopMenuContent(
                state = LoginRegisterState.Login,
                currentState = loginRegisterState,
                onStateChange = { loginRegisterState = it }
            )
            TopMenuContent(
                state = LoginRegisterState.Register,
                currentState = loginRegisterState,
                onStateChange = { loginRegisterState = it }
            )
        }

        Column(
            modifier = Modifier.padding(leftRight = 50.px, topBottom = 50.px),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .margin(top = 40.px)
                    .width(300.px),
                src = Res.Image.LOGO,
                alt = "Logo Image"
            )

            if (loginRegisterState == LoginRegisterState.Login) {
                LoginContent(
                    onLoginClick = {
                        errorText = ""
                        scope.launch {
                            val email = (document.getElementById(Id.EMAIL_INPUT) as HTMLInputElement).value
                            val password = (document.getElementById(Id.PASSWORD_INPUT) as HTMLInputElement).value
                            login(
                                email = email,
                                password = password,
                                onSuccess = {
                                    it?.remember()
                                    context.router.navigateTo(Screen.AdminCreateBusiness.route)
                                },
                                onError = { errorText = it }
                            )
                        }
                    }
                )
            } else {
                RegisterContent(
                    onRegisterClick = {
                        errorText = ""
                        scope.launch {
                            val firstName = (document.getElementById(Id.FIRST_NAME_INPUT) as HTMLInputElement).value
                            val lastName = (document.getElementById(Id.LAST_NAME_INPUT) as HTMLInputElement).value
                            val email = (document.getElementById(Id.EMAIL_INPUT) as HTMLInputElement).value
                            val phoneNumber = (document.getElementById(Id.PHONE_NUMBER_INPUT) as HTMLInputElement).value
                            val password = (document.getElementById(Id.PASSWORD_INPUT) as HTMLInputElement).value
                            register(
                                firstName = firstName,
                                lastName = lastName,
                                email = email,
                                phoneNumber = phoneNumber,
                                password = password,
                                onSuccess = {
                                    it?.remember()
                                    context.router.navigateTo(Screen.AdminCreateBusiness.route)
                                },
                                onError = { errorText = it }
                            )
                        }
                    }
                )
            }

            SpanText(
                modifier = Modifier
                    .width(350.px)
                    .color(Colors.Red)
                    .textAlign(TextAlign.Center),
                text = errorText
            )
        }
    }
}

@Composable
fun LoginContent(
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(leftRight = 100.px, topBottom = 50.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputContent(
            id = Id.EMAIL_INPUT,
            placeholder = "E-Mail"
        )

        InputContent(
            id = Id.PASSWORD_INPUT,
            placeholder = "Password"
        )

        ButtonContent(
            text = "Login",
            onClick = onLoginClick
        )
    }
}

@Composable
fun RegisterContent(
    onRegisterClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(leftRight = 100.px, topBottom = 50.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputContent(
            id = Id.FIRST_NAME_INPUT,
            placeholder = "First Name"
        )

        InputContent(
            id = Id.LAST_NAME_INPUT,
            placeholder = "Last Name"
        )

        InputContent(
            id = Id.EMAIL_INPUT,
            placeholder = "E-Mail"
        )

        InputContent(
            id = Id.PHONE_NUMBER_INPUT,
            placeholder = "Phone Number"
        )

        InputContent(
            id = Id.PASSWORD_INPUT,
            placeholder = "Password"
        )

        ButtonContent(
            text = "Register",
            onClick = onRegisterClick
        )
    }
}