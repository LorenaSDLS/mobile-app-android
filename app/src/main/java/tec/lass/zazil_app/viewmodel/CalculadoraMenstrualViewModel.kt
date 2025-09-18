package tec.iama.myapplication.ciclo.viewmodel

import androidx.lifecycle.ViewModel
import java.time.LocalDate

/**
 * Clase CalculadoraMenstrualViewModel
 *
 * Este ViewModel maneja los cálculos relacionados con el ciclo menstrual de la usuaria,
 * incluyendo la próxima menstruación y los días fértiles.
 */

class CalculadoraMenstrualViewModel : ViewModel() {


    /**
     * Función calcularProximaMenstruacion
     *
     * Calcula la fecha de la próxima menstruación sumando la duración del ciclo a la fecha
     * de la última menstruación.
     *
     * @param fechaUltimaMenstruacion Fecha de la última menstruación (LocalDate).
     * @param duracionCiclo Duración del ciclo menstrual en días (Int).
     * @return La fecha estimada de la próxima menstruación (LocalDate).
     */

    fun calcularProximaMenstruacion(fechaUltimaMenstruacion: LocalDate, duracionCiclo: Int): LocalDate {
        return fechaUltimaMenstruacion.plusDays(duracionCiclo.toLong())
    }


    fun calcularDiasFertiles(proximaMenstruacion: LocalDate, duracionCiclo: Int): Pair<LocalDate, LocalDate> {
        val ovulacion = proximaMenstruacion.minusDays((duracionCiclo / 2).toLong())
        return Pair(ovulacion.minusDays(4), ovulacion.plusDays(4))
    }
}