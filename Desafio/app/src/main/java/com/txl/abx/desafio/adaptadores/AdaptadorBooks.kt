package com.txl.abx.desafio.adaptadores

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.txl.abx.desafio.books.Books

class AdaptadorBooks(val books: MutableList<Books>): RecyclerView.Adapter<AdaptadorBooks.BookHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = books.size


    class BookHolder(view: View): RecyclerView.ViewHolder(view){

    }
}