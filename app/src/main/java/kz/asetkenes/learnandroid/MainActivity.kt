package kz.asetkenes.learnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kz.asetkenes.learnandroid.ui.navigation.MainDestination
import kz.asetkenes.learnandroid.ui.screens.signup.SignUpScreen
import kz.asetkenes.learnandroid.ui.screens.splash.SplashScreen
import kz.asetkenes.learnandroid.ui.screens.users.UsersScreen
import kz.asetkenes.learnandroid.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApplicationScope.init(applicationContext)

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = MainDestination.SPLASH_DESTINATION) {
                        
                        composable(MainDestination.SPLASH_DESTINATION) { 
                            SplashScreen(navController = navController)
                        }

                        composable(MainDestination.SIGN_IN_DESTINATION) {
                            SignUpScreen()
                        }

                        composable(MainDestination.HOME_DESTINATION) {
                            UsersScreen()
                        }
                    }
                }
            }
        }
    }
}