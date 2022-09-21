package lee.module.design.base.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import lee.module.design.base.model.Screen

@Composable
fun BindNavigator(navController: NavHostController, screen: Screen) {
    if (navController.currentDestination?.route != screen.route) {
        navController.navigate(screen.route)
    }
}
