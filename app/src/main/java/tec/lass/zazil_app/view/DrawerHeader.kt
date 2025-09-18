package tec.lass.zazil_app.view
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import tec.lass.zazil_app.R
import tec.lass.zazil_app.viewmodel.SessionViewModel
/**
 * Composable que representa el encabezado del menú lateral (drawer).
 * Muestra información del usuario o un logo en la parte superior del menú.
 */
@Composable
fun DrawerHeader( currentRoute: String?, onHeaderClick: () -> Unit, onCloseDrawer: () -> Unit, sessionViewModel: SessionViewModel, navController: NavController) {
    val phone by sessionViewModel.phoneNumber.observeAsState()
    val userName by sessionViewModel.userName.observeAsState("Usuario")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFDD5507C))
            .clickable {
                if (currentRoute != "perfil") {
                    phone?.let {
                        onHeaderClick()
                        navController.navigate("perfil/$it")
                    }
                    onCloseDrawer()
                }
            }
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_usuario),
            contentDescription = "Usuario",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = userName,
            color = Color.Black,
            fontSize = 30.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}


