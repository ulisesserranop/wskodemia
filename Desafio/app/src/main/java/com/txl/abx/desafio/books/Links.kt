package com.txl.abx.practicamodulo3p3.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Links(val self: String, val related: String = "")