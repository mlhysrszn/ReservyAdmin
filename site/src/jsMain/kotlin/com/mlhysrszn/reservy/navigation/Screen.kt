package com.mlhysrszn.reservy.navigation

sealed class Screen(val route: String) {
    data object Login : Screen(route = "/")
    data object AdminCreateBusiness : Screen(route = "/create-business")
    data object AdminUpdateBusiness : Screen(route = "/update-business")
}