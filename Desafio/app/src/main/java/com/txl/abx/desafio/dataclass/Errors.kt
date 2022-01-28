package com.txl.abx.desafio.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Errors(
    val errors: List<Error>
    )
