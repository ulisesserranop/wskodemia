package com.txl.abx.desafio.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Observacion(
    val fecha: String,
    val comentario: String
)
