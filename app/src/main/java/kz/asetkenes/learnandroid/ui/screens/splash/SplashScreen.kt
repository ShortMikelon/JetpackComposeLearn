package kz.asetkenes.learnandroid.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kz.asetkenes.learnandroid.ui.navigation.MainDestination

@Composable
fun SplashScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Splash Screen",
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }

    LaunchedEffect(Unit) {
        navController.navigate(MainDestination.SIGN_IN_DESTINATION) {
            popUpTo(MainDestination.SPLASH_DESTINATION) {
                inclusive = true
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreen_Preview() {
    val navController = rememberNavController()
    SplashScreen(navController = navController)
}