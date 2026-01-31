package com.example.diplom

sealed class Screen(val route:String){
    object LoginScreen : Screen(route = "login_screen")
    object RegisterScreen : Screen(route = "reg_screen")

    object AppScreen : Screen(route = "app_screen")

    object NewTestScreen : Screen(route = "new_test_screen")

    object CreatingNewTestScreen : Screen(route = "creating_new_test_screen")

    object TestToRecreateScreen : Screen(route = "tests_to_recreate_screen")
    object RecreatingTestScreen : Screen(route = "recreating_test_screen")

    object CreateNewUserScreen : Screen(route = "creating_new_user_screen")
    object UpdateUserInformationScreen : Screen(route = "update_user_inf_screen")

    object TestToCompleteScreen : Screen(route = "test_to_complete_screen")
    object TestingScreen : Screen(route = "testing_screen")

    object ResultsScreen : Screen(route = "results_screen")
    object UserForUserResults : Screen(route = "user_for_results")
    object UserResults : Screen(route = "user_results")

    object DeleteTestScreen : Screen(route = "delete_screen")

    fun withArgs(vararg args:String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}