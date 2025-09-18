
package tec.lass.zazil_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import tec.lass.zazil_app.ui.theme.ZAZIL_APPTheme
import tec.lass.zazil_app.view.MyApp
import tec.lass.zazil_app.viewmodel.SessionViewModel
import tec.lass.zazil_app.viewmodel.UserProfileViewModel

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZAZIL_APPTheme {
                val navController = rememberNavController()
                MyApp(navController = navController, sessionViewModel = SessionViewModel())
            }
        }
    }

}




