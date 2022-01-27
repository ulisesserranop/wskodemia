package com.txl.abx.desafio.dataclass

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Empleado(
    val nombre: String,
    val telefono: Long,
    val casado: Boolean,
    @SerialName("cuota_hora")
    val cuotaHora: Double,
    @SerialName("dias-De-Semana-Trabajo")
    val diasDeSemanaTrabajo: List<String>,
    val observaciones: List<Observacion>
)
