package tec.lass.zazil_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tec.lass.zazil_app.R
/**
 * Composable que representa la pantalla de 'Conócenos'.
 * Muestra información sobre la aplicación y la organización zazil
 */


@Composable
fun PantallaConocenos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cargar imagen desde res/drawable
        Image(
            painter = painterResource(id = R.drawable.toalla), // Reemplaza con el nombre de tu imagen
            contentDescription = "Imagen de toalla",
            modifier = Modifier
                .height(210.dp) // Ajusta el tamaño de la imagen
                .padding(bottom = 8.dp)
        )

        Text(
            text = "¿Quiénes somos?",
            fontSize = 24.sp,
            color = Color(0xFFFF9045),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.zazil_sin), // Reemplaza con el nombre de tu imagen
            contentDescription = "Logo de Zazil",
            modifier = Modifier
                .height(150.dp) // Ajusta el tamaño de la imagen
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Zazil es una marca comprometida con el bienestar de las mujeres y el cuidado del medio ambiente. Su misión es proporcionar soluciones innovadoras y sostenibles para el período menstrual.",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.sabias), // Reemplaza con el nombre de tu imagen
            contentDescription = "sabias",
            modifier = Modifier
                .height(250.dp) // Ajusta el tamaño de la imagen
                .padding(bottom = 16.dp)
        )
        Text(text = "¿Cómo lo hacen? A través de la creación de toallas femeninas reutilizables.", fontSize = 18.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Promueve:", fontSize = 18.sp, color = Color.Black)

        // Filas para los cuadritos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly // Distribuye equitativamente
        ) {
            // Primer cuadro
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color(0xFFF1C7D4), shape = RoundedCornerShape(15.dp))
                    .background(Color(0xFFF1C7D4), shape = RoundedCornerShape(15.dp))
                    .padding(9.dp)
            ) {
                Text(text = "Erradicar la pobreza menstrual", textAlign = TextAlign.Center, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Segundo cuadro
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color(0xFFF1C7D4), shape = RoundedCornerShape(15.dp))
                    .background(Color(0xFFF1C7D4), shape = RoundedCornerShape(15.dp))
                    .padding(11.dp)
            ) {
                Text(text = "Eliminar el uso de desechables", textAlign = TextAlign.Center, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Tercer cuadro
            Box(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, Color(0xFFF1C7D4), shape = RoundedCornerShape(15.dp))
                    .background(Color(0xFFF1C7D4), shape = RoundedCornerShape(15.dp))
                    .padding(20.dp)
            ) {
                Text(text = "Impulsar el autoempleo", textAlign = TextAlign.Center, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Al diseñar toallas con materiales de alta calidad, hipoalergénicos y absorbentes, garantizan una experiencia cómoda y segura durante el período menstrual. Pero lo más importante es que son reutilizables, lo que significa que ayudan a reducir la generación de residuos y contribuyen a la conservación del medio ambiente.", fontSize = 16.sp, color = Color.Black, textAlign = TextAlign.Left)

        Image(
            painter = painterResource(id = R.drawable.toallaperf), // Reemplaza con el nombre de tu imagen
            contentDescription = "toalla perfecta",
            modifier = Modifier
                .height(300.dp) // Ajusta el tamaño de la imagen
                .padding(top = 16.dp, bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Misión:", fontSize = 18.sp, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "En Zazil, no solo estamos redefiniendo la menstruación, sino también el impacto que tiene en la economía y el medio ambiente. Nuestra misión es empoderar a las mujeres a tomar decisiones informadas sobre su salud menstrual mientras generan un impacto positivo en su bienestar financiero y en el planeta \n \n", fontSize = 16.sp, color = Color.Black)
        Text(text = "Visión:", fontSize = 18.sp, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Imaginamos un mundo donde la menstruación no solo es sostenible para el planeta, sino también empoderadora para todas las mujeres. Queremos que cada elección consciente de Zazil contribuya a la creación de comunidades fuertes, mujeres empoderadas económicamente y un entorno más saludable y equitativo. \n" +
                "Nuestra visión es que Zazil no sea solo un producto, sino una fuerza positiva que transforma la forma en que vivimos la menstruación, promoviendo el bienestar personal y global", fontSize = 16.sp, color = Color.Black)
        Image(
            painter = painterResource(id = R.drawable.beneficios), // Reemplaza con el nombre de tu imagen
            contentDescription = "beneficios",
            modifier = Modifier
                .height(300.dp) // Ajusta el tamaño de la imagen
                .padding(top = 16.dp, bottom = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Fue creada por la fundación Todas Brillamos.", fontSize = 16.sp, color = Color.Black)

        Image(
            painter = painterResource(id = R.drawable.todasbrillamos), // Reemplaza con el nombre de tu imagen
            contentDescription = "Logo de Todas Brillamos",
            modifier = Modifier
                .height(150.dp) // Ajusta el tamaño de la imagen
                .padding(top = 16.dp, bottom = 16.dp)
        )

        Text(
            text = "Dedicada a empoderar a mujeres y niñas mediante educación, salud y desarrollo personal.",
            fontSize = 16.sp,
            color = Color.Black,
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Nuestro lema:", fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "LIBERTAD PARA TODAS", fontSize = 20.sp, color = Color(0xFFFDD5507C))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Es la esencia de nuestra misión. Creemos que todas las personas tienen un brillo único y merecen vivir con libertad y dignidad.", fontSize = 16.sp,color = Color.Black)
        Spacer(modifier = Modifier.height(25.dp))
        Text(text = "Nosotros como asociación:", fontSize = 17.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Somos una organización sin fines de lucro y Donataria Autorizada comprometida con el cambio positivo en la sociedad mexicana. Nuestro enfoque se centra en la igualdad de género, el empoderamiento de las mujeres y la erradicación de la pobreza menstrual. Nuestra misión es crear un entorno donde todas las personas, sin importar su género, puedan vivir con dignidad y libertad. Trabajamos incansablemente para promover la igualdad, empoderar a las mujeres y asegurar que todas tengan acceso a productos de higiene menstrual. Únete a nosotros en esta importante causa y juntos hagamos brillar a todas las personas.", fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Nuestro trabajo se extiende a tres áreas clave:", fontSize = 17.sp, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "• Educación menstrual: La fundación trabaja para brindar educación menstrual, desmitificar tabúes y estigmas en torno a la menstruación y garantizar que todas las niñas y mujeres tengan acceso a información precisa y recursos adecuados para una menstruación saludable.\n \n"+"" +
                    "• Empoderamiento económico: Comprendemos la importancia del empoderamiento económico. Nos esforzamos a crear oportunidades para que las mujeres sean autosuficientes y para que puedan desarrollar sus propios recursos financieros.\n \n"+
                    "• Sostenibilidad y responsabilidad social: Nuestra fundación se compromete a desarrollar proyectos sostenibles y amigables con el medio ambiente, como ZAZIL, abogando por un enfoque responsable hacia el planeta. También nos dedicamos a inculcar a las nuevas generaciones la importancia de tomar decisiones conscientes y comprender el impacto que estas decisiones pueden tener en la sociedad y el entorno. \n \n", fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(18.dp))
        Text(text = "Nuestra misión: \n " + "Impulsar un cambio positivo en la sociedad mexicana al abordar las desigualdades de género, empoderar a las mujeres y niñas, y promover la igualdad de oportunidades. Nos dedicamos a proporcionar educación menstrual, acceso a productos de higiene menstrual, y oportunidades de empoderamiento económico. Buscamos erradicar la pobreza menstrual y crear una sociedad donde todas las personas, sin importar su género, puedan vivir con dignidad y libertad. Nos esforzamos por inculcar una responsabilidad social y ambiental en las nuevas generaciones, resaltando la importancia de tomar decisiones informadas y sostenibles para un mundo mejor.\n \n", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "Nuestra visión: \n" + "Es un futuro en el que la igualdad de género es una realidad, donde cada mujer y niña puede acceder a la educación menstrual, recursos económicos, y oportunidades para alcanzar su máximo potencial. Buscamos un mundo donde la pobreza menstrual sea un recuerdo del pasado y donde la sostenibilidad ambiental sea una parte intrínseca de nuestra forma de vida. Visualizamos comunidades comprometidas con la responsabilidad social y el respeto por nuestro planeta. En este futuro, todos descubren su brillo único y contribuyen al bienestar de la humanidad.\n \n", fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "OBJETIVO", fontSize = 18.sp, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "La Fundación Todas Brillamos AC, tiene como objetivo primordial mejorar la calidad de vida de las niñas y mujeres en México que enfrentan desafíos sociales y económicos. Esto se logra a través de la promoción de la educación menstrual, el empoderamiento económico y la implementación de soluciones sostenibles y respetuosas con el medio ambiente. La fundación se compromete a brindar recursos, orientación y apoyo integral para empoderar a las mujeres y niñas, permitiéndoles vivir vidas con dignidad y oportunidades iguales en México.")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Nuestros logros", fontSize = 18.sp, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            text = "1. Hemos beneficiado a miles de niñas y mujeres, proporcionando acceso a educación menstrual y productos de higiene.\n" +
                    "2. Construcción Exitosa de un Taller de Maquila Zazil en Tulancingo, Hidalgo.\n" +
                    "3. Desarrollamos proyectos sostenibles que no solo benefician a las comunidades, sino que también protegen nuestro entorno.\n" +
                    "4. Creación de Emprendimiento Social Zazil, Toallas Femeninas Reutilizables.\n" +
                    "5. Establecimiento de Alianzas Estratégicas con organizaciones, empresas y comunidades locales, maximizando el impacto positivo.\n" +
                    "6. Sensibilización sobre Decisiones Sostenibles para cuidar el medio ambiente y su entorno.\n" +
                    "7. Entrega de Más de 1000 Toallas Reutilizables Zazil a Mujeres y Niñas Necesitadas.\n \n",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(text = "ODS en los que trabajamos", fontSize = 18.sp, color = Color.Black, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(9.dp))
        Text(text = "1.ODS 1: Fin de la pobreza:\n" +
                "La fundación trabaja para erradicar la pobreza menstrual y empoderar económicamente a las mujeres, contribuyendo así al objetivo de poner fin a la pobreza.\n" +
                "2.ODS 4: Educación de calidad:\n" +
                "Proporciona educación menstrual para mujeres y niñas, contribuyendo al acceso a una educación de calidad.\n" +
                "3.ODS 5: Igualdad de género:\n" +
                "La fundación tiene como misión principal promover la igualdad de género, abordando la pobreza menstrual y empoderando a las mujeres.\n" +
                "4.ODS 8: Trabajo decente y crecimiento económico:\n" +
                "Empodera económicamente a las mujeres a través de programas de formación y apoyo empresarial, contribuyendo al crecimiento económico.\n" +
                "5.ODS 10: Reducir la desigualdad en y entre los países:\n" +
                "Busca abordar las desigualdades económicas y sociales, promover la inclusión y empoderar a grupos vulnerables.\n" +
                "6.ODS 12: Producción y consumo responsables:\n" +
                "Promueve prácticas sostenibles y responsabilidad ambiental en la comunidad, alineándose con el objetivo de\n" +
                "producción y consumo responsables. 7.ODS 13: Acción por el clima:\n" +
                "Al fomentar prácticas sostenibles, la fundación contribuye indirectamente a la acción por el clima.\n" +
                "8.ODS 17: Alianzas para lograr los objetivos:\n" +
                "A través de alianzas estratégicas con organizaciones y comunidades locales, la fundación contribuye a la colaboración\n" +
                "necesaria para alcanzar los objetivos de desarrollo sostenible.", fontSize = 16.sp)
        Image(
            painter = painterResource(id = R.drawable.alianzas), // Reemplaza con el nombre de tu imagen
            contentDescription = "alianzas",
            modifier = Modifier
                .height(300.dp) // Ajusta el tamaño de la imagen
                .padding(top = 16.dp, bottom = 16.dp)
        )


        }
    }

