package com.txl.abx.practicamodulo3p3.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val type: String,
    val id: String,
    val attributes: Attributes,
    val relationships: Relationships,
    val links: Links
)
