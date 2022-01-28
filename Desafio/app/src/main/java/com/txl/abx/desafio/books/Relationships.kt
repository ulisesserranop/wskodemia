package com.txl.abx.practicamodulo3p3.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class Relationships(val authors: Authors,val categories: Categories)
