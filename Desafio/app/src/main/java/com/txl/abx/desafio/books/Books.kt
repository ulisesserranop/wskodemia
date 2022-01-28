package com.txl.abx.desafio.books

import com.txl.abx.practicamodulo3p3.dataclass.Book
import kotlinx.serialization.Serializable

@Serializable
data class Books(val data: MutableList<Book>): java.io.Serializable
