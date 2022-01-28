package com.txl.abx.desafio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.txl.abx.practicamodulo3p3.dataclass.Book

class Detalles : AppCompatActivity() {
    private val TAG = Detalles::class.qualifiedName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        intent.extras?.let {
            val book = it.getSerializable("book") as Book
            Log.d(TAG,book.attributes.title)
        }

    }
}