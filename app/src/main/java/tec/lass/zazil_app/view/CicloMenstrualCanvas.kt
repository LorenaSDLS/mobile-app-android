package tec.iama.myapplication.ciclo.view
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import kotlin.math.cos
import kotlin.math.sin

/**
 * Composable que dibuja un canvas para representar el ciclo menstrual.
 * Muestra diferentes fases del ciclo y sus respectivos días.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CicloMenstrualConIconos(
    proximaMenstruacion: LocalDate?,
    diasFertilesInicio: LocalDate?,
    diasFertilesFin: LocalDate?,
    fechaUltimaMenstruacion: LocalDate,
    duracionCiclo: Int = 28
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                LegendItem(color = Color(0xFFFFC0CB), text = "Menstruación")
                LegendItem(color = Color(0xFFADD8E6), text = "Fase Folicular")
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                LegendItem(color = Color(0xFFB9C65F), text = "Ovulación")
                LegendItem(color = Color(0xFFFFA500), text = "Fase Lútea")
            }
        }

        // Gráfico de ciclo menstrual
        Box(
            modifier = Modifier
                .size(200.dp) // Aseguramos que sea cuadrado
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(200.dp)) { // Mantiene dimensiones cuadradas
                val canvasSize = Size(size.width, size.height)
                val radius = size.minDimension / 2

                if (proximaMenstruacion != null && diasFertilesInicio != null && diasFertilesFin != null) {
                    // Ajustamos los días de cada fase
                    val duracionFaseMenstrual = 5
                    val duracionFaseLutea = 14
                    val duracionFaseOvulacion = 3
                    val duracionFaseFolicular = duracionCiclo - (duracionFaseMenstrual + duracionFaseLutea + duracionFaseOvulacion)

                    // Cálculo de los ángulos de cada fase en el gráfico circular
                    val totalAngle = 360f
                    val angleFaseMenstrual = (duracionFaseMenstrual / duracionCiclo.toFloat()) * totalAngle
                    val angleFaseFolicular = (duracionFaseFolicular / duracionCiclo.toFloat()) * totalAngle
                    val angleFaseOvulacion = (duracionFaseOvulacion / duracionCiclo.toFloat()) * totalAngle
                    val angleFaseLutea = totalAngle - (angleFaseMenstrual + angleFaseFolicular + angleFaseOvulacion)

                    var startAngle = -90f

                    // Fase Menstrual
                    drawPhase(
                        startAngle = startAngle,
                        sweepAngle = angleFaseMenstrual,
                        color = Color(0xFFFFC0CB), // Color de la menstruación
                        size = canvasSize
                    )
                    startAngle += angleFaseMenstrual

                    // Fase Folicular
                    drawPhase(
                        startAngle = startAngle,
                        sweepAngle = angleFaseFolicular,
                        color = Color(0xFFADD8E6), // Color de la fase folicular
                        size = canvasSize
                    )
                    startAngle += angleFaseFolicular

                    // Fase de Ovulación
                    drawPhase(
                        startAngle = startAngle,
                        sweepAngle = angleFaseOvulacion,
                        color = Color(0xFFB9C65F), // Color de la ovulación
                        size = canvasSize
                    )
                    startAngle += angleFaseOvulacion

                    // Fase Lútea
                    drawPhase(
                        startAngle = startAngle,
                        sweepAngle = angleFaseLutea,
                        color = Color(0xFFFFA500), // Color de la fase lútea
                        size = canvasSize
                    )

                    val totalDias = duracionCiclo
                    val angleBetweenDias = 360f / totalDias
                    val textRadius = radius - 15.dp.toPx()

                    var currentDate = fechaUltimaMenstruacion
                    startAngle = -90f

                    val textPaint = android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 30f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }

                    for (dia in 1..totalDias) {
                        val angleRad = Math.toRadians((startAngle + dia * angleBetweenDias).toDouble())
                        val x = (size.width / 2 + textRadius * cos(angleRad)).toFloat()
                        val y = (size.height / 2 + textRadius * sin(angleRad)).toFloat()

                        val dayOfMonth = currentDate.dayOfMonth.toString()
                        drawContext.canvas.nativeCanvas.drawText(dayOfMonth, x, y, textPaint)

                        currentDate = currentDate.plusDays(1)
                    }
                } else {
                    drawCircle(color = Color.Gray, radius = radius)
                }
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

fun DrawScope.drawPhase(startAngle: Float, sweepAngle: Float, color: Color, size: Size, fill: Boolean = true) {
    if (fill) {
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = true,
            size = size
        )
    } else {
        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = true,
            size = size,
            style = Stroke(60f)
        )
    }
}